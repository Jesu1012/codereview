package com.xyz.codereview.Vista.Scene1.Extend

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.xyz.codereview.R


@Composable
fun TaskBoardScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Task Board",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp),
            color = Color.White
        )

        TaskCard(
            time = "08 AM",
            title = "Bugs #B743",
            category = "Major Category",
            schedule = "08:00 AM - 11:35 AM",
            backgroundColor = Color(0xFFFFF9C4), // Light yellow
            avatarResource = R.drawable.ic_play // replace with your resource
        )

        TaskCard(
            time = "09 AM",
            title = "Feature #F884",
            category = "Secondary",
            schedule = "04:12 AM - 06:40 AM",
            backgroundColor = Color(0xFFBBDEFB), // Light blue
            avatarResource = R.drawable.ic_play // replace with your resource
        )

        TaskCard(
            time = "11 AM",
            title = "Feature #F764",
            category = "Secondary",
            schedule = "04:12 AM - 06:40 AM",
            backgroundColor = Color(0xFFFFCDD2), // Light pink
            avatarResource = R.drawable.ic_play // replace with your resource
        )

        TaskCard(
            time = "05 AM",
            title = "Improvement #I064",
            category = "Secondary",
            schedule = "04:12 AM - 06:40 AM",
            backgroundColor = Color(0xFFC8E6C9), // Light green
            avatarResource = R.drawable.ic_play // replace with your resource
        )
    }
}

@Composable
fun TaskCard(
    time: String,
    title: String,
    category: String,
    schedule: String,
    backgroundColor: Color,
    avatarResource: Int
) {
    Column(
        modifier = Modifier.padding(vertical= 8.dp)
    ) {
        Text(
            text = time,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom= 4.dp),
            color = Color.White
        )
        Card(
            colors = CardDefaults.cardColors(backgroundColor),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom= 4.dp)
                    )
                    Text(
                        text = category,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom= 8.dp)
                    )
                    Text(
                        text = schedule,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Image(
                    painter = painterResource(id = avatarResource),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
            }
        }
    }
}
@Preview
@Composable
fun TaskBoardScreenPreview() {
    TaskBoardScreen()
}
@Composable
fun BlockProjects(
) {
    // Placeholder for project items
    Column (modifier = Modifier
        .fillMaxWidth()
        .padding(end = 20.dp, top = 20.dp)
        .verticalScroll(rememberScrollState())
    ){
        repeat(4) { index ->
            ProjectItem(
                name = "Proyecto ${index + 1}",
                description = "hace ${index + 1} minutos",
                Modifier.padding(vertical = 5.dp)
            )
        }
    }

}

@Composable
fun ProjectItem(
    name: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color(0x4D7F7F7F), RoundedCornerShape(8.dp))
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color.DarkGray,
            modifier = Modifier.size(60.dp)
        ) {
            // Placeholder for project icon
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(name, color = Color.White, style = MaterialTheme.typography.bodyLarge)
            Text(description, color = Color.Gray, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview
@Composable
fun ProjectsScreenPreview() {
    BlockProjects();
}
