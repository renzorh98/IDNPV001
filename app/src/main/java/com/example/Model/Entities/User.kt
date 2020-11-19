package com.example.Model.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName =  "User")
data class User(
    @PrimaryKey(autoGenerate = true) val UseCod: Int,
    @ColumnInfo(name = "UseNam") val UseNam: String?,
    @ColumnInfo(name = "UseEma") val UseEma: String?,
    @ColumnInfo(name = "UsePas") val UsePas: String?
)