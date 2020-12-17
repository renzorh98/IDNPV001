package com.example.dto

import java.time.LocalDateTime

data class Training (
    var trainingId: String,
    var date: LocalDateTime,
    var distance: Double,
    var time: String,
    var type: String
)