package com.xyz.codereview.Modelo

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.OutputStream
import java.net.Socket
import java.util.concurrent.ConcurrentLinkedQueue

object Client {
    private var clientRunning = false
    private lateinit var clientSocket: Socket
    private val actionsOnMainThread = ConcurrentLinkedQueue<() -> Unit>()
    private val ioScope = CoroutineScope(Dispatchers.IO)

    fun startClient(serverAddress: String, serverPort: Int = 25001) {
        clientRunning = true
        ioScope.launch {
            try {
                clientSocket = Socket(serverAddress, serverPort)
                Log.d("Info","Conexión establecida con el servidor")

                launch {
                    handleServerCommunication(clientSocket)
                }
            } catch (e: Exception) {
                Log.d("Info","Error al conectar con el servidor: ${e.message}")
                clientRunning = false
            }
        }
    }

    private suspend fun handleServerCommunication(server: Socket) {
        val stream = server.getInputStream()
        val buffer = ByteArray(1024)
        var bytesRead: Int = -1

        try {
            while (server.isConnected && stream.read(buffer).also { bytesRead = it } != -1) {
                val data = String(buffer, 0, bytesRead)
                println("Mensaje recibido del servidor: $data")
            }
        } catch (e: Exception) {
            println("Error en la comunicación con el servidor: ${e.message}")
        } finally {
            server.close()
            clientRunning = false
        }
    }

    fun stopClient() {
        clientRunning = false
        try {
            clientSocket.close()
        } catch (e: Exception) {
            println("Error al cerrar la conexión del cliente: ${e.message}")
        }
    }

    fun sendMessageToServer(message: String) {
        if (!clientRunning) {
            println("El cliente no está conectado.")
            return
        }

        ioScope.launch {
            try {
                val data = message.toByteArray()
                val stream: OutputStream = clientSocket.getOutputStream()
                if (clientSocket.isConnected) {
                    withContext(Dispatchers.IO) {
                        stream.write(data)
                        stream.flush()
                    }
                }
            } catch (e: Exception) {
                println("Error al enviar mensaje al servidor: ${e.message}")
            }
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

    fun isClientRunning(): Boolean {
        return clientRunning
    }
}
