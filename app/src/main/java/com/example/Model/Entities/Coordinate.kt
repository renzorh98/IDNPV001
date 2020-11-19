package com.example.Model.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Coordinate (
    @PrimaryKey(autoGenerate = true) val CooDoc: Int,
    val CooTraCod: Int,
    val CooLat: Int?,
    val CooLng: Int?
)