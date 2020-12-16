package com.example.ViewModel

import android.app.Application

import android.location.Location

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class WorkoutViewModel(application: Application) : AndroidViewModel(application) {


    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()

    private var reference: DatabaseReference

    init {
        reference = database.getReference("idnpv001-default-rtdb")
    }


    fun getCurrentLocation(){
        var myPosition: LatLng
        fusedLocationClient.lastLocation
            .addOnSuccessListener {
                    location : Location ->
                Log.e("Location", "Latitude" + location.latitude +" "+ location.longitude)
            }
    }
}