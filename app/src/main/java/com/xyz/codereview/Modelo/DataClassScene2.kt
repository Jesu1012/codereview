package com.xyz.codereview.Modelo

import androidx.compose.ui.graphics.Color

data class HighlightedLine(val line: Int, val color: Color)

data class HighlightedRange(val start: Int, val end: Int, val color: Color)