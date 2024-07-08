package com.xyz.codereview.Vista.Scene1.Extend

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xyz.codereview.R


@Composable
fun CodeDialog(
    onDismiss: () -> Unit,
    onCreate: () -> Unit
) {
    var selectedLanguageIndex by remember { mutableStateOf(0) }
    var selectGithub by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.7f))
            .clickable(onClick = onDismiss)
    ) {
        BoxWithConstraints(
            modifier = Modifier.align(Alignment.Center)
        ) {
            val maxWidth = if (maxWidth < 600.dp) maxWidth * 0.9f else 590.dp

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFF4E4E4E),
                modifier = Modifier
                    .width(maxWidth)
                    .padding(25.dp)
                    .clickable(enabled = false) {}
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Nuevo proyecto",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White
                        )
                        IconButton(onClick = onDismiss) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Cerrar",
                                tint = Color.White)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Proyecto",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                    ){
                        ProjectOption(
                            icon = R.drawable.languagelogo,
                            text = "Github",
                            isSelected = selectGithub,
                            onSelect = {
                                selectGithub = !selectGithub

                            }
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                    ) {



/*
                        val addedCodes = SettingsState.getAddedCodes()

                        addedCodes.forEachIndexed { index, code ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(8.dp)
                            ) {
                                ProjectOption(
                                    icon = R.drawable.languagelogo,
                                    text = code.toString(),
                                    isSelected = selectedLanguageIndex == index,
                                    onSelect = {
                                        selectedLanguageIndex = index

                                    }
                                )
                                Spacer(modifier = Modifier.width(60.dp))
                            }
                        }*/
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick ={
                            /*if (SettingsState.getAddedCodes().size>0) {
                                SettingsState.selectedLanguage = SettingsState.getAddedCodes()[selectedLanguageIndex]
                            }else{
                                SettingsState.selectedLanguage = CodeLang.Default
                            }*/

                            onCreate()
                        }
                         ,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("Crear")
                    }
                }
            }
        }
    }
}



@Composable
fun ProjectOption(
    icon: Int,
    text: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    val borderColor = if (isSelected) Color.Magenta else Color.Transparent
    val borderWidth = if (isSelected) 2.dp else 1.dp

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color.Black,
            border = BorderStroke(width = borderWidth, color = borderColor),
            modifier = Modifier
                .size(85.dp)
                .clickable(onClick = onSelect)
                .padding(bottom = 5.dp)

        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = text,
                modifier = Modifier.padding(16.dp)
            )
        }
        Text(
            text = text,
            color = (if (isSelected) Color.Magenta else Color(0xFF03A9F4)),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview
@Composable
fun CodeDialogPreview() {

    CodeDialog(onDismiss = {}, onCreate = {})
    ProjectOption(R.drawable.languagelogo,"HTML",false,{})
}
