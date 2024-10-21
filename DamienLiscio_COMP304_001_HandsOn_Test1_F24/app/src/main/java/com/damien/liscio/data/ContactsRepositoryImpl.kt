package com.damien.liscio.data

class ContactsRepositoryImpl : ContactRepository {
    private val contacts = mutableListOf<Contact>()


    override fun getContacts(): List<Contact> {
        return contacts
    }

    override fun addContact(contact: Contact) {
        contacts.add(contact)
    }
}