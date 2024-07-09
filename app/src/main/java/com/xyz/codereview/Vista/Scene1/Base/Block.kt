package com.xyz.codereview.Vista.Scene1.Base

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xyz.codereview.Controlador.SettingsState
import com.xyz.codereview.R



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

data class NavigationItem(val title: Int, val icon: Int, val screen: Screen)

@Composable
fun SootheNavigationBar(modifier: Modifier = Modifier) {
    val items = listOf(
        NavigationItem(R.string.navHome_name, R.drawable.ic_home, Screen.Home),
        NavigationItem(R.string.navQuickCode_name, R.drawable.baseline_add_box_24, Screen.QuickCode),
        NavigationItem(R.string.navSetting_name, R.drawable.ic_ajustes, Screen.Settings),
    )
    NavigationBar(
        containerColor = Color.Black,
        modifier = modifier
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Image(
                        painter = painterResource(id = item.icon),
                        contentDescription = stringResource(id = item.title),
                        modifier = Modifier
                            .height(30.dp)
                            .width(30.dp)
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = item.title),
                        style = TextStyle(
                            fontSize = 10.sp,
                            color = if (SettingsState.selectedScreen == item.screen) Color.White else Color.White.copy(alpha = 0.7f)
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                selected = SettingsState.selectedScreen == item.screen,
                onClick = {
//                    if (item.screen != Screen.Code) {
//                        lastSelectedScreen = SettingsState.selectedScreen // Guardar el ítem anteriormente seleccionado
//                        SettingsState.selectedScreen = item.screen // Actualiza el ítem actual
//                    } else {
//                        // Cuando Screen.Code es seleccionado, no actualizamos lastSelectedScreen ni selectedScreen
//                        // pero podemos manejar cualquier lógica adicional necesaria aquí.
//                    }
                    SettingsState.selectedScreen = item.screen
                    Log.d("Screen", item.screen.nameScreen)
                }
            )
        }
    }
}

@Composable
fun HeadContent(
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    screenPrincipal: Screen
) {
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
    Surface(
        color = Color(0x4D7F7F7F),
        shape = RoundedCornerShape(8.dp),
    ) {
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
private fun SootheBottomNavigationPreview() {

    SootheNavigationBar()

}

@Preview
@Composable
fun HeadContentPreview() {

}