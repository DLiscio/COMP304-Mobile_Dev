package com.example.damienliscio_comp304lab1ex1

//Import Statements
import androidx.compose.runtime.mutableStateListOf

//Note Data Class
data class Note(var title: String, var content: String)
object NoteDataSource{  //Note Data Source Object
    val notesList = mutableStateListOf( //List of pre-filled notes
        Note("Short Note Example", "This is a short note example."),
        Note("Long Note Example", "This is a long note example to show the display of the note in the notes list only displays a portion of a longer note"),
        Note("Grocery List", "1. Apples 2. Bananas 3. Milk")
    )

    //Method for adding notes
    fun addNote(note: Note) {
        notesList.add(note) //Add note to list
    }

    //Method for updating notes
    fun updateNote(index: Int, newTitle: String, newContent: String) {
        if (index >= 0 && index < notesList.size) { //If the index is valid, update the note
            notesList[index] = Note(newTitle, newContent) //Update given methods note data
        }
    }
}