package com.example.ViewModel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi

import androidx.lifecycle.AndroidViewModel
import com.example.Model.database.AppDatabase
import com.example.View.UI.Fragments.workout.LocationLiveData
import com.example.dto.Coordinate
import com.example.dto.Training
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DatabaseReference
import java.time.LocalDateTime


class WorkoutViewModel(application: Application) : AndroidViewModel(application) {

    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)
    private val locationLiveData = LocationLiveData(application)
    internal fun getLocationLiveData() = locationLiveData
    var database: AppDatabase = AppDatabase()
    private var ref: DatabaseReference
    init {
        ref = database.getReference()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addTraining():Training? {
        val key = ref.child("trainings").push().key ?: return null
        var training = Training(key,LocalDateTime.now(),0.0,"", "run")
        ref.child("trainings").child(key).setValue(training)
        //addUserWithTraining(key)
        return training
    }

    private fun addUserWithTraining(key: String?) {
        ref.child("userWithTrainings").child("0").setValue(key)
    }
    fun addCoordinateToTraining(key: String?, coordinate: Coordinate){
        if(key!=null){
            val coordinateKey = ref.child("trainings").push().key ?: return
            ref.child("trainings").child(key).child("coordinates").child(coordinateKey).setValue(coordinate)
        }
    }




    @RequiresApi(Build.VERSION_CODES.O)
    fun setAttributesTraining(training: Training?, text: CharSequence?, distance: Double) {
        if(training != null){
            ref.child("trainings").child(training.trainingId).child("time").setValue(text)
            ref.child("trainings").child(training.trainingId).child("distance").setValue(distance)
        }
    }

}