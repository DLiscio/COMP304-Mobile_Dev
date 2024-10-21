package com.damien.liscio.data

enum class ContactType {
    Personal,
    Work,
    Family,
    Other;

    companion object {
        fun getAllTypes() = entries // Helper method to get all contact types
    }
}