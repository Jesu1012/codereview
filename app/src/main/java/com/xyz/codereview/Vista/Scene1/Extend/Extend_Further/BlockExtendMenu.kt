package com.xyz.codereview.Vista.Scene1.Extend.Extend_Further

import androidx.compose.runtime.*
import com.xyz.codereview.Vista.Scene1.Extend.Extend_Further.Custom.BlockConnectionServer



@Composable
fun BlockConnection() {

    BlockConnectionServer()

}
@Composable
fun BlockConnectionClient(){
    /*var connectionStatus by remember { mutableStateOf("Desconectado") }
    var isConnecting by remember { mutableStateOf(false) }
    var serverTarget by remember { mutableStateOf("") }
    var serverPort by remember { mutableStateOf("25001") }
    var errorMessage by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        while (true) {
            if (Client.isClientRunning()) {
                Client.update()

                //serverRunning = true
            } else {
                //clientList = emptyList()
                //serverRunning = false
            }
            delay(10000) // Update client list every 10 seconds
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = Color.Red)
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Target: ", color = Color.Green)
            TextField(
                value = serverTarget,
                onValueChange = { serverTarget = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.weight(1f)
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Port: ", color = Color.Red)
            TextField(
                value = serverPort,
                onValueChange = { serverPort = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }

        Button(
            onClick = {
                isConnecting = true
                coroutineScope.launch(Dispatchers.IO) {
                    Client.startClient(getFirstTwoSegmentsIpAddress()+serverTarget)
                }

                Log.d("BlockConnection", "Connecting to ${getFirstTwoSegmentsIpAddress()}$serverTarget")
                connectionStatus = Client.isClientRunning().toString()
            },
            enabled = !isConnecting
        ) {
            Text(text = "Connect", color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = connectionStatus, color = Color.White)
    }*/
}



/*fun getDeviceIpAddress(): String {
    return try {
        val interfaces = NetworkInterface.getNetworkInterfaces() ?: return "Unknown IP"
        for (networkInterface in Collections.list(interfaces)) {
            val addresses = networkInterface.inetAddresses ?: continue
            for (address in Collections.list(addresses)) {
                if (!address.isLoopbackAddress && address.isSiteLocalAddress) {
                    return address.hostAddress ?: "Unknown IP"
                }
            }
        }
        "Unknown IP"
    } catch (ex: SocketException) {
        ex.printStackTrace()
        "Unknown IP"
    }
}*/
