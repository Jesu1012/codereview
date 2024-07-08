package com.xyz.codereview.Vista.Scene1.Extend.Extend_Further
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun Timer() {
    var isRunning by remember { mutableStateOf(false) }
    var timeLeft by remember { mutableStateOf(30 * 60) } // 30 minutes in seconds

    LaunchedEffect(isRunning) {
        if (isRunning) {
            while (timeLeft > 0) {
                delay(1000L)
                timeLeft--
            }
            isRunning = false
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
            val minutes = timeLeft / 60
            val seconds = timeLeft % 60
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
                    SandClockAnimation(timeLeft)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            Button(onClick = {
                if (!isRunning) {
                    timeLeft = 30 * 60 // 30 minutes in seconds
                    isRunning = true
                }
            }) {
                Text("Start Timer", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            }
        }
    }
}

@Composable
fun SandClockAnimation(timeLeft: Int) {
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 180f,
        animationSpec = infiniteRepeatable(
            animation = tween(60000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(200.dp)) {
        Canvas(modifier = Modifier.size(100.dp)) {
            rotate(rotation) {
                drawRoundRect(
                    color = Color(0xFF009688),
                    cornerRadius = CornerRadius(12.dp.toPx(), 12.dp.toPx()),
                    size = size / 2F
                )
                drawRoundRect(
                    color = Color(0xFF009688).copy(alpha = 0.5f),
                    cornerRadius = CornerRadius(12.dp.toPx(), 12.dp.toPx()),
                    topLeft = center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Timer()
}
