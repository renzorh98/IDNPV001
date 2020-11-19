package com.example.Model.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Training (
    @PrimaryKey(autoGenerate = true) val TraCod: Int,
    val TraUsuCod: Int?,
    val TraCal: Int?,
    val TraDat: Int?,
    val TraSta: Int?,
    val TraEnd: Int?,
    val TraKil: Int?,
    val TraTim: Int?
)
