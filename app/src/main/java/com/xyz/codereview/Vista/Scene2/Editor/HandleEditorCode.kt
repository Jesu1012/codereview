package com.xyz.codereview.Vista.Scene2.Editor

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.github.difflib.DiffUtils
import com.github.difflib.patch.DeltaType
import com.xyz.codereview.Modelo.BoxState
import com.xyz.codereview.Modelo.BoxStateManager
import com.xyz.codereview.Modelo.BoxStateManagerSingleton
import com.xyz.codereview.Modelo.Server
import com.xyz.codereview.R
import com.xyz.codereview.Controlador.SettingsState
import com.xyz.codereview.Vista.Scene1.Extend.Extend_Further.NavigationItem
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope.coroutineContext

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun BlockHandleEditorCode(
    modifier: Modifier,
    colorTheme: Color,
    orientation: Int = 0
) {
    var codeLastCheck by remember { mutableStateOf("") }
    var deleteBlock by remember { mutableStateOf(emptyList<Pair<Int, String>>()) }
    var splitterOffset by remember { mutableStateOf(1f) }
    val animatedSplitterOffset by animateFloatAsState(targetValue = splitterOffset)
    val minHeight = 100.dp
    val boxStateManager = remember { BoxStateManagerSingleton.getInstance(SettingsState.elementSelect!!)}

    BoxWithConstraints(modifier = modifier
        .padding(end = 20.dp)
        .fillMaxSize()) {
        val totalHeight = maxHeight
        val totalWidth = maxWidth
        val draggableHeight = totalHeight - (2 * minHeight)
        val draggableWidth = totalWidth - (2 * minHeight)

        val draggableModifier = if (orientation == 0) {
            Modifier
                .fillMaxWidth()
                .height(25.dp)
                .background(Color.Black)
                .pointerInput(Unit) {
                    detectVerticalDragGestures(
                        onDragStart = {
                            CoroutineScope(Dispatchers.Main).launch {
                                //boxStateManager.deselectLastChecked()
                                boxStateManager.deselectLastChecked()
                                Log.d("HandleEditorCode", "onDragStart")
                            }
                        }
                    ) { change, dragAmount ->
                        val adjustedDragAmount = dragAmount / 4
                        val newOffset = splitterOffset + adjustedDragAmount / draggableHeight.value
                        splitterOffset = newOffset.coerceIn(0f, 1f)
                    }
                }
        } else {
            Modifier
                .fillMaxHeight()
                .width(25.dp)
                .background(Color.Black)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragStart = {
                            CoroutineScope(Dispatchers.Main).launch {
                                boxStateManager.deselectLastChecked()
                                Log.d("HandleEditorCode", "onDragStart")
                            }
                        }
                    ) { change, dragAmount ->
                        val adjustedDragAmount = dragAmount / 4
                        val newOffset = splitterOffset + adjustedDragAmount / draggableWidth.value
                        splitterOffset = newOffset.coerceIn(0f, 1f)
                    }
                }
        }

        if (orientation == 0) {
            Column {
                if (animatedSplitterOffset > 0f) {
                    Box(
                        modifier = Modifier
                            .weight(animatedSplitterOffset)
                            .fillMaxWidth()
                            .background(Color.Black, shape = RoundedCornerShape(5.dp))
                    ) {
                        if (animatedSplitterOffset > 0.7f) {
                            HandleEditorCode(
                                modifier = Modifier,
                                colorTheme = colorTheme,
                                { splitterOffset = 0f },
                                onCodeChange = { newCode ->
                                    codeLastCheck = newCode
                                },
                                onDeleteBlockChange = { newDeleteBlock ->
                                    deleteBlock = newDeleteBlock
                                },
                                boxStateManager = boxStateManager

                            )
                        } else {
                            CircularProgressIndicator()
                        }
                    }
                }

                Box(modifier = draggableModifier.align(Alignment.CenterHorizontally)) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(Color.White.copy(0.5f), shape = RoundedCornerShape(40))
                            .align(Alignment.Center)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .background(Color(0xFFE74C3C), shape = CircleShape)
                            )
                            Spacer(modifier = Modifier.width(1.dp))
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .background(Color(0xFFF39C12), shape = CircleShape)
                            )
                            Spacer(modifier = Modifier.width(1.dp))
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .background(Color(0xFF27AE60), shape = CircleShape)
                            )
                        }
                    }
                }

                if (animatedSplitterOffset < 1f) {
                    Box(
                        modifier = Modifier
                            .weight(1f - animatedSplitterOffset)
                            .fillMaxWidth()
                            .background(Color.Black, shape = RoundedCornerShape(5.dp))
                            .padding(vertical = 20.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            if (animatedSplitterOffset < 0.8f){
                                IconButton(onClick = {
                                    CoroutineScope(coroutineContext).launch {
                                        boxStateManager.deselectLastChecked()
                                        splitterOffset = 1f
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Cerrar",
                                        tint = Color.White
                                    )
                                }
                                DisplayCodeEditorView(
                                    fullCode = codeLastCheck,
                                    deleteBlock = deleteBlock
                                )
                            }else{
                                CircularProgressIndicator()
                            }

                        }
                    }
                }
            }
        } else {
            Row {
                if (animatedSplitterOffset > 0f) {
                    Box(
                        modifier = Modifier
                            .weight(animatedSplitterOffset)
                            .fillMaxHeight()
                            .background(Color.Black, shape = RoundedCornerShape(5.dp))
                    ) {
                        if (animatedSplitterOffset > 0.7f) {
                            HandleEditorCode(
                                modifier = Modifier,
                                colorTheme = colorTheme,
                                { splitterOffset = 0f },
                                onCodeChange = { newCode ->
                                    codeLastCheck = newCode
                                },
                                onDeleteBlockChange = { newDeleteBlock ->
                                    deleteBlock = newDeleteBlock
                                },
                                boxStateManager = boxStateManager
                            )
                        } else {
                            CircularProgressIndicator()
                        }
                    }
                }

                Box(modifier = draggableModifier.align(Alignment.CenterVertically)) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(Color.White.copy(0.5f), shape = RoundedCornerShape(40))
                            .align(Alignment.Center)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .background(Color(0xFFE74C3C), shape = CircleShape)
                            )
                            Spacer(modifier = Modifier.width(1.dp))
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .background(Color(0xFFF39C12), shape = CircleShape)
                            )
                            Spacer(modifier = Modifier.width(1.dp))
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .background(Color(0xFF27AE60), shape = CircleShape)
                            )
                        }
                    }
                }

                if (animatedSplitterOffset < 1f) {
                    Box(
                        modifier = Modifier
                            .weight(1f - animatedSplitterOffset)
                            .fillMaxHeight()
                            .background(Color.Black, shape = RoundedCornerShape(5.dp))
                            .padding(horizontal = 20.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            if (animatedSplitterOffset < 0.8f){
                                IconButton(onClick = {
                                    CoroutineScope(coroutineContext).launch {
                                        boxStateManager.deselectLastChecked()
                                        splitterOffset = 1f
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Cerrar",
                                        tint = Color.White
                                    )
                                }
                                DisplayCodeEditorView(
                                    fullCode = codeLastCheck,
                                    deleteBlock = deleteBlock
                                )
                            }else{
                                CircularProgressIndicator()
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HandleEditorCode(
    modifier: Modifier,
    colorTheme: Color,
    onChange: () -> Unit,
    onCodeChange: (String) -> Unit,
    onDeleteBlockChange: (List<Pair<Int, String>>) -> Unit,
    boxStateManager: BoxStateManager
) {
    val clipboardManager: ClipboardManager = LocalContext.current.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 10.dp)
                    .horizontalScroll(scrollState),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            coroutineScope.launch {
                                scrollState.scrollBy(0f)
                            }
                        }
                    }
                ) {
                    boxStateManager.boxStates.forEachIndexed { index, state ->
                        DynamicBox(
                            index = index, // Pasamos el índice aquí
                            modifier = if (isPortrait)
                                Modifier
                                    .fillMaxWidth(0.8f)
                                    .aspectRatio(0.5f)
                            else Modifier
                                .fillMaxHeight(0.8f)
                                .aspectRatio(2f),
                            colorTheme = colorTheme,
                            clipboardManager = clipboardManager,
                            state = state,
                            onStateChange = { newState ->
                                boxStateManager.updateBoxState(index, newState)
                            },
                            coroutineScope = coroutineScope,
                            scrollState = scrollState,
                            boxStateManager = boxStateManager
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    // Botón para agregar más Box
                    NavigationItem(
                        icon = painterResource(id = R.drawable.baseline_add_box_24),
                        selected = true,
                        modifier = Modifier.clickable {
                            boxStateManager.addBoxState(BoxState(pasteLocalState = false, clipboardText = "Clipboard is empty"))
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            // Espacio para arrastrar y mover el Row
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(colorTheme.copy(0.2f))
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            coroutineScope.launch {
                                scrollState.scrollBy(-1.5f * dragAmount.x)
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text("Arrastra aquí para mover", color = colorTheme)
            }
        }

        if (boxStateManager.showCodeDifferent) {
            val selectedBoxStates = boxStateManager.getSelectedBoxStates()
            if (selectedBoxStates.size >= 2) {
                val differences = getDeleteBlock(
                    selectedBoxStates.first().clipboardText,
                    selectedBoxStates.last().clipboardText
                )
                onChange()
                onCodeChange(boxStateManager.getBoxStateBySecondCheckedIndex()?.clipboardText ?: "")
                onDeleteBlockChange(differences)
            }
        }
    }
}

@Composable
fun DynamicBox(
    index: Int,
    modifier: Modifier,
    colorTheme: Color,
    clipboardManager: ClipboardManager,
    state: BoxState,
    onStateChange: (BoxState) -> Unit,
    coroutineScope: CoroutineScope,
    scrollState: ScrollState,
    boxStateManager: BoxStateManager
) {
    var seeListServer by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .border(5.dp, color = colorTheme, shape = RoundedCornerShape(25.dp)),
        contentAlignment = Alignment.Center
    ) {
        if (!state.pasteLocalState) {
            Column (
                modifier = Modifier.fillMaxSize()
                    ,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            val newState = state.copy(pasteLocalState = true)
                            val clipData: ClipData? = clipboardManager.primaryClip
                            val updatedClipboardText = if (clipData != null && clipData.itemCount > 0) {
                                clipData.getItemAt(0).text.toString()
                            } else {
                                "Clipboard is empty"
                            }
                            onStateChange(newState.copy(clipboardText = updatedClipboardText))
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(colorTheme.copy(0.8f)),
                        modifier = Modifier.size(100.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_paste),
                            contentDescription = "",
                            modifier = Modifier
                                .size(80.dp)
                                .padding(8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(5.dp))

                    Button(
                        onClick = {
                            seeListServer = !seeListServer
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(colorTheme.copy(0.8f)),
                        modifier = Modifier.size(100.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_computer),
                            contentDescription = "",
                            modifier = Modifier
                                .size(80.dp)
                                .padding(8.dp)
                        )
                    }
                }
                if (seeListServer) {
                    LazyRow (
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 5.dp)

                    ) {

                        Server.messag.toList().forEach { message ->
                            item {
                                Button(
                                    onClick = {
                                        val newState = state.copy(pasteLocalState = true)
                                        onStateChange(newState.copy(clipboardText = message))
                                    },
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.buttonColors(colorTheme.copy(0.8f)),
                                    modifier = Modifier.size(100.dp)
                                ) {
                                    Text(
                                        text = message,
                                        color = Color.White,
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 1
                                    )
                                }
                                Spacer(modifier = Modifier.width(5.dp))
                            }
                        }
                    }
                }


            }

        } else {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            onStateChange(state.copy(pasteLocalState = false, clipboardText = "", isChecked = false))
                        },
                        modifier = Modifier.padding(2.dp),
                        colors = ButtonDefaults.buttonColors(colorTheme.copy(0.4f))
                    ) {
                        Text("Clean", color = Color.White)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Checkbox(
                        checked = state.isChecked,
                        onCheckedChange = { isChecked ->
                            val newState = state.copy(isChecked = isChecked)
                            onStateChange(newState)
                            boxStateManager.updateBoxState(index, newState) // Usamos el índice aquí
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = colorTheme.copy(1f),
                            uncheckedColor = Color.White
                        )
                    )
                }

                DisplayCodeEditor(
                    fullCode = state.clipboardText,
                    coroutineScope = coroutineScope,
                    scrollState = scrollState
                )
            }
        }
    }
}

fun getDeleteBlock(text1: String, text2: String): List<Pair<Int, String>> {
    val lines1 = text1.split("\n")
    val lines2 = text2.split("\n")
    val patch = DiffUtils.diff(lines1, lines2)
    val deleteBlock = mutableListOf<Pair<Int, String>>()

    patch.deltas.forEach { delta ->
        if (delta.type == DeltaType.DELETE || delta.type == DeltaType.CHANGE) {
            delta.source.lines.forEachIndexed { index, line ->
                deleteBlock.add(Pair(delta.source.position + index + 1, line))
            }
        }
    }

    return deleteBlock
}


