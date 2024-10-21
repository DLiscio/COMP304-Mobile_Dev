package com.damien.liscio.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.damien.liscio.viewModel.ContactsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun ContactList(modifier: Modifier) {
    val contactsViewModel: ContactsViewModel = koinViewModel()
    val contacts = contactsViewModel.contacts.observeAsState(emptyList())
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    SnackbarHost(
        hostState = snackbarHostState
    )

    LaunchedEffect(contacts.value) {
        val latestContact = contacts.value.lastOrNull()
        latestContact?.let { contact ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = "Added Contact: ${contact.name}, Email: ${contact.email}, Phone: ${contact.phoneNumber}, Contact Type: ${contact.contactType}"
                )
            }
        }
    }

    LazyColumn(
        modifier = modifier
    ) {
        items(contacts.value) { contact ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Name: ${contact.name}")
                Text(text = "Phone Number: ${contact.phoneNumber}")
                Text(text = "Email: ${contact.email}")
                Text(text = "Contact Type: ${contact.contactType}")

                DashedDivider()
            }
        }
    }
}