package com.example.dto

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UserWithTraining (
    var workoutId: String
){
    @Exclude
    fun toMap(): Map<String, Any?>{
        return mapOf(
            "uid" to  workoutId
        )
    }
}