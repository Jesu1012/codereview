package com.xyz.codereview.Vista.Scene1.Extend

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xyz.codereview.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.NetworkInterface
import java.net.Socket
import java.net.SocketException
import java.util.Collections


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
            onClick = { expanded() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("SUBMIT")
        }
    }
}
@Composable
fun LoginForm(expanded: () -> Unit) {
    val username = remember { mutableStateOf("") }
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
        Spacer(modifier = Modifier.height(24.dp))

        TextButton(
            onClick = { expanded() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("SUBMIT")
        }
    }
}

@Composable
fun UserProfile() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.Black)
    ) {
        UserProfileHeader()
        Spacer(modifier = Modifier.height(16.dp))
        AchievementsSection()
        Spacer(modifier = Modifier.height(16.dp))
        ProjectsSection()
        Spacer(modifier = Modifier.height(16.dp))
        SkillsSection()
        Spacer(modifier = Modifier.height(32.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(80.dp) // Ajusta el tamaño del círculo según sea necesario
                .border(2.dp, Color.Magenta, CircleShape)
                .clip(CircleShape)
        ) {
            Text("EDIT PROFILE", color = Color.White, textAlign = TextAlign.Center,fontWeight = FontWeight.Bold)
        }

    }
}

@Composable
fun UserProfileHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.publicidad2), // Reemplazar con el recurso de imagen correcto
            contentDescription = "User Profile Picture",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text("Evans Collins", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text("AGE", color = Color.Gray)
        }
    }
}

@Composable
fun AchievementsSection() {
    Column {
        Text("ACHIEVEMENT", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            AchievementIcon(R.drawable.ic_account)
            AchievementIcon(R.drawable.ic_account)
            AchievementIcon(R.drawable.ic_account)
        }
    }
}

@Composable
fun AchievementIcon(iconRes: Int) {
    Image(
        painter = painterResource(id = iconRes),
        contentDescription = "Achievement",
        modifier = Modifier
            .size(40.dp)
            .padding(4.dp)
    )
}

@Composable
fun ProjectsSection() {
    Column {
        Text("STATUS HEALTH", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            //ProjectStatus("3", "On Progress")
            ProjectStatus("90%", "Finished")
            ProjectStatus("80%", "On Time")
            //ProjectStatus("85%", "On Budget")
        }
    }
}

@Composable
fun ProjectStatus(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Text(label, color = Color.Gray)
    }
}

@Composable
fun SkillsSection() {
    Column {
        Text("SKILLS", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            SkillChip("Web Development")
            SkillChip("Android")
            SkillChip("Kotlin")
            SkillChip("Java")
        }
    }
}

@Composable
fun SkillChip(label: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .border(1.dp, Color.Green, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(label, color = Color.Green)
    }
}

@Preview(showBackground = true)
@Composable
fun UserProfilePreview() {
    UserProfile()
}

@Preview(showBackground = true)
@Composable
fun RegistrationFormPreview() {
    RegistrationForm({})
}





