package com.example.liveData

import android.annotation.SuppressLint
import android.app.Application
import android.location.Location

import androidx.lifecycle.LiveData
import com.example.dto.Coordinate
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices


class LocationLiveData(application: Application) : LiveData<Coordinate>() {

    private var fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application)

    override fun onInactive() {
        super.onInactive()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }
    @SuppressLint("MissingPermission")
    override fun onActive() {
        super.onActive()
        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            location: Location -> location.also {
            setLocationData(it)
        }
        }
        startLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }
    private val locationCallback = object : LocationCallback (){
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            locationResult ?: return
            for (location in locationResult.locations){
                setLocationData(location)
            }
        }
    }
    private fun setLocationData(location: Location){
        value = Coordinate(location.latitude.toString(), location.longitude.toString())
    }

    companion object{
        val locationRequest: LocationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 10000/4
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }


}