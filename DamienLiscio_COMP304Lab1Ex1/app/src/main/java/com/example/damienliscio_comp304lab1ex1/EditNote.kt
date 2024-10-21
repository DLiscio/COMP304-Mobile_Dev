package com.example.damienliscio_comp304lab1ex1

//Import Statements
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// EditNote Activity Class
class EditNote : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val noteIndex = intent.getIntExtra("index", -1) //Get the note index from the intent
        val noteTitle = intent.getStringExtra("title") ?: "" //Get the note title from the intent
        val noteContent = intent.getStringExtra("content") ?: "" //Get the note content from the intent
        setContent {
            EditNoteScreen(noteTitle, noteContent){ title, content ->
                if (noteIndex != -1) { //If the note index is valid, update the note
                    NoteDataSource.updateNote(noteIndex, title, content) //Update the note using the data class objects method
                }
                finish() //Finish the activity
            }
        }
    }
}

//EditNote Screen Function
@Composable
fun EditNoteScreen(initialTitle: String, initialContent: String, onSave: (String, String) -> Unit) {
    var titleText by remember { mutableStateOf(TextFieldValue(initialTitle)) } //Title text
    var noteText by remember { mutableStateOf(TextFieldValue(initialContent)) } //Note text

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.LightGray)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Blue),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Edit Note", fontSize = 40.sp, color = Color.White, modifier = Modifier.padding(16.dp))
        }
        //Title text field
        TextField(
            value = titleText, //Set the value of the text field as note title
            onValueChange = { titleText = it }, //Update the value of the text field when it changes
            label = { Text("Note Title") }, //Textfield label
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )
        //Content text field
        TextField(
            value = noteText, //Set the value of the text field as note text
            onValueChange = { noteText = it }, //Update the value of the text field when it changes
            label = { Text("Note Content") }, //Textfield label
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        LargeFloatingActionButton( //Save button
            containerColor = Color.Blue,
            contentColor = Color.White,
            onClick = {
                onSave(titleText.text, noteText.text) //On click, save the note
            }
        ) {
            Text("Save Note") //Button text
        }
    }
}