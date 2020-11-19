package com.example.Model.Entities

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithTraining (
    @Embedded val user: User,
    @Relation(
        parentColumn = "UseCod",
        entityColumn = "TraUsuCod"
    )
    val trainings: List<Training>
)