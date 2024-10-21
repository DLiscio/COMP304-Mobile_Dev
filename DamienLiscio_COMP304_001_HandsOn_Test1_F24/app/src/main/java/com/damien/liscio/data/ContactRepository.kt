package com.damien.liscio.data

interface ContactRepository {
    fun getContacts(): List<Contact>
    fun addContact(contact: Contact)
}