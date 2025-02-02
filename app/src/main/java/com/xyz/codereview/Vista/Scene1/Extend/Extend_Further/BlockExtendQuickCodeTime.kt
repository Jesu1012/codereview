package com.xyz.codereview.Vista.Scene1.Extend.Extend_Further

import android.content.Context
import android.os.Vibrator
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xyz.codereview.Modelo.PomodoroState
import kotlinx.coroutines.delay



@Composable
fun Timer() {
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

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Pomodoro ${PomodoroState.currentPomodoro} - ${PomodoroState.currentStage}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(8.dp))
            val minutes = PomodoroState.timeLeft / 60
            val seconds = PomodoroState.timeLeft % 60
            Text(
                text = "Time Left: ${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                item {
                    SandClockAnimation(PomodoroState.timeLeft)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            Button(onClick = {
                if (!PomodoroState.isRunning) {
                    PomodoroState.isRunning = true
                } else {
                    PomodoroState.reset()
                }
            }) {
                Text(
                    if (PomodoroState.isRunning) "Reset Timer" else "Start Timer",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun SandClockAnimation(timeLeft: Int) {
    // ... (el mismo código que antes)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Timer()
}
