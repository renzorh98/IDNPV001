package com.example.dto

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Coordinate (
    var longitude: Double,
    var latitude:Double)
{
    @Exclude
    fun toMap(): Map<String, Any?>{
        return mapOf(
            "longitude" to longitude,
            "latitude" to  latitude
        )
    }
}