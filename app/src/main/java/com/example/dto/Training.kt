package com.example.dto

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Training (
    var trainingId: String,
    var date: Long,
    var distance: Double,
    var time: String,
    var type: String
){

}