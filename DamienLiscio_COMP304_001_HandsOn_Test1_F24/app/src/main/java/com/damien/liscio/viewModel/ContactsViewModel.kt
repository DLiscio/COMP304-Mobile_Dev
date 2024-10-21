package com.damien.liscio.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.damien.liscio.data.Contact
import com.damien.liscio.data.ContactRepository

class ContactsViewModel(
    private val contactRepository: ContactRepository
): ViewModel() {
    private val _contacts = MutableLiveData<List<Contact>>()
    val contacts: LiveData<List<Contact>> get() = _contacts

    init {
        getContacts()
    }

    fun getContacts() {
        _contacts.value = contactRepository.getContacts()
    }

    fun addContact(contact: Contact) {
        val updatedList = _contacts.value.orEmpty().toMutableList().apply {
            add(contact)
        }
        _contacts.value = updatedList
    }
}