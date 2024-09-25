package com.example.damienliscio_comp304lab1ex1

//Import statements
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

//CreateNote Activity Class
class CreateNote : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CreateNoteScreen{ title, content -> //Set the content of the screen as CreateNote Screen
                NoteDataSource.addNote(Note(title, content)) //Add the note to the note list using data class objects method
                finish() //Finish the activity
            }
        }
    }
}

//CreateNote Screen function
@Composable
fun CreateNoteScreen(onSave: (String, String) -> Unit) {
    var titleText by remember { mutableStateOf(TextFieldValue("")) }  //Title text
    var noteText by remember { mutableStateOf(TextFieldValue("")) } //Note text

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
            Text(text = "Create Note", fontSize = 40.sp, color = Color.White, modifier = Modifier.padding(16.dp))
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
            onClick = { //On click, save the note
                onSave(titleText.text, noteText.text)
            }
        ) {
            Text("Save Note") //Button text
        }
    }
}

