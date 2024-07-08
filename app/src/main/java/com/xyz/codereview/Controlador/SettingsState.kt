package com.xyz.codereview.Controlador

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import com.xyz.codereview.Vista.Scene1.Screen
import com.wakaztahir.codeeditor.model.CodeLang
import com.wakaztahir.codeeditor.theme.CodeThemeType
import com.xyz.codereview.Modelo.Element

object SettingsState {
    var usuarioCurrent by mutableStateOf<UsuarioC?>(null)
    var selectedScreen by mutableStateOf<Screen>(Screen.Home)
    var selectedLanguage: CodeLang = CodeLang.Default
    var selectedTheme: CodeThemeType = CodeThemeType.Default
    var elementSelect: Element? = null
    var initialized = false
    var listSelectedLanguages: MutableList<CodeLang> = mutableListOf()
    var listSelectedTheme: MutableList<CodeThemeType> = mutableListOf()


    fun initialize() {
        if (!initialized) {
            listSelectedLanguages.addAll(
                listOf(CodeLang.CPP, CodeLang.CSharp, CodeLang.Kotlin,CodeLang.C,CodeLang.Appollo,CodeLang.HTML,CodeLang.Bash,CodeLang.JSON
                ,CodeLang.FSharp, CodeLang.Lisp, CodeLang.Markdown,CodeLang.Ex)) // Reemplaza con los lenguajes que prefieras
            initialized = true
            listSelectedTheme.addAll(listOf(CodeThemeType.Dracula, CodeThemeType.Monokai, CodeThemeType.Serenity)) // Reemplaza con los temas que prefieras
        }
    }
}



