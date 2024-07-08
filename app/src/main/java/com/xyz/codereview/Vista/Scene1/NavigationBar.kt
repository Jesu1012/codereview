package com.xyz.codereview.Vista.Scene1

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xyz.codereview.R
import com.xyz.codereview.Controlador.SettingsState


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
    //Projects(Scenes.Scene1,0,"Projects"),
    GeneralSettings(Scenes.Scene1,1,"General"),
    LanguageSettings(Scenes.Scene1,1,"Language"),
    BuildingInstructions(Scenes.Scene1,1,"Building"),
    Legal(Scenes.Scene1,1,"Legal"),
    //Code(Scenes.Scene1,0, "Code"),
    EditorPrincipal(Scenes.Scene2,0,"Editor Principal"),
    ServerRaspy(Scenes.Scene2,0,"Server Raspy"),
    GameArcore(Scenes.Scene2,0, "Game Arcore")
}

data class NavigationItem(val title: Int, val icon: Int, val screen: Screen)

@Composable
fun SootheNavigationBar(modifier: Modifier = Modifier) {
    val items = listOf(
        NavigationItem(R.string.navHome_name, R.drawable.ic_home, Screen.Home),
        //NavigationItem(R.string.navProjects_name, R.drawable.ic_proyectos, Screen.Projects),
        NavigationItem(R.string.navQuickCode_name, R.drawable.baseline_add_box_24, Screen.QuickCode),
        NavigationItem(R.string.navSetting_name, R.drawable.ic_ajustes, Screen.Settings),
        //NavigationItem(R.string.navProgramming_name, R.drawable.ic_programar, Screen.Code)
    )

    //Screen.entries.first { it.nameScreen == onScreenChange.toString()}
    //
    // Estado para mantener la pantalla actual seleccionada

    // Estado para mantener el ítem anteriormente seleccionado, inicializado con el ítem Home
    var lastSelectedScreen by remember { mutableStateOf<Screen?>(Screen.Home) }

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
fun SootheNavigationRail(modifier: Modifier = Modifier, onScreenChange: (Screen) -> Unit) {
    val items = listOf(
        NavigationItem(R.string.navHome_name, R.drawable.ic_home, Screen.Home),
        NavigationItem(R.string.navQuickCode_name, R.drawable.ic_comunidad, Screen.QuickCode), // Replace with the correct screen
        //NavigationItem(R.string.navProjects_name, R.drawable.ic_proyectos, Screen.Projects), // Replace with the correct screen
        NavigationItem(R.string.navSetting_name, R.drawable.ic_ajustes, Screen.Settings),
        //NavigationItem(R.string.navProgramming_name, R.drawable.ic_programar, Screen.Code) // Replace with the correct screen
    )

    NavigationRail(
        containerColor = Color.Black,
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight()
        ) {
            items.forEach { item ->
                NavigationRailItem(
                    icon = {
                        Image(
                            painter = painterResource(id = item.icon),
                            contentDescription = stringResource(id = item.title)
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(id = item.title),
                            style = TextStyle(
                                fontSize = 10.sp,
                                color = Color.White.copy(alpha = 0.7f)
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    selected = false,
                    onClick = { onScreenChange(item.screen) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun SootheBottomNavigationPreview() {

    SootheNavigationBar()
    //SootheNavigationRail(onScreenChange = {})

}
