package com.example.damienliscio_comp304lab1ex1

//Import Statements
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.content.Intent
import androidx.compose.ui.platform.LocalContext

//Main Activity Class
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreenLayout() //Set the content of the screen as Homescreen Layout on launch.
        }
    }
}

//Composable homescreen layout
@Composable
fun HomeScreenLayout() {
    val context = LocalContext.current  //Store current context

    //Main column
    Column(
        modifier = Modifier  //Use modifier to adjust elements design
            .fillMaxSize()
            .background(color = Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SimpleRow()  //Header row
        Spacer(modifier = Modifier.height(20.dp))
        SimpleLazyColumn(NoteDataSource.notesList) //Lazy column of notes
        Spacer(modifier = Modifier.height(20.dp))
        NewNoteBtn{  //Button to create new note
            val intent = Intent(context, CreateNote::class.java) //Create new intent
            context.startActivity(intent) //Start the activity
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

//Header row function
@Composable
fun SimpleRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Blue),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "QuickNotes", fontSize = 40.sp, color = Color.White, modifier = Modifier.padding(16.dp))
    }
}

//Lazy column function
@Composable
fun SimpleLazyColumn(notes: List<Note>) {
    val context = LocalContext.current  //Store current context
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(8.dp)
    ) {
        items(notes.size) { index -> //Loop through notes
            val note = notes[index] //Get current note using index
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable{  //On click, start edit note activity for given note
                        val intent = Intent(context, EditNote::class.java) //Create new intent
                        intent.putExtra("title", note.title) //Add note title to intent
                        intent.putExtra("content", note.content) //Add note content to intent
                        intent.putExtra("index", index) //Add note index to intent
                        context.startActivity(intent)} //Start the activity
                    .padding(8.dp)
                    .background(Color.Transparent)
                    .padding(16.dp)
            ){
                //Styling for text in lazy column
                Text(
                    text  = note.title,
                    fontSize = 20.sp,
                    color = Color.Black
                )
                Text(
                    text = note.content.take(50) + if (note.content.length > 50) "..." else "",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

        }
    }
}

//New note button function
@Composable
fun NewNoteBtn(onClick: () -> Unit) {
    LargeFloatingActionButton(
        onClick = onClick, //On click, start create note activity
        containerColor = Color.Blue,
        contentColor = Color.White,
        modifier = Modifier
            .padding(8.dp),
    ) {
        Icon(Icons.Filled.Add, "Add New Note")
    }
}

