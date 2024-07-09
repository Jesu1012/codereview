package com.xyz.codereview.Vista.Scene2.Editor.Extend

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xyz.codereview.R

@Composable
fun TaskList(colorTheme : Color) {
    var title by remember { mutableStateOf("Título") }
    var tasks by remember { mutableStateOf(listOf("Elemento de la lista")) }
    var completedTasks by remember { mutableStateOf(listOf<String>()) }
    var newTaskText by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Row (modifier = Modifier.fillMaxWidth()){
            BasicTextField(
                value = title,
                onValueChange = { title = it },
                textStyle = MaterialTheme.typography.headlineMedium.copy(color = Color.Gray),
                cursorBrush = SolidColor(Color.White)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(Icons.Default.Favorite, contentDescription = "")
        }

        Spacer(modifier = Modifier.height(16.dp))


        LazyColumn {
            items(tasks) { task ->
                TaskItem(
                    task = task,
                    onCheckedChange = {
                        tasks = tasks - task
                        completedTasks = completedTasks + task
                    },
                    onDeleteItem = {
                        tasks = tasks - task
                    },
                    onTaskChange = {newTask ->
                        tasks = tasks.map { if (it == task) newTask else it }
                    },
                    colorTheme = colorTheme
                )
            }

        }
        AddTaskItem(
            onAddTask = {
                tasks = tasks + newTaskText
                newTaskText = ""
            }
        )

        if (completedTasks.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Elementos marcados",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(completedTasks) { task ->
                    CompletedTaskItem(
                        task = task,
                        onUnchecked = {
                            completedTasks = completedTasks - task
                            tasks = tasks + task
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "${completedTasks.size} elementos marcados",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}

@Composable
fun TaskItem(task: String,onCheckedChange: () -> Unit, onDeleteItem: () -> Unit, onTaskChange: (String) -> Unit, colorTheme : Color) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(onClick = { onDeleteItem()}) {
            Icon(painter = painterResource(id = R.drawable.ic_delete), contentDescription = "")
        }

        Checkbox(
            checked = false,
            onCheckedChange = {

                onCheckedChange()
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        BasicTextField(
            value = task,
            onValueChange = onTaskChange,
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray),
            modifier = Modifier
                .weight(1f)
                .background(colorTheme.copy(0.2f)),
            cursorBrush = SolidColor(Color.White)
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}


@Composable
fun CompletedTaskItem(task: String, onUnchecked: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Checkbox(
            checked = true,
            onCheckedChange = { onUnchecked() }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = task,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.Gray,
                textDecoration = TextDecoration.LineThrough
            )

        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun AddTaskItem(onAddTask: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onAddTask()
            }
    ) {


        Icon(Icons.Default.Add, contentDescription = "Agregar tarea")

        Text(
            text = "Agregar elemento a la lista",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.Gray
            )

        )

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTaskList() {
    Surface {
        TaskList(Color.Red)
    }
}