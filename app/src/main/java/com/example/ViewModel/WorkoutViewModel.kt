package com.example.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dto.Coordinate
import com.example.liveData.LocationLiveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

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

class WorkoutViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is workout Fragment"
    }
    val text: LiveData<String> = _text
}}