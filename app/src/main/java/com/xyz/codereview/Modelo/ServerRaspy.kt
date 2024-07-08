package com.xyz.codereview.Modelo

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.ConcurrentLinkedQueue
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

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
}
