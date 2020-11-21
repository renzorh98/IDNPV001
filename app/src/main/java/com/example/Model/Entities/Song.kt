package com.example.Model.Entities

import android.net.Uri

class Song(
    val fileUri: Uri?,
    val fileIcon: String?,
    val name: String,
    val artist: String
) {
}