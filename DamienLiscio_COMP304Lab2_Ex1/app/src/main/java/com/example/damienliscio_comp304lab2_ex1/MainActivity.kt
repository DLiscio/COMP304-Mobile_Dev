package com.example.damienliscio_comp304lab2_ex1

//Import Statements
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.CheckCircle
import android.content.Intent
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.activity.viewModels
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp

//Home Page Activity Class
class MainActivity : ComponentActivity() {
    private val taskViewModel: TaskViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        taskViewModel.loadSampleTasks()
        setContent {
            Material3Design {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    HomePage(taskViewModel = taskViewModel)
                }
            }
        }
    }
    override fun onResume() {
        super.onResume()
    }
}

@Composable
fun Material3Design(content: @Composable () -> Unit) {
    val darkTheme = isSystemInDarkTheme()
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = Color(0xFF3FB1B5),
            secondary = Color(0xFF673AB7),
            background = Color(0xFF1A1A1A),
            surface = Color(0xFF121212),
            onPrimary = Color.Black,
            onSecondary = Color.Black,
            onBackground = Color.White,
            onSurface = Color.White,
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF673AB7),
            secondary = Color(0xFF3FB1B5),
            background = Color(0xFFE6E6E6),
            surface = Color.White,
            onPrimary = Color.White,
            onSecondary = Color.Black,
            onBackground = Color.Black,
            onSurface = Color.Black,
        )
    }

    val typography = Typography(
        bodyLarge = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            letterSpacing = 0.5.sp
        )
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}

@Composable
fun HomePage(taskViewModel : TaskViewModel) {
    val context = LocalContext.current

    Scaffold(
        topBar = { TopBar() },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = { NewTaskFAB(taskViewModel = taskViewModel) },
        bottomBar = { BottomNavigationBar() }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ){
            TasksContent(modifier = Modifier.fillMaxSize(), taskViewModel = taskViewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                "TrackMyDay",
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontFamily = FontFamily.Monospace
                )
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier.background(
            Brush.verticalGradient(
                colors = listOf(Color(0xFF3FB1B5), Color(0xFF673AB7)),
                startY = 0f,
                endY = Float.POSITIVE_INFINITY
            )
        )
    )
}

@Composable
fun NewTaskFAB(taskViewModel: TaskViewModel) {
    val context = LocalContext.current

    FloatingActionButton(
        onClick = { val intent = Intent(context, CreateTask::class.java)
            context.startActivity(intent)},
        containerColor = Color.Transparent,
        contentColor = Color.White,
        modifier = Modifier.background(
            Brush.horizontalGradient(
                colors = listOf(Color(0xFF673AB7),Color(0xFF3FB1B5)),
                startX = 0f,
                endX = Float.POSITIVE_INFINITY
            )
        )
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Add Task", modifier = Modifier.size(60.dp))
    }
}

@Composable
fun BottomNavigationBar() {
    val context = LocalContext.current
    NavigationBar(
        containerColor = Color.Transparent,
        modifier = Modifier.background(
            Brush.verticalGradient(
                colors = listOf(Color(0xFF673AB7),Color(0xFF3FB1B5)),
                startY = 0f,
                endY = Float.POSITIVE_INFINITY
            )
        )
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home", modifier = Modifier.size(30.dp), tint = Color.White) },
            label = { Text("Home", style = TextStyle(color = Color.White, fontSize = 14.sp)) },
            selected = false,
            onClick = { val intentHome = Intent(context, MainActivity::class.java)
                context.startActivity(intentHome) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.CheckCircle, contentDescription = "Completed Tasks", modifier = Modifier.size(30.dp), tint = Color.White) },
            label = { Text("Completed Tasks", style = TextStyle(color = Color.White, fontSize = 14.sp)) },
            selected = false,
            onClick = { val intentCompleted = Intent(context, CompletedTasks::class.java)
                context.startActivity(intentCompleted) }
        )
    }
}

@Composable
fun TasksContent(modifier: Modifier = Modifier, taskViewModel : TaskViewModel) {
    val tasks by taskViewModel.tasks.collectAsState()
    val context = LocalContext.current

    LazyColumn{
        items(tasks.size) { taskIndex ->
            val task = tasks[taskIndex]
            TaskRow(task = task, onClickStatus = { newStatus ->
                taskViewModel.updateTaskStatus(task = task, newStatus)
            }, onTaskClick = {
                val intent = Intent(context, EditTask::class.java).apply {
                    putExtra("TASK_TITLE", task.taskTitle)
                    putExtra("TASK_STATUS", task.taskStatus)
                    putExtra("TASK_DUE_DATE", task.taskDueDate)
                }
                context.startActivity(intent)
            })
        }
    }
}

@Composable
fun TaskRow(task: TaskData, onClickStatus: (TaskStatus) -> Unit, onTaskClick: () -> Unit) {
    Log.d("TaskRow", "Displaying task: ${task.taskTitle}")
    var taskStatus by remember { mutableStateOf(task.taskStatus) }
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        ConfirmCompletion { confirmed ->
            showDialog = false
            if (confirmed) {
                taskStatus = TaskStatus.Complete
                onClickStatus(TaskStatus.Complete)
            } else {
                taskStatus = TaskStatus.InProgress
                onClickStatus(TaskStatus.InProgress)
            }
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable{onTaskClick()},
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Task title and date
        Column(modifier = Modifier
            .weight(1f)) {
            Text(task.taskTitle, style = MaterialTheme.typography.titleLarge)
            Text(task.taskDueDate, style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.width(16.dp))

        val color = when (taskStatus) {
            TaskStatus.Incomplete -> Color.Red
            TaskStatus.InProgress -> Color.Yellow
            TaskStatus.Complete -> Color.Green
        }
        Box(
            modifier = Modifier
                .size(20.dp)
                .background(color, CircleShape)
                .clickable {
                    taskStatus = when (taskStatus) {
                        TaskStatus.Incomplete -> TaskStatus.InProgress
                        TaskStatus.InProgress -> {
                            showDialog = true
                            taskStatus
                        }

                        TaskStatus.Complete -> TaskStatus.Incomplete
                    }
                    if (!showDialog) {
                        onClickStatus(taskStatus)
                    }
                }
        )
    }
}

@Composable
fun ConfirmCompletion(onResult: (Boolean) -> Unit) {
    AlertDialog(
        onDismissRequest = { /* Do nothing */ },
        title = { Text("Complete Task") },
        text = { Text("Would you like to mark this task as complete?") },
        confirmButton = {
            Button(onClick = { onResult(true) }) {
                Text("Yes")
            }
        },
        dismissButton = {
            Button(onClick = { onResult(false) }) {
                Text("No")
            }
        }
    )
}




