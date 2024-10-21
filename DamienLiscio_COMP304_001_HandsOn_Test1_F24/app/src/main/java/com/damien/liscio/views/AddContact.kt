package com.damien.liscio.views

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.damien.liscio.data.Contact
import com.damien.liscio.data.ContactType
import com.damien.liscio.viewModel.ContactsViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.ui.graphics.Color

@Composable
fun AddContact(modifier: Modifier) {
    val contactsViewModel: ContactsViewModel = koinViewModel()

    var name by remember { mutableStateOf(TextFieldValue("")) }
    var number by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var selectedType by remember { mutableStateOf(ContactType.Other) }

    val isDark = isSystemInDarkTheme()
    val containerColor = if (isDark) Color.White else Color.Black
    val contentColor = if (isDark) Color.Black else Color.White

    Column(
        modifier = modifier
            .padding(10.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = modifier
                .fillMaxWidth()
        )

        TextField(
            value = number,
            onValueChange = { number = it },
            label = { Text("Number") },
            modifier = modifier
                .fillMaxWidth()
        )

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = modifier
                .fillMaxWidth()
        )

        Text("Select Contact Type:")
        ContactType.getAllTypes().forEach { contactType ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = selectedType == contactType,
                    onCheckedChange = { isChecked ->
                        selectedType = if (isChecked) {
                            contactType
                        } else {
                            ContactType.Other
                        }
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = containerColor,
                        checkmarkColor = contentColor
                    )
                )
                Text(text = contactType.name)
            }
        }

        Button(
            onClick = {
                val contact = Contact(
                    name = name.text,
                    phoneNumber = number.text.toLong(),
                    email = email.text,
                    contactType = selectedType
                )
                contactsViewModel.addContact(contact)
                name = TextFieldValue("")
                number = TextFieldValue("")
                email = TextFieldValue("")
                selectedType = ContactType.Other
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = containerColor,
                contentColor = contentColor
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Contact")
        }
    }
}