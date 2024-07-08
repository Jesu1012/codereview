package com.xyz.codereview.Vista.Scene1.Extend

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.xyz.codereview.Modelo.GameViewModel
import com.xyz.codereview.R
import com.xyz.codereview.Vista.Scene1.Extend.Extend_Further.BlockCraft
import com.xyz.codereview.Vista.Scene1.Extend.Extend_Further.Timer

@Composable
fun BlockQuickCode(orientation: Int = 0,viewModel: GameViewModel) {
    var splitterOffset by remember { mutableStateOf(0.9f) }
    val animatedSplitterOffset by animateFloatAsState(targetValue = splitterOffset)
    val minHeight = 100.dp

    BoxWithConstraints(modifier = Modifier
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
                    detectVerticalDragGestures { change, dragAmount ->
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
                    detectHorizontalDragGestures { change, dragAmount ->
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
                            .background(Color.Blue, shape = RoundedCornerShape(5.dp))
                    ) {
                        BlockCraft(viewModel)
                    }
                }

                Box(modifier = draggableModifier.align(Alignment.CenterHorizontally)) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(Color.White, shape = RoundedCornerShape(40))
                            .align(Alignment.Center)
                    )
                }

                if (animatedSplitterOffset < 1f) {
                    Box(
                        modifier = Modifier
                            .weight(1f - animatedSplitterOffset)
                            .fillMaxWidth()
                            .background(Color(0xFF009688), shape = RoundedCornerShape(5.dp))
                    ) {
                        if (animatedSplitterOffset > 0.87f) {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable {
                                        splitterOffset = 0.4f
                                    },
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                item {
                                    Row {
                                        Image(
                                            painter = painterResource(id = R.drawable.baseline_ads_click_24),
                                            contentDescription = "Click Time"
                                        )
                                    }
                                }
                            }

                        } else {
                            Timer()
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
                            .background(Color.Blue, shape = RoundedCornerShape(5.dp))
                    ) {
                        BlockCraft(viewModel)
                    }
                }

                Box(modifier = draggableModifier.align(Alignment.CenterVertically)) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(Color.White, shape = RoundedCornerShape(40))
                            .align(Alignment.Center)
                    )
                }

                if (animatedSplitterOffset < 1f) {
                    Box(
                        modifier = Modifier
                            .weight(1f - animatedSplitterOffset)
                            .fillMaxHeight()
                            .background(Color(0xFF009688), shape = RoundedCornerShape(5.dp))
                    ) {
                        if (animatedSplitterOffset > 0.87f) {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable {
                                        splitterOffset = 0.6f
                                    },
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                item {
                                    Row {
                                        Image(
                                            painter = painterResource(id = R.drawable.baseline_ads_click_24),
                                            contentDescription = "Click Time"
                                        )
                                    }
                                }
                            }

                        } else {
                            Timer()
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Orientation_1() {
    //BlockQuickCode(orientation = 1)
}
@Preview(showBackground = true)
@Composable
fun Orientation_0() {
    //BlockQuickCode(orientation = 0)
}
