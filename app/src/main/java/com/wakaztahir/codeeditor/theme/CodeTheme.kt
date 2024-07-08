package com.wakaztahir.codeeditor.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import com.wakaztahir.codeeditor.parser.ParseResult
import com.wakaztahir.codeeditor.prettify.parser.Prettify

abstract class CodeTheme(val colors: SyntaxColors) {

    private val colorMap = hashMapOf(
        Prettify.PR_TYPE to colors.type,
        Prettify.PR_KEYWORD to colors.keyword,
        Prettify.PR_LITERAL to colors.literal,
        Prettify.PR_COMMENT to colors.comment,
        Prettify.PR_STRING to colors.string,
        Prettify.PR_PUNCTUATION to colors.punctuation,
        Prettify.PR_PLAIN to colors.plain,
        Prettify.PR_TAG to colors.tag,
        Prettify.PR_DECLARATION to colors.declaration,
        Prettify.PR_SOURCE to colors.source,
        Prettify.PR_ATTRIB_NAME to colors.attrName,
        Prettify.PR_ATTRIB_VALUE to colors.attrValue,
        Prettify.PR_NOCODE to colors.nocode
    )

    open infix fun toSpanStyle(result: ParseResult): SpanStyle {
        return SpanStyle(color = colorMap[result.styleKeysString] ?: kotlin.run {
            Throwable("Missing color value for style key : ${result.styleKeysString}").printStackTrace()
            colors.nocode
        })
    }
}

enum class CodeThemeType(
    override val displayName: String,
    override val color: Color
) : Displayable {
    Default("Default", Color(0xFF334756)) { // Darker blue
        override fun getTheme(): CodeTheme = DefaultTheme()
    },
    Monokai("Monokai", Color(0xFF668B1A)) { // Darker yellow-green
        override fun getTheme(): CodeTheme = MonokaiTheme()
    },
    Dracula("Dracula", Color(0xFF806BBE)) { // Darker purple
        override fun getTheme(): CodeTheme = DraculaTheme()
    },
    Serenity("Serenity", Color(0xFF3B78FF)) { // Darker blue
        override fun getTheme(): CodeTheme = SerenityTheme()
    },
    MidnightCherry("Midnight Cherry", Color(0xFFC0392B)) { // Darker red
        override fun getTheme(): CodeTheme = MidnightCherryTheme()
    },
    PastelDream("Pastel Dream", Color(0xFF7890B4)) { // Darker blue-gray
        override fun getTheme(): CodeTheme = PastelDreamTheme()
    };

    internal abstract fun getTheme(): CodeTheme

    private var _theme: CodeTheme? = null
    val theme: CodeTheme
        get() {
            if (_theme == null) _theme = getTheme()
            return _theme!!
        }
}



interface Displayable {
    val displayName: String
    val color: Color
}
