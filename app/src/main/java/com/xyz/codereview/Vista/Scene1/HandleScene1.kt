package com.xyz.codereview.Vista.Scene1

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xyz.codereview.Controlador.SettingsState
import com.xyz.codereview.Modelo.NavigationItem
import com.xyz.codereview.Modelo.Screen
import com.xyz.codereview.R

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
@Preview
@Composable
private fun SootheBottomNavigationPreview() {

    SootheNavigationBar()

}