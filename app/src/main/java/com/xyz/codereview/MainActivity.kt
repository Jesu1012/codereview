package com.xyz.codereview

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.google.firebase.database.FirebaseDatabase
import com.xyz.codereview.Modelo.GameViewModel
import com.xyz.codereview.Vista.Scene1.MySootheApp
import com.xyz.codereview.ui.theme.CodereviewTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: GameViewModel
    @RequiresApi(Build.VERSION_CODES.R)
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        //
        val splashScreen = installSplashScreen()
        var keepSplashScreenOn = true
        splashScreen.setKeepOnScreenCondition { keepSplashScreenOn }
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val fadeOut = AnimationUtils.loadAnimation(this, android.R.anim.fade_out)
            fadeOut.duration = 500 // Duración de la animación en milisegundos
            fadeOut.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    splashScreenView.remove()
                }
                override fun onAnimationRepeat(animation: Animation?) {}
            })
            splashScreenView.view.startAnimation(fadeOut)
        }
        //
        super.onCreate(savedInstanceState)
        viewModel = GameViewModel()
        setupFullScreenMode()
        //
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        enableEdgeToEdge()
        setContent {
            //
            CodereviewTheme {
                SettingsState.initialize()
                val windowSizeClass = calculateWindowSizeClass(this)
                HandleBackButton { setupFullScreenMode() }
                MainScreen(windowSizeClass, viewModel, this@MainActivity)
            }
            //
        }

        android.os.Handler().postDelayed({
            keepSplashScreenOn = false
        }, 500)

        lifecycleScope.launch {
            viewModel.loadState(this@MainActivity)
        }
    }
    //
    override fun onDestroy() {
        super.onDestroy()
        lifecycleScope.launch {
            viewModel.saveState(this@MainActivity)
        }
    }

    override fun onPause() {
        super.onPause()
        lifecycleScope.launch {
            viewModel.saveState(this@MainActivity)
        }
    }

    override fun onStop() {
        super.onStop()
        lifecycleScope.launch {
            viewModel.saveState(this@MainActivity)
        }
    }
    //
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onConfigurationChanged(newConfig: android.content.res.Configuration) {
        super.onConfigurationChanged(newConfig)
        setupFullScreenMode()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun setupFullScreenMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.decorView?.post {
                window.setDecorFitsSystemWindows(false)
                window.insetsController?.let {
                    it.hide(WindowInsets.Type.statusBars())
                    it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                }
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or

                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
    }
    //
}

@Composable
fun HandleBackButton(onBackPressed: () -> Unit) {
    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    DisposableEffect(backPressedDispatcher) {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }

        backPressedDispatcher?.addCallback(callback)

        onDispose {
            callback.remove()
        }
    }
}
@Composable
fun MainScreen(windowSizeClass: WindowSizeClass, viewModel: GameViewModel, context: Context) {
    var isLoaded by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            viewModel.loadState(context)
            isLoaded = true
        }
    }

    if (isLoaded) {

        MySootheApp(windowSizeClass, viewModel)

    } else {

        CircularProgressIndicator()
    }
}

