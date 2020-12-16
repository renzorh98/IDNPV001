package com.example.Model.database

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AppDatabase {

    private val myRef = Firebase.database.reference
    fun getReference(): DatabaseReference {
        return myRef
    }
}