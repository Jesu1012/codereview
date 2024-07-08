package com.xyz.codereview.Modelo


import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

import com.wakaztahir.codeeditor.theme.CodeThemeType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID
import android.content.Context
import com.google.gson.annotations.SerializedName
import com.xyz.codereview.SettingsState

object BoxStateManagerSingleton {
    private val instances = mutableStateListOf<Pair<Element, BoxStateManager>>()

    fun getInstance(element: Element): BoxStateManager {
        return instances.firstOrNull { it.first == element }?.second
            ?: BoxStateManager()
    }
    fun getInstanceE(element: Element): Element {
        return instances.firstOrNull { it.first == element }?.first
            ?: element
    }

    fun addInstance(element: Element, boxStateManager: BoxStateManager) {
        instances.add(element to boxStateManager)
        Log.d("BoxStateManagerSingleton", "addInstance: ${instances.size}")
    }

    fun removeInstance(element: Element) {
        instances.removeAll { it.first == element }
    }

    fun removeAllInstance() {
        instances.clear()
    }

    fun getInstances(): List<Pair<Element, BoxStateManager>> {
        return instances
    }

    fun saveAllStates(context: Context) {
        val savedInstances = instances.map { (element, manager) ->
            StorageManager.SavedInstance(element, manager.boxStates)
        }
        StorageManager.saveBoxStates(context, savedInstances)
    }

    fun loadAllStates(context: Context) {
        val savedInstances = StorageManager.loadBoxStates(context)
        instances.clear()
        savedInstances.forEach { (element, boxStates) ->
            val manager = BoxStateManager()
            manager.setBoxStates(boxStates)
            instances.add(element to manager)
        }
    }
}


class BoxStateManager {

    private val _boxStates = mutableStateListOf<BoxState>(BoxState(pasteLocalState = false, clipboardText = "Clipboard is empty"))

    val boxStates: List<BoxState> get() = _boxStates

    var showCodeDifferent by mutableStateOf(false)
        private set

    private var firstCheckedIndex: Int? = null
    private var secondCheckedIndex: Int? = null

    fun addBoxState(boxState: BoxState) {
        _boxStates.add(boxState)
    }

    fun updateBoxState(index: Int, boxState: BoxState) {
        if (index in _boxStates.indices) {
            _boxStates[index] = boxState
            if (boxState.isChecked) {
                handleCheck(index)
            } else {
                handleUncheck(index)
            }
        }
    }

    private fun handleCheck(index: Int) {
        if (firstCheckedIndex == null) {
            firstCheckedIndex = index
        } else if (firstCheckedIndex != index) {
            secondCheckedIndex = index
            showCodeDifferent = true
        }
    }

    private fun handleUncheck(index: Int) {
        if (firstCheckedIndex == index) {
            firstCheckedIndex = null
        }
        if (secondCheckedIndex == index) {
            secondCheckedIndex = null
        }
        showCodeDifferent = false
    }

    fun deselectLastChecked() {
        secondCheckedIndex?.let {
            _boxStates[it] = _boxStates[it].copy(isChecked = false)
            secondCheckedIndex = null
        }
        showCodeDifferent = false
    }

    fun getSelectedBoxStates(): List<BoxState> {
        return _boxStates.filter { it.isChecked }
    }

    fun getBoxStateBySecondCheckedIndex(): BoxState? {
        return secondCheckedIndex?.let { _boxStates.getOrNull(it) }
    }

    fun setBoxStates(states: List<BoxState>) {
        _boxStates.clear()
        _boxStates.addAll(states)
        states.forEach() { state ->
            if (state.isChecked) {
                handleCheck(_boxStates.indexOf(state))
            }
        }
    }
}



data class BoxState(
    @SerializedName("pasteLocalState") var pasteLocalState: Boolean,
    @SerializedName("clipboardText") var clipboardText: String,
    @SerializedName("isChecked") var isChecked: Boolean = false
)

data class Element(
    @SerializedName("id") val id: String = UUID.randomUUID().toString(),
    @SerializedName("name") val name: String,
    @SerializedName("color") val color: Color,
    @SerializedName("type") val type: String,
    @SerializedName("offsetX") var offsetX: Float = 0f,
    @SerializedName("offsetY") var offsetY: Float = 0f,
    @SerializedName("numBase") var numBase: Int = 0,
    @SerializedName("theme") var theme: CodeThemeType = CodeThemeType.Default
) {
    var offset: Offset
        get() = Offset(offsetX, offsetY)
        set(value) {
            offsetX = value.x
            offsetY = value.y
        }
}

@SuppressLint("MutableCollectionMutableState")
class GameViewModel : ViewModel() {

    private val _elementsDynamicPrincipal = MutableStateFlow(listOf<Element>())
    val elementsDynamicPrincipal: StateFlow<List<Element>> = _elementsDynamicPrincipal

    private val _elementsDynamicExtra = MutableStateFlow(listOf<Element>())
    val elementsDynamicExtra: StateFlow<List<Element>> = _elementsDynamicExtra


    fun saveState(context: Context) {
        BoxStateManagerSingleton.saveAllStates(context)
    }

    fun loadState(context: Context) {
        BoxStateManagerSingleton.loadAllStates(context)
        val loadedElements = BoxStateManagerSingleton.getInstances().map { it.first }
        // Filtrar elementos duplicados por id
        val uniqueElements = loadedElements.distinctBy { it.id }
        _elementsDynamicExtra.value = uniqueElements
    }

    override fun onCleared() {
        super.onCleared()
        // Aquí se debe pasar el contexto adecuado
        // val context: Context = // Obtener el contexto de alguna manera
        // saveState(context)
    }

    fun changeElements(typeDesired: String) {
        _elementsDynamicPrincipal.value = if (typeDesired == "code") {
            SettingsState.listSelectedLanguages.map { language ->
                Element(name = language.displayName, color = language.color, type = "code")
            }
        } else if (typeDesired == "theme") {
            SettingsState.listSelectedTheme.map { theme ->
                Element(name = theme.displayName, color = theme.color, type = "theme")
            }
        } else {
            emptyList()
        }
    }

    fun addElementPrincipal(element: Element) {
        _elementsDynamicPrincipal.value = _elementsDynamicPrincipal.value + element

    }

    fun addElementExtra(element: Element, offset: Offset) {
        // Comprobar si el elemento ya existe
        if (_elementsDynamicExtra.value.none { it.id == element.id }) {
            element.offset = offset
            _elementsDynamicExtra.value = _elementsDynamicExtra.value + element
        }
    }

    fun updateElementOffset(id: String, offset: Offset) {
        Log.d("GameViewModel", "updateElementOffset: $id")
        BoxStateManagerSingleton.getInstances().filter { it.first.id == id }.forEach { (element) ->
            if (element.id == id){
                element.offset = offset
                elementsDynamicExtra.value.filter { it.id == id }
                    .forEach { it.offset = offset}
            }
        }
//        for (element in _elementsDynamicExtra.value) {
//            if (element.id == id) {
//                element.offset = offset
//                BoxStateManagerSingleton.getInstanceE(element).offset = offset
//            }
//        }
    }

    fun deleteAllElements() {
        _elementsDynamicExtra.value = emptyList()
    }

    fun onElementDrop(element: Element, offset: Offset) {
        val toleranceX = 200 // Define una tolerancia para los límites
        val toleranceY = 170 // Define una tolerancia para los límites
        val targetElement = _elementsDynamicExtra.value.firstOrNull {
            it.type != element.type &&
                    Math.abs(it.offset.y - offset.y) < toleranceY &&
                    Math.abs(it.offset.x - offset.x) < toleranceX
        }
        if (targetElement != null && element.numBase == 0 && targetElement.numBase == 0) {
            var newName = ""
            var newColor = Color.White
            var theme = CodeThemeType.Default
            if (element.type == "code") {
                newName = element.name
                newColor = targetElement.color.copy(alpha = 0.5f)
                theme = CodeThemeType.entries.first { it.displayName == targetElement.name }
            }
            if (targetElement.type == "code") {
                newName = targetElement.name
                newColor = element.color.copy(alpha = 0.5f)
                theme = CodeThemeType.entries.first { it.displayName == element.name }
            }

            val newType = "mixed"
            val newNumBase = 1
            val newElement = Element(name = newName, color = newColor, type = newType, numBase = newNumBase, theme = theme)
            newElement.offset = offset
            addElementExtra(newElement, offset)

            _elementsDynamicExtra.value = _elementsDynamicExtra.value - targetElement - element
            BoxStateManagerSingleton.addInstance(newElement, BoxStateManager())

            Log.d("GameViewModel", "onElementDrop: $element and $targetElement")
        }
    }
}

