package com.xyz.codereview

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


data class Usuario(
    val edad: Int = 0, val nombre: String = "", val apellido: String = ""
)

@Composable
fun ListaDeUsuarios(usuarios: List<Usuario>, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        usuarios.forEach { usuario ->
            ItemUsuario(usuario)
            HorizontalDivider()
        }
    }
}

@Composable
fun ItemUsuario(usuario: Usuario) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = "Nombre: ${usuario.nombre} ${usuario.apellido}")
        Text(text = "Edad: ${usuario.edad}")
    }
}

fun obtenerUsuarios(usuarios: SnapshotStateList<Usuario>) {
    val baseDeDatos = FirebaseDatabase.getInstance()
    val referencia = baseDeDatos.getReference("usuarios-test")
    referencia.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            usuarios.clear()
            for (snapshotUsuario in snapshot.children) {
                try {
                    val usuario = snapshotUsuario.getValue(Usuario::class.java)
                    if (usuario != null) {
                        usuarios.add(usuario)
                    } else {
                        Log.e("obtenerUsuarios", "Error al convertir el snapshot a Usuario")
                    }
                } catch (e: Exception) {
                    Log.e("obtenerUsuarios", "Error: ${e.message}")
                }
            }
        }

        override fun onCancelled(error: DatabaseError) {
            println("Error al obtener datos: ${error.message}")
        }
    })
}