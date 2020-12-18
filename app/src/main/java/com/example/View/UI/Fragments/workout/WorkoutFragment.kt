package com.example.View.UI.Fragments.workout

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.SystemClock

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ViewModel.WorkoutViewModel
import com.example.dto.Coordinate
import com.example.dto.Training
import com.example.idnpv001.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.android.synthetic.main.fragment_workout.*
import kotlin.math.pow
import kotlin.math.sqrt


class WorkoutFragment : Fragment(), OnMapReadyCallback {


    private val LOCATION_PERMISSION_REQUEST_CODE = 2000
    private lateinit var workoutViewModel: WorkoutViewModel
    private lateinit var mMap: GoogleMap
    private var training: Training? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        workoutViewModel =
            ViewModelProvider(this).get(WorkoutViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_workout, container, false)


        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        mapFragment.onCreate(this.arguments)


        return root
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this.requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    }


    override fun onMapReady(googleMap: GoogleMap?) {
        if (googleMap != null) {
            mMap = googleMap
        }
        if (ActivityCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mMap.isMyLocationEnabled = true
        var isTraining: Boolean
        var locationTemp =  Coordinate(0.0,0.0)
        var distance = 0.0

        if(hasLocationPermission()){

            button.setOnClickListener {
                mMap.clear()
                if (googleMap != null) {
                    mMap = googleMap
                }
                isTraining = true
                training = workoutViewModel.addTraining()
                chronometer.start()
                button2.isEnabled = true
                button.isEnabled = false

                val polylineOptions = PolylineOptions()
                    .width(5.0F)
                    .color(Color.RED)

                workoutViewModel.getLocationLiveData().observe(this, Observer {
                    if(it!=null){
                        if(isTraining){
                            if(locationTemp.latitude == 0.0 && locationTemp.latitude == 0.0 ){
                                locationTemp = it
                                mMap.addMarker(
                                    MarkerOptions()
                                        .position(LatLng(it.latitude, it.longitude))
                                        .title("Inicio"))
                            }
                            distance += sqrt((locationTemp.latitude - it.latitude).pow(2) + (locationTemp.longitude - it.longitude).pow(2))
                            textView5.text = distance.toString()
                            workoutViewModel.addCoordinateToTraining(training?.trainingId, it)
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), 16.0f))
                            polylineOptions.add(LatLng(it.latitude, it.longitude))
                            mMap.addPolyline(
                                polylineOptions
                            )
                            locationTemp = it
                        }
                    }
                })
            }
            button2.setOnClickListener {
                isTraining = false
                chronometer.stop()
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    workoutViewModel.setAttributesTraining(training, chronometer.text, distance)
                }
                mMap.addMarker(
                    MarkerOptions()
                        .position(LatLng(locationTemp.latitude, locationTemp.longitude))
                        .title("Fin"))
                distance = 0.0
                textView5.text = distance.toString()
                chronometer.base = SystemClock.elapsedRealtime()
                button2.isEnabled = false
                button.isEnabled = true
            }
        }
        else{
            val permissionRequest = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            requestPermissions(permissionRequest, LOCATION_PERMISSION_REQUEST_CODE)
        }
    }
}