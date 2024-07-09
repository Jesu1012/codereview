package com.xyz.codereview.Vista.Scene1.Extend

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Settings
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
import com.xyz.codereview.Controlador.SettingsState
import com.xyz.codereview.Vista.Scene1.Base.*
import com.xyz.codereview.Vista.Scene1.Extend.Extend_Further.*



@Composable
fun BlockSettings(screen: Screen) {

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(end = 10.dp)
        .verticalScroll(rememberScrollState())
    ){
        if (screen != Screen.Settings){
            CloseBar(
                icon = Icons.Default.KeyboardArrowLeft,
                text = screen.nameScreen,
                onClick = {
                    SettingsState.selectedScreen = Screen.Settings
                },
                textColor = Color.White
            )
        }
        when (screen) {
            Screen.Settings -> ContentSettings()
            Screen.GeneralSettings -> ContentGeneralScreen()
            Screen.Legal -> ContentLegalScreen()
            else -> {}
        }
    }



}




@Composable
fun ContentSettings(textColor: Color = Color.White){

    SettingsButton(
        icon = Icons.Default.Settings,
        text = "General",
        onClick = {
            SettingsState.selectedScreen = Screen.GeneralSettings
        },
        textColor = textColor
    )
//    SettingsButton(
//        icon = Icons.Default.Face,
//        text = "Language",
//        onClick = { onScreenChange(Screen.LanguageSettings) },
//        textColor = textColor
//    )
//    SettingsButton(
//        icon = Icons.Default.Build,
//        text = "Building Instructions",
//        onClick = { onScreenChange(Screen.BuildingInstructions) },
//        textColor = textColor
//    )
    SettingsButton(
        icon = Icons.Default.DateRange,
        text = "Legal",
        onClick = {
            SettingsState.selectedScreen = Screen.Legal
        },
        textColor = textColor
    )
    Text(
        text = "0.0.1 (0.0.1)",
        style = MaterialTheme.typography.bodyMedium,
        color = textColor,
        modifier = Modifier.padding(top = 20.dp)
    )

}
@Composable
fun SettingsButton(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
    textColor: Color
) {
    val spaceBetweenContent: Int = 5
    val sizeCorner: Int = 8
    val paddingItem: Int = 26
    val sizeIconItem: Int = 34
    val paddingIconTitleItem: Int = 16
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = spaceBetweenContent.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(sizeCorner.dp),
        color = Color(0x4D7F7F7F)
    ) {
        Row(
            modifier = Modifier.padding(paddingItem.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.Magenta,
                modifier = Modifier.size(sizeIconItem.dp)
            )
            Spacer(modifier = Modifier.width(paddingIconTitleItem.dp))
            Text(
                text = text,
                color = textColor,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.Magenta,
                modifier = Modifier.size(sizeIconItem.dp)
            )
        }
    }
}

@Preview
@Composable
fun BlockSettingsPreview() {
    //HeadContent(Color.Black, Color.White,10,50,Screen.Settings,Screen.GeneralSettings);
    //Content(Color.Black, Color.White,{},10,50)
    //CloseBarS(Icons.Default.KeyboardArrowLeft, Screen.GeneralSettings.toString(),{}, Color.White)
    BlockSettings(Screen.Settings)
}