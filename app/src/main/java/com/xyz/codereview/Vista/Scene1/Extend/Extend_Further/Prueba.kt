package com.xyz.codereview.Vista.Scene1.Extend.Extend_Further
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Windows81StartScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF3F51B5))
            .padding(16.dp)
    ) {
        Text(
            text = "Start",
            color = Color.White,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//                Tile(name = "Mail", color = Color(0xFF0078D7), width = 150.dp, height = 150.dp)
//                Tile(name = "Calendar", color = Color(0xFF8E24AA), width = 150.dp, height = 150.dp)
//            }
//            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//                Tile(name = "Photos", color = Color(0xFF00B294), width = 150.dp, height = 150.dp)
//                Tile(name = "People", color = Color(0xFFF25022), width = 150.dp, height = 70.dp)
//            }
//            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//                Tile(name = "Store", color = Color(0xFF0099BC), width = 150.dp, height = 150.dp)
//                Tile(name = "Internet Explorer", color = Color(0xFF0078D7), width = 70.dp, height = 70.dp)
//                Tile(name = "Help+Tips", color = Color(0xFFF25022), width = 70.dp, height = 70.dp)
//            }
//            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//                Tile(name = "Weather", color = Color(0xFF1F75FE), width = 310.dp, height = 150.dp)
//            }
//            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//                Tile(name = "Finance", color = Color(0xFF00A300), width = 150.dp, height = 70.dp)
//                Tile(name = "Reading List", color = Color(0xFFF25022), width = 150.dp, height = 70.dp)
//            }
//            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//                Tile(name = "Skype", color = Color(0xFF00AFF0), width = 150.dp, height = 150.dp)
//                Tile(name = "Maps", color = Color(0xFF1F75FE), width = 150.dp, height = 150.dp)
//            }
        }
    }
}
@Composable
fun Tile(name: String, color: Color, width: Dp, height: Dp, onclick: () -> Unit){
    Box(
        modifier = Modifier
            .size(width, height)
            .background(color, shape = RoundedCornerShape(8.dp))
            .clickable {
                onclick()

            },
        contentAlignment = Alignment.Center
    ) {
        Text(text = name, color = Color.White, fontSize = 16.sp)
    }
}


@Preview(showBackground = true)
@Composable
fun Windows81StartScreenPreview() {
    Windows81StartScreen()
}
