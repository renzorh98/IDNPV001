package com.example.Model.Entities

import androidx.room.Embedded
import androidx.room.Relation

data class TrainingAndTrajectory(
    @Embedded val training: Training,
    @Relation(
        parentColumn = "TraCod",
        entityColumn = "TrjTraCod"
    )
    val trajectory: Trajectory
)