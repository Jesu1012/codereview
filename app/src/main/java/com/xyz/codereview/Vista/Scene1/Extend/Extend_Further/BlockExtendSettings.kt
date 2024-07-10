package com.xyz.codereview.Vista.Scene1.Extend.Extend_Further

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wakaztahir.codeeditor.model.CodeLang
import com.wakaztahir.codeeditor.theme.CodeThemeType
import com.wakaztahir.codeeditor.theme.Displayable
import com.xyz.codereview.R
import com.xyz.codereview.Controlador.SettingsState


val themeColors = listOf(
    Color(0xFF859900), // DefaultTheme
    Color(0xFFA7E22E), // MonokaiTheme
    Color(0xFFBD93F9), // DraculaTheme
    Color(0xFF66B3FF), // SerenityTheme
    Color(0xFFE74C3C), // MidnightCherryTheme
    Color(0xFFC5CAE9)  // PastelDreamTheme
)



@Composable
fun <T : Displayable> SelectionApp(
    items: Array<T>,
    selectedItems: List<T>,
    onCheckedChange: (T, Boolean) -> Unit
) {
    // Dividimos los elementos en dos listas, una para cada fila
    val (firstRowItems, secondRowItems) = items.withIndex().partition { it.index % 2 == 0 }

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .horizontalScroll(rememberScrollState())
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            firstRowItems.forEach { (index, item) ->
                val isChecked = selectedItems.contains(item)
                SelectionItem(
                    label = item.displayName,
                    color = item.color,
                    isChecked = isChecked,
                    width = 150.dp,
                    height = 70.dp
                ) { shouldBeChecked ->
                    onCheckedChange(item, shouldBeChecked)
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            secondRowItems.forEach { (index, item) ->
                val isChecked = selectedItems.contains(item)
                SelectionItem(
                    label = item.displayName,
                    color = item.color,
                    isChecked = isChecked,
                    width = 150.dp,
                    height = 70.dp
                ) { shouldBeChecked ->
                    onCheckedChange(item, shouldBeChecked)
                }
            }
        }
    }
}




@Composable
fun SelectionItem(
    label: String,
    color: Color,
    isChecked: Boolean,
    width: Dp,
    height: Dp,
    onCheckedChange: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .size(width, height)
            .background(color, shape = RoundedCornerShape(5.dp))
            .padding(5.dp)
            .clickable {
                onCheckedChange(!isChecked)
            }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                modifier = Modifier.align(Alignment.TopEnd)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), // Reemplaza con tu recurso de imagen
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.Center)
            )
            Text(
                text = label,
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(4.dp) // Optional padding for better text appearance
            )
        }
    }
}




@Composable
fun ThemeSelectionApp() {
    val selectedThemes = remember { mutableStateListOf<CodeThemeType>().apply { addAll(SettingsState.listSelectedTheme) } }

    SelectionApp(
        items = CodeThemeType.values(),
        selectedItems = selectedThemes,
        onCheckedChange = { item, shouldBeChecked ->
            val theme = item as CodeThemeType
            if (shouldBeChecked) {
                if (!selectedThemes.contains(theme)) {
                    selectedThemes.add(theme)
                    SettingsState.listSelectedTheme.add(theme)
                }
            } else {
                selectedThemes.remove(theme)
                SettingsState.listSelectedTheme.remove(theme)
            }
        }
    )
}

@Composable
fun LanguageSelectionApp() {
    val selectedLanguages = remember { mutableStateListOf<CodeLang>().apply { addAll(SettingsState.listSelectedLanguages) } }

    SelectionApp(
        items = CodeLang.values(),
        selectedItems = selectedLanguages,
        onCheckedChange = { item, shouldBeChecked ->
            val language = item as CodeLang
            if (shouldBeChecked) {
                if (!selectedLanguages.contains(language)) {
                    selectedLanguages.add(language)
                    SettingsState.listSelectedLanguages.add(language)
                }
            } else {
                selectedLanguages.remove(language)
                SettingsState.listSelectedLanguages.remove(language)
            }
        }
    )
}

@Composable
fun ContentGeneralScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {
        Text(
            text = "Languages of Programing",
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        LanguageSelectionApp()
        Text(
            text = "Preferred Programming Theme",
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        ThemeSelectionApp()
    }
}


@Composable
fun ContentLanguageScreen() {
    Text(
        text = "Language Settings",
        Modifier.fillMaxSize(),
        color = Color.DarkGray
    )
}

@Composable
fun ContentInstructionsScreen() {
    Text(
        text = "Building Instructions",
        Modifier.fillMaxSize(),
        color = Color.DarkGray
    )
}

@Composable
fun ContentLegalScreen() {
    Text(
        text = "Legal",
        Modifier.fillMaxSize(),
        color = Color.DarkGray
    )
}
@Preview
@Composable
fun LanguageSelectionAppPreview() {
    ContentGeneralScreen()
}