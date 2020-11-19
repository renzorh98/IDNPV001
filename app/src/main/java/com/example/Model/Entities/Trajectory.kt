package com.example.Model.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName =  "Trajectory")
data class Trajectory (
    @PrimaryKey(autoGenerate = true) val TrjCod: Int,
    @ColumnInfo(name = "TrjTraCod") val TrjTraCod: Int?,
    @ColumnInfo(name = "TrjTyp") val TrjTyp: Int?,
    @ColumnInfo(name = "TrjSta") val TrjSta: Int?,
    @ColumnInfo(name = "TrjEnd") val TrjEnd: Int?
)