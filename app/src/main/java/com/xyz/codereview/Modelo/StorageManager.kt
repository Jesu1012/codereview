package com.xyz.codereview.Modelo

import android.content.Context
import android.os.Environment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

object StorageManager {
    private const val FILE_NAME = "box_states.json"

    data class SavedInstance(
        val element: Element,
        val boxStates: List<BoxState>
    )

    fun saveBoxStates(context: Context, instances: List<SavedInstance>) {
        val gson = Gson()
        val json = gson.toJson(instances)
        val file = File(context.getExternalFilesDir(null), FILE_NAME)
        file.writeText(json)
    }

    fun loadBoxStates(context: Context): List<SavedInstance> {
        val file = File(context.getExternalFilesDir(null), FILE_NAME)
        if (!file.exists()) return emptyList()
        val json = file.readText()
        val gson = Gson()
        val type = object : TypeToken<List<SavedInstance>>() {}.type
        return gson.fromJson(json, type)
    }
}

