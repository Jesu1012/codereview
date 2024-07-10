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
import com.google.gson.annotations.SerializedName
import com.xyz.codereview.Modelo.BoxState
import com.xyz.codereview.Modelo.Task
import com.xyz.codereview.R
import java.util.UUID





@Composable
fun TaskList(boxState: BoxState, onStateChange: (BoxState) -> Unit, colorTheme: Color) {
    var title by remember { mutableStateOf("Title") }
    var newTaskText by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
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
            items(boxState.listTasks) { task ->
                TaskItem(
                    task = task,
                    onCheckedChange = {
                        val newState = boxState.copy(
                            listTasks = boxState.listTasks - task,
                            listTasksCompleted = boxState.listTasksCompleted + task
                        )
                        onStateChange(newState)
                    },
                    onDeleteItem = {
                        val newState = boxState.copy(
                            listTasks = boxState.listTasks - task
                        )
                        onStateChange(newState)
                    },
                    onTaskChange = { newTaskContent ->
                        val newState = boxState.copy(
                            listTasks = boxState.listTasks.map {
                                if (it.id == task.id) it.copy(content = newTaskContent) else it
                            }
                        )
                        onStateChange(newState)
                    },
                    colorTheme = colorTheme
                )
            }
        }

        AddTaskItem(
            onAddTask = {

                val newState = boxState.copy(
                    listTasks = boxState.listTasks + Task(content = newTaskText)
                )
                onStateChange(newState)
                newTaskText = ""

            }
        )

        if (boxState.listTasksCompleted.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Elementos marcados",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(boxState.listTasksCompleted) { task ->
                    CompletedTaskItem(
                        task = task,
                        onUnchecked = {
                            val newState = boxState.copy(
                                listTasksCompleted = boxState.listTasksCompleted - task,
                                listTasks = boxState.listTasks + task
                            )
                            onStateChange(newState)
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "${boxState.listTasksCompleted.size} marked elements",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}



@Composable
fun TaskItem(task: Task, onCheckedChange: () -> Unit, onDeleteItem: () -> Unit, onTaskChange: (String) -> Unit, colorTheme: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(onClick = { onDeleteItem() }) {
            Icon(painter = painterResource(id = R.drawable.ic_delete), contentDescription = "")
        }

        Checkbox(
            checked = false,
            onCheckedChange = { onCheckedChange() }
        )
        Spacer(modifier = Modifier.width(8.dp))
        BasicTextField(
            value = task.content,
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
fun CompletedTaskItem(task: Task, onUnchecked: () -> Unit) {
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
            text = task.content,
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
            .clickable { onAddTask() }
    ) {
        Icon(Icons.Default.Add, contentDescription = "Add Task")
        Text(
            text = "Add item to list",
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTaskList() {
    Surface {
        //TaskList(Color.Red)
    }
}
