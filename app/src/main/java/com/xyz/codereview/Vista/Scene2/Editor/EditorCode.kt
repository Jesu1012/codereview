package com.xyz.codereview.Vista.Scene2.Editor

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.wakaztahir.codeeditor.prettify.PrettifyParser
import com.wakaztahir.codeeditor.utils.parseCodeAsAnnotatedString
import com.xyz.codereview.SettingsState
import com.xyz.codereview.Vista.Scene1.Extend.Extend_Further.Tile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DisplayCodeEditor(fullCode: String, coroutineScope: CoroutineScope, scrollState: ScrollState) {
    val language by remember { mutableStateOf(SettingsState.selectedLanguage) }

    val themeState by remember { mutableStateOf(SettingsState.selectedTheme) }
    val parser = remember { PrettifyParser() }
    val theme = remember(themeState) { themeState.theme }

    fun parse(code: String): AnnotatedString {
        return parseCodeAsAnnotatedString(
            parser = parser,
            theme = theme,
            lang = language,
            code = code
        )
    }

    var textFieldValue by remember { mutableStateOf(TextFieldValue(parse(fullCode))) }
    val (lineCount, setLineCount) = remember { mutableStateOf(0) }
    val lineHeight = 20.sp
    val verticalScrollState = rememberScrollState()
    val horizontalScrollState = rememberScrollState()

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {

        }

        Row(modifier = Modifier.weight(1f)) {
            Column(
                modifier = Modifier
                    .width(40.dp)
                    .verticalScroll(verticalScrollState)
            ) {
                for (line in 1..lineCount) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .height(with(LocalDensity.current) { lineHeight.toDp() })
                            .clickable {

                            }
                    ) {

                        Text(
                            text = line.toString(),
                            color = Color.White,
                            style = LocalTextStyle.current.copy(lineHeight = lineHeight),
                            textAlign = TextAlign.Right,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(verticalScrollState)
                    .horizontalScroll(horizontalScrollState)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            coroutineScope.launch {
                                if (kotlin.math.abs(dragAmount.x) > kotlin.math.abs(dragAmount.y)) {
                                    // Horizontal drag
                                    horizontalScrollState.scrollBy(-1.2f*dragAmount.x)
                                } else {
                                    // Vertical drag
                                    verticalScrollState.scrollBy(-1.2f*dragAmount.y)
                                }
                                scrollState.scrollBy(0f)
                            }
                        }
                    }
            ) {
                BasicTextField(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 10.dp),
                    value = textFieldValue,
                    onValueChange = {
                        textFieldValue = it.copy(annotatedString = parse(it.text))
                    },
                    onTextLayout = { result ->
                        setLineCount(result.lineCount)
                    },
                    textStyle = LocalTextStyle.current.copy(lineHeight = lineHeight),
                    enabled = false
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(8.dp)
                .horizontalScroll(rememberScrollState())
        ) {

        }
    }
}

@Composable
fun DisplayCodeEditorView(fullCode: String, deleteBlock: List<Pair<Int, String>>) {
    val language by remember { mutableStateOf(SettingsState.selectedLanguage) }
    val themeState by remember { mutableStateOf(SettingsState.selectedTheme) }
    val parser = remember { PrettifyParser() }
    val theme = remember(themeState) { themeState.theme }

    fun parse(code: String): AnnotatedString {
        return parseCodeAsAnnotatedString(
            parser = parser,
            theme = theme,
            lang = language,
            code = code
        )
    }

    var textFieldValue by remember { mutableStateOf(TextFieldValue(parse(fullCode))) }
    val (lineCount, setLineCount) = remember { mutableStateOf(0) }
    val lineHeight = 20.sp
    val verticalScrollState = rememberScrollState()
    val horizontalScrollState = rememberScrollState()

    val highlightedLines = remember { mutableStateMapOf<Int, Boolean>() }
    val tempDraggedLines = remember { mutableStateMapOf<Int, Boolean>() }
    val visitedLines = remember { mutableSetOf<Int>() }

    val density = LocalDensity.current

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {}

        Row(modifier = Modifier.weight(1f)) {
            Column(
                modifier = Modifier
                    .width(50.dp)
                    .verticalScroll(verticalScrollState)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                val line = (offset.y / density.run { lineHeight.toPx() }).toInt() + 1
                                if (deleteBlock.any { it.first == line }) {
                                    toggleLineHighlight(line, highlightedLines, tempDraggedLines, visitedLines)
                                }
                            },
                            onDrag = { change, _ ->
                                val line = (change.position.y / density.run { lineHeight.toPx() }).toInt() + 1
                                if (deleteBlock.any { it.first == line } && !visitedLines.contains(line)) {
                                    toggleLineHighlight(line, highlightedLines, tempDraggedLines, visitedLines)
                                }
                            },
                            onDragEnd = {
                                visitedLines.clear()
                            }
                        )
                    }
            ) {
                for (line in 1..lineCount) {
                    val isRedLine = deleteBlock.any { it.first == line }
                    val isHighlighted = highlightedLines[line] == true
                    val backgroundColor = if (isRedLine) Color(0x4DFF0000) else Color.Black // Color rojo suave

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .height(with(density) { lineHeight.toDp() })
                            .background(backgroundColor) // Aplicar color de fondo
                            .clickable {
                                if (isRedLine) {
                                    highlightedLines[line] = !(highlightedLines[line] ?: false)
                                }
                            }
                    ) {
                        Text(
                            text = line.toString(),
                            color = Color.White,
                            style = LocalTextStyle.current.copy(lineHeight = lineHeight),
                            textAlign = TextAlign.Right,
                            modifier = Modifier.weight(1f)
                        )
                        if (isHighlighted) {
                            Canvas(modifier = Modifier.size(10.dp).padding(start = 5.dp)) {
                                drawCircle(color = Color.Red, radius = 5.dp.toPx())
                            }
                        } else {
                            Spacer(modifier = Modifier.size(10.dp).padding(start = 5.dp))
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(verticalScrollState)
                    .horizontalScroll(horizontalScrollState)
            ) {
                val annotatedString = buildAnnotatedString {
                    fullCode.lines().forEachIndexed { index, line ->
                        val lineNumber = index + 1
                        val deleteLine = deleteBlock.find { it.first == lineNumber }
                        if (highlightedLines[lineNumber] == true && deleteLine != null) {
                            withStyle(style = SpanStyle(background = Color(0x4DFF0000), color = Color.White)) { // Color de fondo rojo suave
                                append(deleteLine.second)
                            }
                        } else {
                            append(parse(line))
                        }
                        append("\n")
                    }
                }

                BasicTextField(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 10.dp),
                    value = textFieldValue.copy(annotatedString = annotatedString),
                    onValueChange = {
                        textFieldValue = it.copy(annotatedString = parse(it.text))
                    },
                    onTextLayout = { result ->
                        setLineCount(result.lineCount)
                    },
                    textStyle = LocalTextStyle.current.copy(lineHeight = lineHeight),
                    enabled = false
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(8.dp)
                .horizontalScroll(rememberScrollState())
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                val ranges = calculateRanges(deleteBlock.map { it.first })
                ranges.forEach { range ->
                    val name = if (range.size > 1) {
                        "Rango: ${range.first()}-${range.last()}"
                    } else {
                        "Rango: ${range.first()}"
                    }
                    val width = if (range.size > 1) 150.dp else 70.dp
                    Tile(name = name, color = Color(0xFFF25022), width = width, height = 70.dp) {
                        range.forEach { line ->
                            highlightedLines[line] = !(highlightedLines[line] ?: false)
                        }
                    }
                }
            }
        }
    }
}

fun calculateRanges(numbers: List<Int>): List<List<Int>> {
    if (numbers.isEmpty()) return emptyList()
    val sortedNumbers = numbers.sorted()
    val ranges = mutableListOf<List<Int>>()
    var rangeStart = sortedNumbers.first()
    var currentRange = mutableListOf(rangeStart)

    for (i in 1 until sortedNumbers.size) {
        if (sortedNumbers[i] == sortedNumbers[i - 1] + 1) {
            currentRange.add(sortedNumbers[i])
        } else {
            ranges.add(currentRange)
            currentRange = mutableListOf(sortedNumbers[i])
        }
    }
    ranges.add(currentRange)
    return ranges
}

private fun toggleLineHighlight(
    line: Int,
    highlightedLines: MutableMap<Int, Boolean>,
    tempDraggedLines: MutableMap<Int, Boolean>,
    visitedLines: MutableSet<Int>
) {
    val isHighlighted = !(highlightedLines[line] ?: false)
    highlightedLines[line] = isHighlighted
    tempDraggedLines[line] = isHighlighted
    visitedLines.add(line)
}


































