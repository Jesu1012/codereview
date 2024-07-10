package com.xyz.codereview.Modelo

import android.content.Context
import android.os.Vibrator
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.net.NetworkInterface
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketException
import java.util.Collections
import java.util.concurrent.ConcurrentLinkedQueue


data class NavigationItem(val title: Int, val icon: Int, val screen: Screen)

enum class Scenes(){
    Scene1,
    Scene2
}
enum class Screen(
    val typeScene: Scenes,
    val category : Int,
    val nameScreen : String
) {
    Home(Scenes.Scene1,0,"Home"),
    QuickCode(Scenes.Scene1,0,"Quick Code"),
    Settings(Scenes.Scene1,0,"Settings"),
    GeneralSettings(Scenes.Scene1,1,"General"),
    Legal(Scenes.Scene1,1,"Legal"),
    EditorPrincipal(Scenes.Scene2,0,"Editor Principal"),
}
//BlockExtendQuickCodeCraft
enum class NavigationPanel {
    NONE,
    Language,
    Theme
}
//BlockExtendMenu
enum class ConnectionStatus { None, Target, Port, Clients }

@Serializable
data class MessageList(val messages: List<String>)

object Server {
    private var serverRunning = false
    private lateinit var serverSocket: ServerSocket
    private val clientConnections = mutableListOf<Socket>()
    private val actionsOnMainThread = ConcurrentLinkedQueue<() -> Unit>()
    private val ioScope = CoroutineScope(Dispatchers.IO)
    var messag = mutableStateListOf<String>()

    fun startServer() {
        serverRunning = true
        serverSocket = ServerSocket(25001)
        Log.d("Server message:", "Servidor en espera...")

        ioScope.launch {
            while (serverRunning) {
                try {
                    val clientSocket = serverSocket.accept()
                    clientConnections.add(clientSocket)
                    Log.d("Server message:", "Conexión establecida")
                    launch {
                        handleClientCommunication(clientSocket)
                    }
                } catch (e: Exception) {
                    Log.d("Server message:", "Error en el servidor: ${e.message}")
                    break
                }
            }
        }
    }

    private fun handleClientCommunication(client: Socket) {
        val stream = client.getInputStream()
        val buffer = ByteArray(40096) // Aumenta el tamaño del buffer a 4096 bytes
        var bytesRead: Int = -1

        try {
            while (client.isConnected && stream.read(buffer).also { bytesRead = it } != -1) {
                val data = String(buffer, 0, bytesRead)
                Log.d("Server message:", "Mensaje recibido del cliente: $data")
                processReceivedData(data)
            }
        } catch (e: Exception) {
            Log.d("Server message:", "Error en la comunicación con el cliente: ${e.message}")
        } finally {
            client.close()
            synchronized(clientConnections) {
                clientConnections.remove(client)
            }
        }
    }

    private fun processReceivedData(data: String) {
        try {
            messag.clear()
            val messageList = Json.decodeFromString<MessageList>(data)
            messageList.messages.forEach { message ->
                Log.d("Server message:", "Mensaje procesado: $message")
                messag.add(message)
            }
        } catch (e: Exception) {
            Log.d("Server message:", "Error al procesar datos recibidos: ${e.message}")
        }
    }

    fun stopServer() {
        serverRunning = false
        synchronized(clientConnections) {
            clientConnections.forEach { it.close() }
            clientConnections.clear()
        }
        serverSocket.close()
    }

    fun sendMessageToAllClients(message: String) {
        val data = message.toByteArray()
        synchronized(clientConnections) {
            clientConnections.forEach { client ->
                val stream = client.getOutputStream()
                if (client.isConnected) {
                    stream.write(data)
                    stream.flush()
                }
            }
        }
    }

    fun sendMessageToClient(client: Socket, message: String) {
        val data = message.toByteArray()
        val stream = client.getOutputStream()
        if (client.isConnected) {
            stream.write(data)
            stream.flush()
        }
    }

    fun executeOnMainThread(action: () -> Unit) {
        actionsOnMainThread.add(action)
    }

    fun update() {
        while (actionsOnMainThread.isNotEmpty()) {
            actionsOnMainThread.poll()?.invoke()
        }
    }

    fun onDestroy() {
        sendMessageToAllClients("exit")
        stopServer()
    }

    fun getClientConnections(): List<Socket> {
        return synchronized(clientConnections) {
            clientConnections.toList()
        }
    }

    fun isServerRunning(): Boolean {
        return serverRunning
    }
    fun getDeviceIpAddress(): String {
        try {
            val interfaces = NetworkInterface.getNetworkInterfaces() ?: return "Unknown IP"
            for (networkInterface in Collections.list(interfaces)) {
                val addresses = networkInterface.inetAddresses ?: continue
                for (address in Collections.list(addresses)) {
                    if (!address.isLoopbackAddress && address.isSiteLocalAddress) {
                        return address.hostAddress ?: "Unknown IP"
                    }
                }
            }
        } catch (ex: SocketException) {
            ex.printStackTrace()
        }
        return "Unknown IP"
    }
}

//BlockExtendQuickCodeTime
object PomodoroState {
    var isRunning by mutableStateOf(false)
    var timeLeft by mutableStateOf(25 * 60) // 25 minutes in seconds
    var currentPomodoro by mutableStateOf(1)
    var currentStage by mutableStateOf("Concentración")

    fun reset() {
        isRunning = false
        timeLeft = 25 * 60
        currentPomodoro = 1
        currentStage = "Concentración"
    }

    fun nextStage(context: Context) {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        when (currentStage) {
            "Concentración" -> {
                if (currentPomodoro < 4) {
                    currentStage = "Pausa Corta"
                    timeLeft = 5 * 60 // 5 minutes break
                } else {
                    currentStage = "Pausa Larga"
                    timeLeft = 15 * 60 // 15 minutes long break
                    //currentPomodoro = 0
                }
                vibrator.vibrate(500) // Vibrate for 500 milliseconds
            }
            "Pausa Corta", "Pausa Larga" -> {
                currentStage = "Concentración"
                timeLeft = 25 * 60 // 25 minutes work
                currentPomodoro++
                vibrator.vibrate(600)
            }
        }
    }
}