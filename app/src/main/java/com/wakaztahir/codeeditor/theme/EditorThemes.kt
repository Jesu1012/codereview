package com.wakaztahir.codeeditor.theme

import androidx.compose.ui.graphics.Color

class DefaultTheme : CodeTheme(
    colors = SyntaxColors(
        type = Color(0xFF859900),
        keyword = Color(0xFF268BD2),
        literal = Color(0xFF269186),
        comment = Color(0xFF93A1A1),
        string = Color(0xFF269186),
        punctuation = Color(0xFF586E75),
        plain = Color(0xFF586E75),
        tag = Color(0xFF859900),
        declaration = Color(0xFF268BD2),
        source = Color(0xFF586E75),
        attrName = Color(0xFF268BD2),
        attrValue = Color(0xFF269186),
        nocode = Color(0xFF586E75),
    )
)

class MonokaiTheme : CodeTheme(
    colors = SyntaxColors(
        type = Color(0xFFA7E22E),
        keyword = Color(0xFFFA2772),
        literal = Color(0xFF66D9EE),
        comment = Color(0xFF76715E),
        string = Color(0xFFE6DB74),
        punctuation = Color(0xFFC1C1C1),
        plain = Color(0xFFF8F8F0),
        tag = Color(0xFFF92672),
        declaration = Color(0xFFFA2772),
        source = Color(0xFFF8F8F0),
        attrName = Color(0xFFA6E22E),
        attrValue = Color(0xFFE6DB74),
        nocode = Color(0xFFF8F8F0),
    )
)
class DraculaTheme : CodeTheme(
    colors = SyntaxColors(
        // Define colors for your Dracula theme here
        type = Color(0xFFBD93F9),
        keyword = Color(0xFFF8C210),
        literal = Color(0xFFCE9178),
        comment = Color(0xFF808080),
        string = Color(0xFFCE9178),
        punctuation = Color(0xFFF8F8F2),
        plain = Color(0xFFF8F8F2),
        tag = Color(0xFFBD93F9),
        declaration = Color(0xFFF8C210),
        source = Color(0xFFF8F8F2),
        attrName = Color(0xFFBD93F9),
        attrValue = Color(0xFFCE9178),
        nocode = Color(0xFFF8F8F2),
    )
)

class SerenityTheme : CodeTheme(
    colors = SyntaxColors(
        type = Color(0xFF66B3FF), // Light blue with 70% opacity
        keyword = Color(0xFF2196F3), // Blue with 70% opacity
        literal = Color(0xFF009688), // Green with 70% opacity
        comment = Color(0xFF9E9E9E), // Gray with 70% opacity
        string = Color(0xFF03A9F4), // Teal with 70% opacity
        punctuation = Color(0xFF555555), // Dark gray with 50% opacity
        plain = Color(0xFFF5F5F5), // Light background
        tag = Color(0xFF66B3FF), // Light blue with 70% opacity
        declaration = Color(0xFF2196F3), // Blue with 70% opacity
        source = Color(0xFFF5F5F5), // Light background
        attrName = Color(0xFF66B3FF), // Light blue with 70% opacity
        attrValue = Color(0xFF03A9F4), // Teal with 70% opacity
        nocode = Color(0xFFF5F5F5), // Light background
    )
)
class MidnightCherryTheme : CodeTheme(
    colors = SyntaxColors(
        type = Color(0xFFE74C3C), // Red with 70% opacity
        keyword = Color(0xFFC0392B), // Dark red with 70% opacity
        literal = Color(0xFF2ECC40), // Green with 70% opacity
        comment = Color(0xFF808080), // Gray with 70% opacity
        string = Color(0xFFF8BBD0), // Pink with 70% opacity
        punctuation = Color(0xFF555555), // Dark gray with 50% opacity
        plain = Color(0xFF222222), // Dark background
        tag = Color(0xFFE74C3C), // Red with 70% opacity
        declaration = Color(0xFFC0392B), // Dark red with 70% opacity
        source = Color(0xFF222222), // Dark background
        attrName = Color(0xFFE74C3C), // Red with 70% opacity
        attrValue = Color(0xFFF8BBD0), // Pink with 70% opacity
        nocode = Color(0xFF222222), // Dark background
    )
)

class PastelDreamTheme : CodeTheme(
    colors = SyntaxColors(
        type = Color(0xFFC5CAE9), // Light pink with 70% opacity
        keyword = Color(0xFFBA68C2), // Purple with 70% opacity
        literal = Color(0xFF7CC557), // Green with 70% opacity
        comment = Color(0xFFD3D3D3), // Light gray with 70% opacity
        string = Color(0xFFF0DBCB), // Peach with 70% opacity
        punctuation = Color(0xFF555555), // Dark gray with 50% opacity
        plain = Color(0xFFF8F8F8), // Very light background
        tag = Color(0xFFC5CAE9), // Light pink with 70% opacity
        declaration = Color(0xFFBA68C2), // Purple with 70% opacity
        source = Color(0xFFF8F8F8), // Very light background
        attrName = Color(0xFFC5CAE9), // Light pink with 70% opacity
        attrValue = Color(0xFFF0DBCB), // Peach with 70% opacity
        nocode = Color(0xFFF8F8F8), // Very light background
    )
)