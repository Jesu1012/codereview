package com.xyz.codereview.Vista

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xyz.codereview.Modelo.GameViewModel
import com.xyz.codereview.R
import com.xyz.codereview.Controlador.SettingsState
import com.xyz.codereview.Modelo.*
import com.xyz.codereview.Vista.Scene1.Base.HeadContent
import com.xyz.codereview.Vista.Scene1.Base.*
import com.xyz.codereview.Vista.Scene1.Extend.Extend_Further.BlockConnection
import com.xyz.codereview.Vista.Scene1.Extend.*
import com.xyz.codereview.Vista.Scene1.SootheNavigationBar
import com.xyz.codereview.Vista.Scene2.Editor.BlockHandleEditorCode
import kotlinx.coroutines.delay


@Composable
fun MySootheApp(windowSize: WindowSizeClass,viewModel: GameViewModel) {
    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            MySootheAppPortrait(SettingsState.selectedScreen, viewModel)
        }
        WindowWidthSizeClass.Expanded -> {
            MySootheAppLandscape(SettingsState.selectedScreen, viewModel, orientation = 1)
        }
    }
}

@Composable
fun MySootheAppPortrait(currentScreen: Screen, viewModel: GameViewModel, orientation: Int = 0) {
    var isDrawerOpen by remember { mutableStateOf(false) }
    val scale = animateFloatAsState(targetValue = if (isDrawerOpen) 0.9f else 1f) // More scale down
    val offsetX = animateFloatAsState(targetValue = if (isDrawerOpen) 200.dp.value else 0f) // Increase the offset when open

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black)) {
        // Drawer
        if (isDrawerOpen) {
            Drawer(
                width = 300.dp
            )
        }

        // Main Content with TopAppBar
        MainContentWithTopBar(
            currentScreen,
            isDrawerOpen,
            { isDrawerOpen = it },
            scale.value,
            offsetX.value.dp,
            orientation,
            viewModel
        )
    }
}

@Composable
fun MySootheAppLandscape(currentScreen: Screen, viewModel: GameViewModel, orientation: Int = 0) {
    var isDrawerOpen by remember { mutableStateOf(false) }
    val scale = animateFloatAsState(targetValue = if (isDrawerOpen) 0.9f else 1f) // More scale down
    val offsetX = animateFloatAsState(targetValue = if (isDrawerOpen) 200.dp.value else 0f) // Increase the offset when open

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black)) {
        // Drawer
        if (isDrawerOpen) {
            Drawer(
                width = 300.dp
            )
        }

        // Main Content with TopAppBar
        MainContentWithTopBar(
            currentScreen,
            isDrawerOpen,
            { isDrawerOpen = it },
            scale.value,
            offsetX.value.dp,
            orientation,
            viewModel
        )
    }
}


@Composable
fun Drawer(width: Dp) {
    var expandedSection1 by remember { mutableStateOf(false) }
    var expandedSection2 by remember { mutableStateOf(false) }
    var expandedSubSection11 by remember { mutableStateOf(false) }
    var expandedSubSection21 by remember { mutableStateOf(false) }
    var expandedSubSection12 by remember { mutableStateOf(false) }
    var expandedSubSection22 by remember { mutableStateOf(false) }
    var suscrito = SettingsState.usuarioCurrent != null

    fun closeAllSubSections() {
        expandedSubSection11 = false
        expandedSubSection21 = false
        expandedSubSection12 = false
        expandedSubSection22 = false
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .width(width / 1.3f)
            .background(Color.Black)
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        item {
            Text(
                text = "ACCOUNT",
                fontSize = 18.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        item {
            AccountSection(
                expandedSection = expandedSection1,
                expandedSubSection1 = expandedSubSection11,
                expandedSubSection2 = expandedSubSection21,
                onSectionClick = {
                    expandedSection1 = !expandedSection1
                    if (expandedSection1) {
                        closeAllSubSections()
                        expandedSection2 = false
                    }
                },
                onSubSection1Click = {
                    expandedSubSection11 = !expandedSubSection11
                    if (expandedSubSection11) expandedSubSection21 = false
                },
                onSubSection2Click = {
                    expandedSubSection21 = !expandedSubSection21
                    if (expandedSubSection21) expandedSubSection11 = false
                },
                suscrito = suscrito,
                onSubscribe = { suscrito = true }
            )
        }

        item {
            Text(
                text = "PC",
                fontSize = 18.sp,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        item {
            PCSection(
                expandedSection = expandedSection2,
                expandedSubSection1 = expandedSubSection12,
                expandedSubSection2 = expandedSubSection22,
                onSectionClick = {
                    expandedSection2 = !expandedSection2
                    if (expandedSection2) {
                        closeAllSubSections()
                        expandedSection1 = false
                    }
                },
                onSubSection1Click = {
                    expandedSubSection12 = !expandedSubSection12
                    if (expandedSubSection12) expandedSubSection22 = false
                },
                onSubSection2Click = {
                    expandedSubSection22 = !expandedSubSection22
                    if (expandedSubSection22) expandedSubSection12 = false
                }
            )
        }
    }
}

@Composable
fun AccountSection(
    expandedSection: Boolean,
    expandedSubSection1: Boolean,
    expandedSubSection2: Boolean,
    onSectionClick: () -> Unit,
    onSubSection1Click: () -> Unit,
    onSubSection2Click: () -> Unit,
    suscrito: Boolean,
    onSubscribe: () -> Unit
) {
    SectionRow(
        iconRes = R.drawable.ic_account,
        text = "User Profile",
        expanded = expandedSection,
        onClick = onSectionClick
    )
    AnimatedVisibility(visible = expandedSection) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = 8.dp)) {
            if (suscrito) {
                if (SettingsState.usuarioCurrent == null){
                    CircularProgressIndicator()
                }else{
                    SettingsState.usuarioCurrent?.let {
                        PerfilUsuarioC(
                            it,
                            {}
                        )
                    }
                }

            } else {
                SectionRow(
                    iconRes = R.drawable.ic_code,
                    text = "Sign Up",
                    expanded = expandedSubSection1,
                    onClick = onSubSection1Click
                )
                AnimatedVisibility(visible = expandedSubSection1) {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)) {
                        RegistrationForm {
                            onSubSection1Click()
                            onSubscribe()
                        }
                    }
                }

                SectionRow(
                    iconRes = R.drawable.ic_code,
                    text = "Login",
                    expanded = expandedSubSection2,
                    onClick = onSubSection2Click
                )
                AnimatedVisibility(visible = expandedSubSection2) {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)) {
                        LoginForm {
                            onSubSection2Click()
                            onSubscribe()
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun PCSection(
    expandedSection: Boolean,
    expandedSubSection1: Boolean,
    expandedSubSection2: Boolean,
    onSectionClick: () -> Unit,
    onSubSection1Click: () -> Unit,
    onSubSection2Click: () -> Unit
) {
    SectionRow(
        iconRes = R.drawable.ic_connection,
        text = "Connection PC",
        expanded = expandedSection,
        onClick = onSectionClick
    )
    AnimatedVisibility(visible = expandedSection) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = 8.dp)) {
            SectionRow(
                iconRes = R.drawable.ic_connectionlan,
                text = "Connection",
                expanded = expandedSubSection1,
                onClick = onSubSection1Click
            )
            AnimatedVisibility(visible = expandedSubSection1) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)) {
                    BlockConnection()
                }
            }
            SectionRow(
                iconRes = R.drawable.ic_download,
                text = "Download Complement",
                expanded = expandedSubSection2,
                onClick = onSubSection2Click
            )
            AnimatedVisibility(visible = expandedSubSection2) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)) {
                    //BlockConnection()
                }
            }
        }
    }
}




@Composable
fun SectionRow(iconRes: Int, text: String, expanded: Boolean, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = text,
            modifier = Modifier.size(34.dp)
        )
        Text(
            text,
            fontSize = 18.sp,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            color = Color.White
        )
        IconButton(onClick = onClick) {
            Icon(
                imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = "Menu",
                tint = Color.White
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContentWithTopBar(currentScreen: Screen, isDrawerOpen: Boolean, setDrawerOpen: (Boolean) -> Unit, scale: Float, offsetX: Dp, orientation: Int, viewModel: GameViewModel) {
    Column(
        Modifier
            .fillMaxSize()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                translationX = offsetX.toPx() // Move the content to the right
            }
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }) {
                if (isDrawerOpen) setDrawerOpen(false)
            }
    ) {
        when(currentScreen.typeScene){
            Scenes.Scene1 -> {

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                        .padding(top = 20.dp, start = 10.dp),
                    bottomBar = {
                        SootheNavigationBar()
                    }
                ) { padding ->

                    Column (
                        Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .background(Color.Black)
                    ){
                        Column{
                            if (currentScreen.category == 0){
                                Row {
                                    IconButton(onClick = { setDrawerOpen(true) }, modifier = Modifier.size(32.dp)) {
                                        Icon(Icons.Filled.Menu, contentDescription = "Menu", tint = Color.White, modifier = Modifier.fillMaxSize())
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    HeadContent(Color.Black, Color.White, currentScreen)
                                }
                            }else{
                                HeadContent(Color.Black, Color.White, Screen.Settings)
                            }


                        }
                        Column{

                            when (currentScreen) {
                                Screen.Home -> BlockHome()
                                Screen.QuickCode -> BlockQuickCode(orientation,viewModel,padding)
                                Screen.Settings,
                                Screen.GeneralSettings,
                                Screen.Legal -> BlockSettings(
                                    currentScreen
                                )
                                else -> {}
                            }
                        }
                    }
                }



            }
            Scenes.Scene2 -> {

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                        .padding(top = 20.dp, start = 10.dp),
                ) { padding ->
                    val context = LocalContext.current

                    LaunchedEffect(PomodoroState.isRunning) {
                        while (PomodoroState.isRunning) {
                            while (PomodoroState.timeLeft > 0) {
                                //delay(1000L)
                                delay(30L)
                                PomodoroState.timeLeft -= 2
                            }
                            PomodoroState.nextStage(context)
                        }
                    }
                    Column (
                        Modifier
                            .fillMaxSize()
                            .background(Color.Black)
                    ){
                        Row {
                            IconButton(onClick = { setDrawerOpen(true) },colors = IconButtonColors(
                                SettingsState.selectedTheme.color.copy(0.5f), Color.White, Color.White, Color.White), modifier = Modifier.size(50.dp)) {
                                Icon(Icons.Filled.Menu, contentDescription = "Menu", tint = Color.White, modifier = Modifier.fillMaxSize())
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            IconButton(onClick = {
                                SettingsState.selectedScreen = Screen.QuickCode

                            },colors = IconButtonColors(SettingsState.selectedTheme.color, Color.White, Color.White, Color.White), modifier = Modifier.size(50.dp)) {
                                Image(painter = painterResource(id = R.drawable.ic_home), contentDescription = "")
                            }
                            if (PomodoroState.isRunning) {
                                Spacer(modifier = Modifier.weight(1f))
                                val minutes = PomodoroState.timeLeft / 60
                                val seconds = PomodoroState.timeLeft % 60
                                Text(
                                    text = "${PomodoroState.currentStage}: ${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }


                        }

                        when (currentScreen) {
                            Screen.EditorPrincipal ->{
                                BlockHandleEditorCode(modifier = Modifier.padding(padding),colorTheme = SettingsState.selectedTheme.color,orientation = orientation)
                            }
                            else -> {}
                        }


                    }


                }

            }
        }
    }
}





@Preview
@Composable
fun PreviewIconButton() {
    Drawer(width = 300.dp)
}
@Preview
@Composable
fun PreviewIconButtonx() {
    //MySootheAppPortrait(Screen.EditorPrincipal)
}