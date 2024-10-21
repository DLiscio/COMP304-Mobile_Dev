package com.damien.liscio.data

data class Contact(
    val name: String,
    val phoneNumber: Long,
    val email: String,
    val contactType: ContactType
)
