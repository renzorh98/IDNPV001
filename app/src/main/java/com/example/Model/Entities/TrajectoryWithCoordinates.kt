package com.example.Model.Entities

import androidx.room.Embedded
import androidx.room.Relation

data class TrajectoryWithCoordinates (
    @Embedded val trajectory: Trajectory,
    @Relation(
        parentColumn = "TraCod",
        entityColumn = "CooTraCod"
    )
    val coordinates: List<Coordinate>
)