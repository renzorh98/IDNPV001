package com.example.dto

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class WorkoutFirebase(
    var coordinates: MutableList<Coordinate>,
    var date: Long,
    var distance: Double,
    var time: String,
    var trainingId: String,
    var type: String
) {
    @Exclude
    fun toMap(): Map<String, Any?>{
        return mapOf(
            "coordinates" to coordinates,
            "date" to date,
            "distance" to distance,
            "time" to time,
            "trainingId" to trainingId,
            "type" to type
        )
    }
}