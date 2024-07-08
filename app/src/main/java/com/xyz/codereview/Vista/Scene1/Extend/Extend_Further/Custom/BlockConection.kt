package com.xyz.codereview.Vista.Scene1.Extend.Extend_Further.Custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xyz.codereview.Modelo.Server


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.NetworkInterface
import java.net.Socket
import java.net.SocketException
import java.util.*

@Composable
fun BlockConnectionServer(
    modifier: Modifier = Modifier
) {
    var clientList by remember { mutableStateOf(listOf<Socket>()) }
    var selectedClient by remember { mutableStateOf<Socket?>(null) }
    var serverRunning by remember { mutableStateOf(false) }
    val serverPort = 25001

    val serverHost = remember { getDeviceIpAddress() }
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
            serverHost = serverHost,
            serverPort = serverPort
        )
    }
}
fun getSegmentsIpAddress(valueNum: Int): String {
    val ipAddress = getDeviceIpAddress()
    val ipSegments = ipAddress.split(".")
    return ipSegments[valueNum] ?: "null"
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




@Composable
fun ServerUI(
    onStartServer: () -> Unit,
    onStopServer: () -> Unit,
    clients: List<Socket>,
    onClientSelected: (Socket) -> Unit,
    serverRunning: Boolean,
    serverHost: String,
    serverPort: Int
) {
    var serverRuntime by remember { mutableStateOf(false) }
    var colo = TextFieldColors(
        focusedTextColor = Color.Black,
        unfocusedTextColor = Color.Black,
        disabledTextColor = Color.Black, // Valor específico dado
        errorTextColor = Color.Black,
        focusedContainerColor = Color.Black,
        unfocusedContainerColor = Color.Black,
        disabledContainerColor = Color.White,
        errorContainerColor = Color.Black,
        cursorColor = Color.Black,
        errorCursorColor = Color.Black,
        textSelectionColors =
        TextSelectionColors(handleColor = Color.Black, backgroundColor = Color.Black),
        focusedIndicatorColor = Color.Black,
        unfocusedIndicatorColor = Color.Black,
        disabledIndicatorColor = Color.Black,
        errorIndicatorColor = Color.Black,
        focusedLeadingIconColor = Color.Black,
        unfocusedLeadingIconColor = Color.Black,
        disabledLeadingIconColor = Color.Black,
        errorLeadingIconColor = Color.Black,
        focusedTrailingIconColor = Color.Black,
        unfocusedTrailingIconColor = Color.Black,
        disabledTrailingIconColor = Color.Black,
        errorTrailingIconColor = Color.Black,
        focusedLabelColor = Color.Black,
        unfocusedLabelColor = Color.Black,
        disabledLabelColor = Color.Black,
        errorLabelColor = Color.Black,
        focusedPlaceholderColor = Color.Black,
        unfocusedPlaceholderColor = Color.Black,
        disabledPlaceholderColor = Color.Black,
        errorPlaceholderColor = Color.Black,
        focusedSupportingTextColor = Color.Black,
        unfocusedSupportingTextColor = Color.Black,
        disabledSupportingTextColor = Color.Black,
        errorSupportingTextColor = Color.Black,
        focusedPrefixColor = Color.Black,
        unfocusedPrefixColor = Color.Black,
        disabledPrefixColor = Color.Black,
        errorPrefixColor = Color.Black,
        focusedSuffixColor = Color.Black,
        unfocusedSuffixColor = Color.Black,
        disabledSuffixColor = Color.Black,
        errorSuffixColor = Color.Black
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .height(500.dp)
            .background(Color.Black)
    ) {


        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start) {
            if ((serverRuntime && !serverRunning)){
                CircularProgressIndicator()
            }

            Button(onClick = {
                serverRuntime = true
                onStartServer()

                             }, enabled = !serverRunning) {
                Text("Start Server", color = Color.Black)
            }
            Button(
                onClick = {
                    serverRuntime = false
                    onStopServer()

                          },
                enabled = serverRunning
            ) {
                Text("Stop Server", color = Color.Black)
            }
        }
//        if (serverRunning){
//            Server.messag.forEach() {
//                Text(text = it, color = Color.White)
//            }
//        }
        Spacer(modifier = Modifier.height(16.dp))
        Column {
            Text(
                text = "Target: ",
                modifier = Modifier.padding(8.dp),
                color = Color.White
            )

            TextField(modifier = Modifier.width(160.dp),value = getDeviceIpAddress(), onValueChange = {}, enabled = false, colors = colo)

        }

        Column(Modifier.fillMaxWidth()) {
            Text(
                text = "Port: ${getDeviceIpAddress()}",
                modifier = Modifier.padding(8.dp),
                color = Color.White
            )
            TextField(modifier = Modifier.width(100.dp),value = serverPort.toString(), onValueChange = {}, enabled = false, colors = colo)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Connected Clients",
            modifier = Modifier.padding(8.dp),
            color = Color.White
        )

        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)) {
            items(clients) { client ->
                ClientItem(client, onClientSelected)
            }
        }

    }
}

@Composable
fun ClientItem(client: Socket, onClientSelected: (Socket) -> Unit) {
    val address = client.inetAddress.hostAddress
    TextField(modifier = Modifier.width(200.dp),value = address.toString(), onValueChange = {}, enabled = false)
//    Text(
//        text = "Client: $address", color = Color.White,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//            .clickable { onClientSelected(client) }
//    )
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
        serverHost = "127.0.0.1",
        serverPort = 8080
    )
}
