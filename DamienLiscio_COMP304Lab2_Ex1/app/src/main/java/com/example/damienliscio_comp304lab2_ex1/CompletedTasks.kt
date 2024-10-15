package com.example.damienliscio_comp304lab2_ex1

//Import Statements
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp

//Completed Task Page Activity Class
class CompletedTasks : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Material3DesignCOT {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    CompletedTaskPage()
                }
            }
        }
    }
}

@Composable
fun Material3DesignCOT(content: @Composable () -> Unit) {
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
fun CompletedTaskPage() {
    Scaffold(
        topBar = { TopBarCOT() },
        bottomBar = { BottomNavigationBarCOT() }
    ) { innerPadding ->
        CompletedTaskContent(Modifier.padding(innerPadding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarCOT() {
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
                    "Completed Tasks",
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
fun BottomNavigationBarCOT() {
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
fun CompletedTaskContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Completed Tasks Page")
    }
}