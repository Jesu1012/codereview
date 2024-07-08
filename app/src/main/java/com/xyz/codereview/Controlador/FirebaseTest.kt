package com.xyz.codereview.Controlador

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.UUID

data class UsuarioC(
    val id: String = "",
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val fullName: String = "",
    val age: Int = 0,
    val gender: String = ""
)

// Create
fun crearUsuarioC(usuario: UsuarioC, onSuccess: () -> Unit, onError: (String) -> Unit) {
    val baseDeDatos = FirebaseDatabase.getInstance()
    val referencia = baseDeDatos.getReference("usuariosC")
    val id = referencia.push().key ?: UUID.randomUUID().toString()
    val usuarioConId = usuario.copy(id = id)

    referencia.child(id).setValue(usuarioConId)
        .addOnSuccessListener {
            onSuccess()
        }
        .addOnFailureListener { e ->
            onError(e.message ?: "Error al crear usuario")
        }
}

// Read
fun obtenerUsuariosC(onSuccess: (List<UsuarioC>) -> Unit, onError: (String) -> Unit) {
    val baseDeDatos = FirebaseDatabase.getInstance()
    val referencia = baseDeDatos.getReference("usuariosC")

    referencia.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val usuarios = mutableListOf<UsuarioC>()
            for (usuarioSnapshot in snapshot.children) {
                val usuario = usuarioSnapshot.getValue(UsuarioC::class.java)
                usuario?.let { usuarios.add(it) }
            }
            onSuccess(usuarios)
        }

        override fun onCancelled(error: DatabaseError) {
            onError(error.message)
        }
    })
}

// Update
fun actualizarUsuarioC(usuario: UsuarioC, onSuccess: () -> Unit, onError: (String) -> Unit) {
    val baseDeDatos = FirebaseDatabase.getInstance()
    val referencia = baseDeDatos.getReference("usuariosC")

    referencia.child(usuario.id).updateChildren(usuario.toMap())
        .addOnSuccessListener {
            onSuccess()
        }
        .addOnFailureListener { e ->
            onError(e.message ?: "Error al actualizar usuario")
        }
}

// Delete
fun eliminarUsuarioC(id: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
    val baseDeDatos = FirebaseDatabase.getInstance()
    val referencia = baseDeDatos.getReference("usuariosC")

    referencia.child(id).removeValue()
        .addOnSuccessListener {
            onSuccess()
        }
        .addOnFailureListener { e ->
            onError(e.message ?: "Error al eliminar usuario")
        }
}
// Obtener usuario por email y contraseña
fun obtenerUsuarioPorEmailYPassword(email: String, password: String, onSuccess: (UsuarioC?) -> Unit, onError: () -> Unit) {
    val baseDeDatos = FirebaseDatabase.getInstance()
    val referencia = baseDeDatos.getReference("usuariosC")

    referencia.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            var usuarioEncontrado: UsuarioC? = null
            for (usuarioSnapshot in snapshot.children) {
                val usuario = usuarioSnapshot.getValue(UsuarioC::class.java)
                if (usuario != null && usuario.password == password) {
                    usuarioEncontrado = usuario
                    break
                }
            }
            onSuccess(usuarioEncontrado)
        }

        override fun onCancelled(error: DatabaseError) {
            onError()
        }
    })
}
// Helper function to convert UsuarioC to Map
fun UsuarioC.toMap(): Map<String, Any?> {
    return mapOf(
        "id" to id,
        "email" to email,
        "username" to username,
        "password" to password,
        "fullName" to fullName,
        "age" to age,
        "gender" to gender
    )
}

