package com.xyz.codereview.Vista.Scene1.Extend.Extend_Further


import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xyz.codereview.Vista.Scene1.Screen
import com.wakaztahir.codeeditor.model.CodeLang
import com.wakaztahir.codeeditor.theme.CodeThemeType
import com.xyz.codereview.Modelo.BoxStateManagerSingleton
import com.xyz.codereview.Modelo.Element
import com.xyz.codereview.Modelo.GameViewModel
import com.xyz.codereview.R
import com.xyz.codereview.SettingsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class NavigationPanel {
    NONE,
    Language,
    Theme
}

@Composable
fun BlockCraft(viewModel: GameViewModel) {
    val dynamicElementsPrincipal by viewModel.elementsDynamicPrincipal.collectAsState()
    val dynamicElementsExtra by viewModel.elementsDynamicExtra.collectAsState()
    var visiblePanel by remember { mutableStateOf(NavigationPanel.NONE) }
    var selectedPanel by remember { mutableStateOf(NavigationPanel.NONE) }
    var requestedPanel by remember { mutableStateOf(NavigationPanel.NONE) }
    val panelWidth by animateDpAsState(targetValue = if (visiblePanel == NavigationPanel.NONE) 0.dp else 70.dp)
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val currentScreen by remember {
        derivedStateOf { SettingsState.selectedScreen }
    }

    var surfaceWidth by remember { mutableStateOf(0) }
    var surfaceHeight by remember { mutableStateOf(0) }

    LaunchedEffect(currentScreen) {
        viewModel.changeElements("")
        selectedPanel = NavigationPanel.NONE
        Log.d("BlockCraft", "currentScreen: $currentScreen")
    }
    LaunchedEffect(requestedPanel) {
        if (requestedPanel != NavigationPanel.NONE) {
            visiblePanel = NavigationPanel.NONE
            delay(150)  // Añade un pequeño retraso para la animación de cierre
            visiblePanel = requestedPanel
        } else {
            visiblePanel = NavigationPanel.NONE  // Cierra el panel de extensión
        }
    }

    Surface(
        modifier = Modifier.fillMaxHeight(),  // Llena la altura disponible
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top
            ) {
                LeftTools(
                    selectedPanel = selectedPanel,
                    onPanelChange = { panel ->
                        when (panel) {
                            NavigationPanel.Language -> viewModel.changeElements("code")
                            NavigationPanel.Theme -> viewModel.changeElements("theme")
                            else -> {
                                viewModel.changeElements("")
                            }
                        }
                        if (selectedPanel == panel && visiblePanel == panel) {
                            requestedPanel = NavigationPanel.NONE // Cierra el panel de extensión si ya está abierto
                        } else if (selectedPanel == panel) {
                            requestedPanel = panel // Abre el panel de extensión si ya está seleccionado
                        } else {
                            selectedPanel = panel // Muestra el panel principal correspondiente
                            requestedPanel = NavigationPanel.NONE
                        }
                    },
                    onDelete = {
                        viewModel.deleteAllElements()
                    }
                )

                Box(
                    modifier = Modifier
                        .width(panelWidth)
                        .fillMaxHeight()
                        .background(Color.DarkGray)
                        .padding(8.dp)
                ) {

                    if (panelWidth > 0.dp) {
                        when (visiblePanel) {
                            NavigationPanel.Language -> ExtendPanelLanguage(coroutineScope, scrollState = scrollState, viewModel = viewModel)
                            NavigationPanel.Theme -> ExtendPanelTheme(coroutineScope, scrollState = scrollState, viewModel = viewModel)
                            else -> {}
                        }
                    }
                }

                Spacer(modifier = Modifier.width(0.5.dp))

                Surface(
                    modifier = Modifier
                        .weight(1f)  // Asegura que InfiniteCraftApp tome todo el espacio disponible
                        .fillMaxHeight()
                        .onGloballyPositioned { coordinates ->
                            surfaceWidth = coordinates.size.width
                            surfaceHeight = coordinates.size.height
                        },  // Llena la altura disponible
                ) {
                    Box(
                        modifier = Modifier

                            .fillMaxSize()
                            .background(Color.Black)
                    ) {
                        // Usamos un Layout personalizado para asegurar que ambos bloques de contenido coexistan
                        CustomLayout(
                            dynamicElementsExtra = dynamicElementsExtra,
                            dynamicElementsPrincipal = dynamicElementsPrincipal,
                            scrollState = scrollState,
                            viewModel = viewModel,
                            onPanelChange = { panel ->
                                requestedPanel = panel
                                selectedPanel = panel
                                when (panel) {
                                    NavigationPanel.Language -> viewModel.changeElements("code")
                                    NavigationPanel.Theme -> viewModel.changeElements("theme")
                                    else -> {
                                        viewModel.changeElements("")
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CustomLayout(
    dynamicElementsExtra: List<Element>,
    dynamicElementsPrincipal: List<Element>,
    scrollState: ScrollState,
    viewModel: GameViewModel,
    onPanelChange: (NavigationPanel) -> Unit
) {


    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            dynamicElementsPrincipal.forEach { element ->
                when (element.type) {
                    "code" -> {
                        ElementBoxStatic(scrollState, element, viewModel, Color.White, onPanelChange) {
                            ElementBoxCode(CodeLang.entries.first { it.displayName == element.name })
                        }
                    }
                    "theme" -> {
                        ElementBoxStatic(scrollState, element, viewModel, Color.White, onPanelChange) {
                            ElementBoxTheme(CodeThemeType.entries.first { it.displayName == element.name })
                        }
                    }
                    else -> {}
                }
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        dynamicElementsExtra.forEach { element ->
            when (element.type) {
                "code" -> {
                    ElementBoxDynamic(element, viewModel, Color.White, onPanelChange = {}
                    ) {
                        ElementBoxCode(CodeLang.entries.first { it.displayName == element.name })
                    }
                }
                "theme" -> {
                    ElementBoxDynamic(element, viewModel, Color.White, onPanelChange = { }
                    ) {
                        ElementBoxTheme(CodeThemeType.entries.first { it.displayName == element.name })
                    }
                }
                else -> {
                    ElementBoxDynamic(element, viewModel, Color.White, onPanelChange = { }
                    ) {
                        CodeBlockWithUI(codeText = element.name, element)
                    }
                }
            }
        }
    }

}



@Composable
fun CodeBlockWithUI(codeText: String,element: Element) {
    Column(
        modifier = Modifier
            .background(color = element.color, shape = RoundedCornerShape(8.dp))
            .padding(10.dp)
            .fillMaxSize()
    ) {
        // Tres bolitas en la parte superior
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(Color.Red, shape = CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(Color.Yellow, shape = CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(Color.Green, shape = CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {

                SettingsState.selectedLanguage = CodeLang.entries.first { it.displayName == element.name }
                SettingsState.selectedTheme = element.theme;
                SettingsState.elementSelect = element
                Log.d("CodeBlockWithUI", "CodeBlockWithUI: ${SettingsState.selectedLanguage} :: ${SettingsState.selectedTheme}")
                //onScreenChange(Screen.EditorPrincipal)
                SettingsState.selectedScreen = Screen.EditorPrincipal
                                 },
                modifier = Modifier.size(50.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_play),
                    contentDescription = "Play",
                    tint = Color.Black,
                    modifier = Modifier.fillMaxSize()
                )
            }
            CodeBlock(codeText)


        }
    }
}

@Composable
fun CodeBlock(codeText: String) {
    val lines = codeText.split("\n")
    Column {
        lines.forEach { line ->
            Text(
                text = line,
                color = Color.White,
                fontFamily = FontFamily.Monospace,
                fontSize = 14.sp,
                modifier = Modifier.padding(2.dp)
            )
        }
    }
}



@Composable
fun ElementBoxStatic(
    scrollState: ScrollState,
    element: Element,
    viewModel: GameViewModel,
    backgroundColor: Color = element.color,
    onPanelChange: (NavigationPanel) -> Unit,
    content: @Composable () -> Unit = {}
) {
    var offset by remember { mutableStateOf(element.offset) }
    var initialPositionY by remember { mutableStateOf(0f) }
    var initialOffsetSet by remember { mutableStateOf(false) }
    var previousScrollValue by remember { mutableStateOf(0) }

    LaunchedEffect(scrollState.value) {
        if (initialOffsetSet) {
            val scrollDifference = scrollState.value - previousScrollValue
            if (scrollDifference > 0) {
                initialPositionY -= scrollDifference.toFloat()
            } else {
                initialPositionY -= scrollDifference.toFloat() // Sumar diferencia negativa efectivamente suma
            }
            previousScrollValue = scrollState.value
            Log.d("ElementBoxStatic", "scrollDifference: $scrollState.value")
        }
    }


    Box(
        modifier = Modifier
            .size(84.dp, 64.dp)
            .onGloballyPositioned { coordinates ->
                val position = coordinates.positionInParent()
                if (!initialOffsetSet) {
                    initialPositionY = position.y
                    previousScrollValue = scrollState.value
                    initialOffsetSet = true
                }
            }
            .offset { IntOffset(offset.x.toInt(), offset.y.toInt()) }
            .background(color = backgroundColor, shape = RoundedCornerShape(8.dp))
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        //initialPositionY = offset.y
                    },
                    onDragEnd = {
                        viewModel.addElementExtra(
                            element,
                            offset.copy(y = offset.y + initialPositionY)
                        )
                        viewModel.onElementDrop(element, element.offset)
                        onPanelChange(NavigationPanel.NONE)
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        offset += dragAmount
                        viewModel.updateElementOffset(
                            element.id,
                            offset.copy(y = offset.y + initialPositionY)
                        )
                    }
                )
            }
    ) {
        content()
//        Text(
//            text = "(${offset.x.toInt()}, ${(offset.y + initialPositionY).toInt()})",
//            color = Color.Red,
//            fontSize = 12.sp,
//            modifier = Modifier.align(Alignment.BottomCenter)
//        )
    }
}

@Composable
fun ElementBoxDynamic(
    element: Element,
    viewModel: GameViewModel,
    backgroundColor: Color = element.color,
    onPanelChange: (NavigationPanel) -> Unit,
    content: @Composable () -> Unit = {}
) {
    var offset by remember { mutableStateOf(element.offset) }
    var sizeBox by remember { mutableStateOf(Offset.Zero) }
    if (element.type == "code") {
        sizeBox = Offset(84f, 64f)
    } else if (element.type == "theme") {
        sizeBox = Offset(160f, 100f)
    } else {
        sizeBox = Offset(180f, 120f)
    }
    Box(
        modifier = Modifier
            .size(sizeBox.x.dp, sizeBox.y.dp)
            .offset { IntOffset(offset.x.toInt(), offset.y.toInt()) }
            .background(color = backgroundColor, shape = RoundedCornerShape(8.dp))
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        viewModel.updateElementOffset(element.id, offset)
                        viewModel.onElementDrop(element, offset)
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        offset += dragAmount
                        //viewModel.updateElementOffset(element.id, offset)
                    }
                )
            }
    ) {
        content()
//        Text(
//            text = "(${offset.x.toInt()}, ${offset.y.toInt()})",
//            color = Color.Red,
//            fontSize = 12.sp,
//            modifier = Modifier.align(Alignment.BottomCenter)
//        )
    }
}

@Composable
fun ElementBoxTheme(theme: CodeThemeType) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(theme.color, shape = RoundedCornerShape(8.dp))

    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Start
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
        Spacer(modifier = Modifier.height(1.dp))
        // Aquí puedes agregar el contenido de tu ElementBoxTheme
        Text(
            text = theme.displayName,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun ElementBoxCode(code: CodeLang) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Imagen de la bolita central
        Box(
            modifier = Modifier
                .size(48.dp) // Tamaño de la bolita central
                .background(color = code.color, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_code), // Reemplaza con tu recurso de imagen
                contentDescription = "Central Circle",
                modifier = Modifier.size(24.dp) // Tamaño del icono
            )
        }
    }
}

@Composable
fun LeftTools(
    selectedPanel: NavigationPanel,
    modifier: Modifier = Modifier,
    onPanelChange: (NavigationPanel) -> Unit,
    onDelete:() -> Unit
) {
    Column(
        modifier = modifier
            .background(Color(0xFF424242))
            .fillMaxHeight()
            .border(width = 0.5.dp, color = Color.Black)
            .width(50.dp)  // Ajuste el ancho según sea necesario
    ) {
        NavigationItemSpecial(
            icon = painterResource(id = R.drawable.ic_ajustes),
            selected = true,
            modifier = Modifier.clickable {
                SettingsState.selectedScreen = Screen.GeneralSettings
            }
        )
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            item {
                // Elemento de navegación para la selección de "Language"
                NavigationItem(
                    icon = painterResource(id = R.drawable.code),
                    selected = selectedPanel == NavigationPanel.Language,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clickable {
                            onPanelChange(NavigationPanel.Language)
                        }
                )
            }
            item {
                // Elemento de navegación para la selección de "Theme"
                NavigationItem(
                    icon = painterResource(id = R.drawable.theme),
                    selected = selectedPanel == NavigationPanel.Theme,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clickable {
                            onPanelChange(NavigationPanel.Theme)
                        }
                )
            }
            item {
                NavigationItem(
                    icon = painterResource(id = R.drawable.baseline_block_24),
                    selected = selectedPanel == NavigationPanel.NONE,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clickable {
                            onPanelChange(NavigationPanel.NONE)
                        }
                )
            }
        }

        // Espacio para expandir y empujar los elementos inferiores hacia abajo
        Spacer(modifier = Modifier.height(8.dp))

        // Elemento de navegación especial para cerrar paneles
        NavigationItemSpecial(
            icon = painterResource(id = R.drawable.ic_delete),
            selected = true,
            modifier = Modifier.clickable {
                onDelete()
                BoxStateManagerSingleton.removeAllInstance()
            }
        )
    }
}

@Composable
fun ExtendPanelLanguage(coroutineScope: CoroutineScope, scrollState: ScrollState, viewModel: GameViewModel) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    coroutineScope.launch {
                        scrollState.scrollBy(-dragAmount.y)
                    }
                }
            }
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            var newElement by remember { mutableStateOf<Element?>(null) }
            SettingsState.listSelectedLanguages.forEach { language ->
                PinButton(
                    pinNumber = language.displayName,
                    backgroundColor = language.color
                )
            }
        }
    }
}

@Composable
fun ExtendPanelTheme(coroutineScope: CoroutineScope,scrollState: ScrollState, viewModel: GameViewModel) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    coroutineScope.launch {

                        scrollState.scrollBy(-dragAmount.y)

                    }
                }
            }
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            var newElement by remember { mutableStateOf<Element?>(null) }
            SettingsState.listSelectedTheme.forEach { theme ->
                PinButton(
                    pinNumber = theme.displayName,
                    backgroundColor = theme.color
                )
            }
        }
    }
}

@Composable
fun PinButton(pinNumber: String, backgroundColor: Color) {


    Box(
        modifier = Modifier
            .padding(0.5.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(30.dp) // Ajuste del tamaño del círculo
                    .clip(CircleShape)
                    .background(backgroundColor)
                    .wrapContentSize(Alignment.Center)
            )
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = pinNumber,
                fontSize = 12.sp, // Ajuste del tamaño de la fuente
                fontWeight = FontWeight.Normal, // Ajuste del peso de la fuente
                color = Color.White, // Ajuste del color de la fuente
                maxLines = 1, // Limita el texto a una línea
                overflow = TextOverflow.Ellipsis, // Añade puntos suspensivos si el texto es demasiado largo
                modifier = Modifier.padding(horizontal = 4.dp) // Padding horizontal para darle espacio al texto
            )
        }
    }
}

@Composable
fun NavigationItem(icon: Painter, selected: Boolean, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(50.dp)
            .background(
                color = if (selected) Color(0xFFFFA000) else Color(0xFFFFFFFF),
                shape = CircleShape
            )
            .padding(4.dp)
    ) {
        Image(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun NavigationItemSpecial(icon: Painter, selected: Boolean, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(50.dp)
            .background(
                color = if (selected) Color(0xFF009688) else Color(0xFF424242),
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Image(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBlockCraft() {
    //BlockCraft()
}
@Preview(showBackground = true)
@Composable
fun PreviewCodeBlockWithUI() {
    val codeText = """
        export const sortBy = (...cbs) => (a, b) => {
            for (let i = 0; i < cbs.length; i++) {
                const cb = cbs[i].desc ? cbs[i].cb : cbs[i];
                const aa = cb(a);
                const bb = cb(b);
                const diff = cbs[i].desc
                    ? isString(aa)
                        ? bb.localeCompare(aa)
                        : aa - bb
                    : isString(bb)
                        ? aa.localeCompare(bb)
                        : aa - bb;
                if (diff !== 0) return diff;
            }
            return 0;
        };
        export const desc = cb => ({ desc: true, cb });
    """.trimIndent()

    CodeBlockWithUI(codeText = codeText,element = Element(name = "Mixed_JavaScript_TypeScript", color = Color(0xFFE74C3C), type = "mixed"))
}