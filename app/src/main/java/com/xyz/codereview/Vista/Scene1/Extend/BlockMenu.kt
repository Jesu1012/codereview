package com.xyz.codereview.Vista.Scene1.Extend

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.xyz.codereview.R


import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import com.xyz.codereview.Controlador.SettingsState
import com.xyz.codereview.Controlador.UsuarioC
import com.xyz.codereview.Controlador.actualizarUsuarioC
import com.xyz.codereview.Controlador.crearUsuarioC
import com.xyz.codereview.Controlador.obtenerUsuarioPorEmailYPassword

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationForm(expanded: () -> Unit) {
    val email = remember { mutableStateOf("") }
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val fullName = remember { mutableStateOf("") }
    val age = remember { mutableStateOf("") }
    val gender = remember { mutableStateOf("") }
    val expanded = remember { mutableStateOf(false) }
    val genderOptions = listOf("Femenino", "Masculino", "Otro")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, end = 16.dp)
            .background(Color.Black)
    ) {
        Text(
            text = "Account Information",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Profile Information",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = fullName.value,
            onValueChange = { fullName.value = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = age.value,
            onValueChange = { age.value = it },
            label = { Text("Age") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))

        ExposedDropdownMenuBox(
            expanded = expanded.value,
            onExpandedChange = { expanded.value = !expanded.value }
        ) {
            OutlinedTextField(
                value = gender.value,
                onValueChange = { gender.value = it },
                label = { Text("Gender") },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                trailingIcon = {
                    Icon(
                        imageVector = if (expanded.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = null,
                        Modifier.clickable { expanded.value = !expanded.value }
                    )
                },
                readOnly = true
            )
            ExposedDropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }
            ) {
                genderOptions.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            gender.value = selectionOption
                            expanded.value = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        TextButton(
            onClick = {
                var user = UsuarioC("0",email.value,
                    username.value,
                    password.value,
                    fullName.value,
                    age.value.toIntOrNull()?:0,
                    gender.value)
                crearUsuarioC(user,{
                    SettingsState.usuarioCurrent = user
                },{})
                expanded()
                      },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            enabled = email.value.isNotEmpty() && username.value.isNotEmpty()
                    && password.value.isNotEmpty() && fullName.value.isNotEmpty()
                    && age.value.isNotEmpty()
                    && gender.value.isNotEmpty()
        ) {
            Text("SUBMIT")
        }
    }
}
@Composable
fun LoginForm(expanded: () -> Unit) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, end = 16.dp)
            .background(Color.Black)
    ) {
        Text(
            text = "Wellcome",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(24.dp))

        TextButton(
            onClick = {
                obtenerUsuario(email.value, password.value, expanded)
            },
            enabled = email.value.isNotEmpty() && password.value.isNotEmpty(),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("SUBMIT")
        }
    }
}

fun obtenerUsuario(
    email: String,
    password: String,
    expanded: () -> Unit,
    retry: Boolean = true
) {
    obtenerUsuarioPorEmailYPassword(
        email,
        password,
        { usuario ->
            if (usuario != null) {
                SettingsState.usuarioCurrent = usuario
                Log.d("Login", "Usuario encontrado ${SettingsState.usuarioCurrent?.email}")
                expanded()
            } else if (retry) {
                Log.d("Login", "Usuario no encontrado, reintentando...")
                obtenerUsuario(email, password, expanded, retry = false)
            } else {
                Log.d("Login", "Usuario no encontrado en segundo intento.")
                // Manejo de error adicional si es necesario
            }
        },
        {
            Log.d("Login", "Error al buscar usuario.")
            if (retry) {
                obtenerUsuario(email, password, expanded, retry = false)
            } else {
                Log.d("Login", "Usuario no encontrado después de reintento.")
                // Manejo de error adicional si es necesario
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilUsuarioC(usuario: UsuarioC, onEditClick: () -> Unit) {
    var layoutPerfil by remember { mutableStateOf(true) }
    if (layoutPerfil){
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.cofe), // Asegúrate de tener esta imagen en tus recursos
                        contentDescription = "Foto de perfil",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = usuario.fullName,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "@${usuario.username}",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            PerfilItem(icon = Icons.Default.Person, title = "Username", value = usuario.username)
            PerfilItem(icon = Icons.Default.Email, title = "Email", value = usuario.email)
            PerfilItem(icon = Icons.Default.DateRange, title = "Age", value = "${usuario.age} años")
            PerfilItem(icon = Icons.Default.Face, title = "Gender", value = usuario.gender)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        SettingsState.usuarioCurrent = null
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                        .border(
                            width = 2.dp,
                            brush = Brush.linearGradient(listOf(Color.Red, Color.Magenta)),
                            shape = MaterialTheme.shapes.small
                        ),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Text(
                        text = "Log Out",
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Button(
                    onClick = { layoutPerfil = false },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                        .border(
                            width = 2.dp,
                            brush = Brush.linearGradient(listOf(Color.Blue, Color.Cyan)),
                            shape = MaterialTheme.shapes.small
                        ),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Text(
                        text = "Edit Profile",
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }


        }
    }else{
        EditarPerfilUsuarioC(usuario = usuario,
            onUpdateClick = {
                actualizarUsuarioC(
                    it,
                    onSuccess = {
                        SettingsState.usuarioCurrent = it
                        layoutPerfil = true
                    },
                    onError = {
                        // Mostrar mensaje de error
                    }
                )

        }, onBackClick = {
            layoutPerfil = true
        })
    }

}

@Composable
fun PerfilItem(icon: ImageVector, title: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = value,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarPerfilUsuarioC(
    usuario: UsuarioC,
    onUpdateClick: (UsuarioC) -> Unit,
    onBackClick: () -> Unit
) {
    var fullName by remember { mutableStateOf(usuario.fullName) }
    var username by remember { mutableStateOf(usuario.username) }
    var email by remember { mutableStateOf(usuario.email) }
    var age by remember { mutableStateOf(usuario.age.toString()) }
    var gender by remember { mutableStateOf(usuario.gender) }
    var password by  remember { mutableStateOf(usuario.password) }

    val expanded = remember { mutableStateOf(false) }
    val genderOptions = listOf("Femenino", "Masculino", "Otro")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Edit Profile",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Full name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        ExposedDropdownMenuBox(
            expanded = expanded.value,
            onExpandedChange = { expanded.value = !expanded.value }
        ) {
            OutlinedTextField(
                value = gender,
                onValueChange = { gender = it },
                label = { Text("Gender") },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                trailingIcon = {
                    Icon(
                        imageVector = if (expanded.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = null,
                        Modifier.clickable { expanded.value = !expanded.value }
                    )
                },
                readOnly = true
            )
            ExposedDropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }
            ) {
                genderOptions.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            gender = selectionOption
                            expanded.value = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onBackClick,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .border(
                        width = 2.dp,
                        brush = Brush.linearGradient(listOf(Color.Yellow, Color.Green)),
                        shape = MaterialTheme.shapes.small
                    ),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Text(
                    text = "Back",
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Button(
                onClick = {
                    val updatedUsuario = usuario.copy(
                        fullName = fullName,
                        username = username,
                        email = email,
                        age = age.toIntOrNull() ?: usuario.age,
                        gender = gender,
                        password = password
                    )
                    onUpdateClick(updatedUsuario)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
                    .border(
                        width = 2.dp,
                        brush = Brush.linearGradient(listOf(Color.Blue, Color.Cyan)),
                        shape = MaterialTheme.shapes.small
                    ),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Text(
                    text = "Update",
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditarPerfilUsuarioCPreview() {
    val usuarioEjemplo = UsuarioC(
        id = "1",
        email = "usuario@ejemplo.com",
        username = "usuario123",
        fullName = "Juan Pérez",
        age = 30,
        gender = "Masculino"
    )
    EditarPerfilUsuarioC(
        usuario = usuarioEjemplo,
        onUpdateClick = {},
        onBackClick = {}
    )
}

@Composable
fun PerfilUsuarioCPreview() {
    val usuarioEjemplo = UsuarioC(
        id = "1",
        email = "usuario@ejemplo.com",
        username = "usuario123",
        fullName = "Juan Pérez",
        age = 30,
        gender = "Masculino"
    )
    PerfilUsuarioC(usuario = usuarioEjemplo, onEditClick = {})
}



@Preview(showBackground = true)
@Composable
fun UserProfilePreview() {
    //UserProfile()
    PerfilUsuarioCPreview()
}

@Preview(showBackground = true)
@Composable
fun RegistrationFormPreview() {
    RegistrationForm({})
}





