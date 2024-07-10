package com.xyz.codereview.Vista.Scene1.Extend.Extend_Further

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xyz.codereview.Modelo.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.Socket


@Composable
fun BlockConnection() {
    BlockConnectionServer()
}
@Composable
fun BlockConnectionServer() {
    var clientList by remember { mutableStateOf(listOf<Socket>()) }
    var selectedClient by remember { mutableStateOf<Socket?>(null) }
    var serverRunning by remember { mutableStateOf(false) }
    val serverPort = 25001


    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        while (true) {
            if (Server.isServerRunning()) {
                Server.update()
                clientList = Server.getClientConnections()
                serverRunning = true
            } else {
                clientList = emptyList()
                serverRunning = false
            }
            delay(1000) // Update client list every 10 seconds
        }
    }

    Column {
        ServerUI(
            onStartServer = {
                coroutineScope.launch(Dispatchers.IO) {
                    if (!Server.isServerRunning()) {
                        Server.startServer()
                    }
                }
            },
            onStopServer = {
                coroutineScope.launch(Dispatchers.IO) {
                    if (Server.isServerRunning()) {
                        Server.stopServer()
                    }
                }
            },
            clients = clientList,
            onClientSelected = { selectedClient = it },
            serverRunning = serverRunning,
            serverPort = serverPort
        )
    }
}

@Composable
fun ServerUI(
    onStartServer: () -> Unit,
    onStopServer: () -> Unit,
    clients: List<Socket>,
    onClientSelected: (Socket) -> Unit,
    serverRunning: Boolean,
    serverPort: Int
) {
    var serverRuntime by remember { mutableStateOf(false) }
    var colorText = Color(0xFF009688)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .height(500.dp)
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            if (serverRuntime && !serverRunning) {
                CircularProgressIndicator(color = Color.White)
            }

            Button(
                onClick = {
                    serverRuntime = true
                    onStartServer()
                },
                enabled = !serverRunning,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text("Start Server", color = colorText)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    serverRuntime = false
                    onStopServer()
                },
                enabled = serverRunning,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {

                Text("Stop Server", color = colorText)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column {
            Text(
                text = "Target:",
                modifier = Modifier.padding(8.dp),
                color = Color.White
            )

            CustomIPDisplay(Server.getDeviceIpAddress(), ConnectionStatus.Target)
        }

        Column(Modifier.fillMaxWidth()) {
            Text(
                text = "Port:",
                modifier = Modifier.padding(8.dp),
                color = Color.White
            )
            CustomIPDisplay(serverPort.toString(), ConnectionStatus.Port)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Connected Clients:",
            modifier = Modifier.padding(8.dp),
            color = Color.White
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            items(clients) { client ->
                ClientItem(client)
            }
        }
    }
}

@Composable
fun CustomIPDisplay(value: String, connectionStatus: ConnectionStatus = ConnectionStatus.None) {
    var colorText = Color(0xFF009688)
    when (connectionStatus) {
        ConnectionStatus.Target -> {
            colorText = Color(0xFF009688)

            Text(
                text = value,
                color = colorText,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        ConnectionStatus.Port -> {
            colorText = Color(0xFF009688)
            Text(
                text = value,
                color = colorText,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        else -> {
            colorText = Color(0xFF009688)
            Box(
                modifier = Modifier
                    .width(160.dp)
                    .height(48.dp)
                    .background(Color.White, shape = RoundedCornerShape(4.dp))
                    .border(5.dp, color = colorText, shape = RoundedCornerShape(4.dp))
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = value,
                    color = colorText,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

}

@Composable
fun ClientItem(client: Socket) {
    val address = client.inetAddress.hostAddress
    CustomIPDisplay(address.toString(),ConnectionStatus.Clients)
}

@Preview
@Composable
fun PreviewServerUI() {
    ServerUI(
        onStartServer = {},
        onStopServer = {},
        clients = emptyList(),
        onClientSelected = {},
        serverRunning = false,
        serverPort = 8080
    )
}