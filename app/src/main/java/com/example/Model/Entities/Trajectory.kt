package com.example.Model.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName =  "Trajectory")
data class Trajectory (
    @PrimaryKey(autoGenerate = true) val TrjCod: Int,
    val TrjTraCod: Int?,
    val TrjTyp: Int?,
    val TrjSta: Int?,
    val TrjEnd: Int?
)