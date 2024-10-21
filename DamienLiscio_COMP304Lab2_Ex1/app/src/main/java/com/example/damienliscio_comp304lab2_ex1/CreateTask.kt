package com.example.damienliscio_comp304lab2_ex1

//Import Statements
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.ui.Alignment
import android.content.Intent
import androidx.activity.viewModels
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import java.util.Calendar

//Create Task Page Activity Class
class CreateTask : ComponentActivity() {
    private val taskViewModel: TaskViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Material3DesignCT {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    CreateTaskPage(taskViewModel)
                }
            }
        }
    }
}

@Composable
fun Material3DesignCT(content: @Composable () -> Unit) {
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
fun CreateTaskPage(taskViewModel : TaskViewModel) {
    var taskTitle by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(Calendar.getInstance()) }
    val formattedDate = "${selectedDate.get(Calendar.YEAR)}-${selectedDate.get(Calendar.MONTH) + 1}-${selectedDate.get(Calendar.DAY_OF_MONTH)}"

    Scaffold(
        topBar = { TopBarCT() },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = { NewTaskFABCT(taskTitle = taskTitle, taskDueDate = formattedDate, taskViewModel = taskViewModel) },
        bottomBar = { BottomNavigationBarCT() }
    ) { innerPadding ->
        NewTaskContent(
            modifier = Modifier.padding(innerPadding),
            taskTitle = taskTitle,
            onTaskTitleChange = { taskTitle = it },
            selectedDate = selectedDate,
            onDateChange = { selectedDate = it }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarCT() {
    CenterAlignedTopAppBar(
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "TrackMyDay",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontFamily = FontFamily.Monospace
                    )
                )
                Text(
                    "Create Task",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontFamily = FontFamily.Monospace
                    )
                )
            }
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
fun NewTaskFABCT(taskTitle: String, taskDueDate: String, taskViewModel: TaskViewModel) {
    val context = LocalContext.current

    FloatingActionButton(
        onClick = {
            if (taskTitle.isNotEmpty()) {
                taskViewModel.addTask(
                    taskTitle = taskTitle,
                    taskDueDate = taskDueDate,
                    taskStatus = TaskStatus.Incomplete
                )
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            }
        },
        containerColor = Color.Transparent,
        contentColor = Color.White,
        modifier = Modifier.background(
            Brush.horizontalGradient(
                colors = listOf(Color(0xFF673AB7), Color(0xFF3FB1B5)),
                startX = 0f,
                endX = Float.POSITIVE_INFINITY
            )
        )
    ) {
        Text(
            "Save Task",
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(10.dp)
        )
    }
}

@Composable
fun BottomNavigationBarCT() {
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
fun NewTaskContent(
    modifier: Modifier = Modifier,
    taskTitle: String,
    onTaskTitleChange: (String) -> Unit,
    selectedDate: Calendar,
    onDateChange: (Calendar) -> Unit
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            TextField(
                value = taskTitle,
                onValueChange = { onTaskTitleChange(it) },
                label = { Text("Task Title") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
            val formattedDate = "${selectedDate.get(Calendar.YEAR)}-${selectedDate.get(Calendar.MONTH) + 1}-${selectedDate.get(Calendar.DAY_OF_MONTH)}"
            Text("Selected Date: $formattedDate")
        }
        item {
            Spacer(modifier=Modifier.height(8.dp))
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                onClick = {
                    showDatePicker(context) { newDate ->
                        onDateChange(newDate)
                    }
                          },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(Color(0xFF673AB7),Color(0xFF3FB1B5)),
                            startX = 0f,
                            endX = Float.POSITIVE_INFINITY
                        )
                    )
            ){
                Text("Select Task Due By Date")
            }
        }
    }
}

fun showDatePicker(context: Context, onDateSelected: (Calendar) -> Unit) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            // Update the Calendar with the selected date
            val newDate = Calendar.getInstance().apply {
                set(selectedYear, selectedMonth, selectedDay)
            }
            onDateSelected(newDate)
        },
        year, month, day
    )

    datePickerDialog.show()
}