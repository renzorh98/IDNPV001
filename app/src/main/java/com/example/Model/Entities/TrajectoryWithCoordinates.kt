package com.example.Model.Entities

import androidx.room.Embedded
import androidx.room.Relation

data class TrajectoryWithCoordinates (
    @Embedded val trajectory: Trajectory,
    @Relation(
        parentColumn = "TrjCod",
        entityColumn = "CooTrjCod"
    )
    val coordinates: List<Coordinate>
)