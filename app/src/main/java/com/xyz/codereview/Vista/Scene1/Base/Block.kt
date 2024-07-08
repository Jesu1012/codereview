package com.xyz.codereview.Vista.Scene1.Base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xyz.codereview.Vista.Scene1.Screen

@Composable
fun HeadContent(
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    screenPrincipal: Screen,
    subScreenPrincipal: Screen
) {
    val paddingBlock: Int = 10
    val spaceTitleContent: Int = 20
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(end = 20.dp)
    ) {
        Text(
            text = screenPrincipal.nameScreen,
            style = MaterialTheme.typography.headlineSmall,
            color = textColor,
            modifier = Modifier.padding(bottom = spaceTitleContent.dp)
        )
        when(subScreenPrincipal){

            //Screen.Projects -> SearchBar()

            Screen.QuickCode,
            Screen.GeneralSettings,
            Screen.LanguageSettings,
            Screen.BuildingInstructions,
            Screen.Legal -> {}

            else -> {}
        }
    }
}

@Composable
fun SearchBar(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray, RoundedCornerShape(8.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Search, contentDescription = null, tint = Color.White)
        Spacer(modifier = Modifier.width(8.dp))
        Text("Buscar", color = Color.White)
    }
}
@Composable
fun CloseBar(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
    textColor: Color
) {
    val paddingItem: Int = 16
    val sizeButton: Int = 30 // Tamaño del botón circular
    val sizeIconItem: Int = 24 // Tamaño del ícono
    Surface (
        color = Color(0x4D7F7F7F),
        shape = RoundedCornerShape(8.dp),
    ){
        Row(
            modifier = Modifier.padding(paddingItem.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            // Circular button with icon
            Button(
                onClick = onClick,
                shape = CircleShape,
                modifier = Modifier.size(sizeButton.dp),
                contentPadding = PaddingValues(0.dp) // Sin padding para centrar el ícono
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.Magenta,
                    modifier = Modifier.size(sizeIconItem.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = text,
                    color = textColor,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }

}
@Preview
@Composable
fun HeadContentPreview() {
    CloseBar(Icons.Default.KeyboardArrowLeft, Screen.GeneralSettings.toString(),{},Color.White)
}