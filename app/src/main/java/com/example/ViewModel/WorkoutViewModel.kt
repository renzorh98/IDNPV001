package com.example.ViewModel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import com.example.dto.Coordinate
import com.example.dto.Training
import com.example.liveData.LocationLiveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class WorkoutViewModel(application: Application) : AndroidViewModel(application) {

    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var reference: DatabaseReference
    init {
        reference = database.getReference("idnpv001-default-rtdb")
    }
    private val locationLiveData = LocationLiveData(application)
    fun getLocationLiveData() = locationLiveData
    fun addCoordinate(trainingId: String,coordinate: Coordinate) {
        val id = reference.push().key
        reference.child("trainings").child(trainingId).child("coordinates").child(id!!).setValue(coordinate)
    }

    fun createTraining(): Training {
        val id = reference.push().key
        val training = Training(id!!,"00/00/0000",0,"0","run")

        reference.child("trainings").child(id).setValue(training)
        return training
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setData(trainingId: String, time: CharSequence?) {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        reference.child("trainings").child(trainingId).child("date").setValue(LocalDateTime.now().format(formatter))
        reference.child("trainings").child(trainingId).child("time").setValue(time)
    }
}