package com.example.View.UI.Fragments.history

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.dto.Coordinate
import com.example.dto.Training
import com.example.idnpv001.R
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_history_detail.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryDetail : Fragment(), OnMapReadyCallback {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var map: GoogleMap
    private lateinit var database: FirebaseDatabase
    private lateinit var myRef: DatabaseReference
    private lateinit var workout: Training
    private lateinit var route: MutableList<Coordinate>
    private lateinit var nameWorkout:TextView
    private lateinit var dateWorkout:TextView
    private lateinit var timeWorkout:TextView
    private lateinit var distanceWorkout:TextView
    private lateinit var caloriesWorkout:TextView
    private lateinit var speedWorkout:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_history_detail, container, false)

        val backButton = root.findViewById<ImageView>(R.id.imageViewBackHistoryDetail)
        nameWorkout = root.findViewById<TextView>(R.id.textViewDetailNameWorkout)
        dateWorkout = root.findViewById<TextView>(R.id.textViewDetailDate)
        timeWorkout = root.findViewById<TextView>(R.id.textViewDetailTime)
        distanceWorkout = root.findViewById<TextView>(R.id.textViewDetailDistance)
        caloriesWorkout = root.findViewById<TextView>(R.id.textViewDetailCalories)
        speedWorkout = root.findViewById<TextView>(R.id.textViewDetailSpeed)

        val workoutMapView = childFragmentManager.findFragmentById(R.id.mapViewWorkout) as SupportMapFragment
        workoutMapView.onCreate(this.arguments)
        workoutMapView.getMapAsync(this)

        backButton.setOnClickListener {
            findNavController().navigate(R.id.navigation_history)
        }


        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_history_detail.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HistoryDetail().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onMapReady(p0: GoogleMap?) {
        p0?.let {
            map = it

            val workoutId = arguments?.get("workoutId")

            workoutId?.let {
                database = FirebaseDatabase.getInstance()
                //child to wokrout id
                myRef = database.getReference("trainings").child(it as String)

                val postListener = object: ValueEventListener{

                    override fun onDataChange(snapshot: DataSnapshot) {
                        route = mutableListOf<Coordinate>()

                        var date = ""
                        var distance = 0.0
                        var time = "00:00"
                        var name = "Workout"
                        var calories = "0"
                        var speed = "0 km/h"

                        snapshot.child("date").getValue<Long>()?.let {
                            val aux = Date(it)
                            val format = SimpleDateFormat("HH:mm yyyy.MM.dd")
                            date = format.format(aux)
                        }

                        snapshot.child("distance").getValue<Double>()?.let {
                            distance = it
                        }

                        snapshot.child("time").getValue<String>()?.let {
                            if(it.length > 1){
                                time = it
                            }
                        }

                        snapshot.child("type").getValue<String>()?.let {
                            name = it
                        }

                        val coordinates = snapshot.child("coordinates")

                        for(c in coordinates.children){
                            var lat = 0.0
                            var lon = 0.0

                            c.child("latitude").getValue<Double>()?.let {
                                lat = it
                            }

                            c.child("longitude").getValue<Double>()?.let {
                                lon = it
                            }

                            route.add(Coordinate(lon, lat))
                        }

                        val points = PolylineOptions()

                        for(r in route){
                            points.add(LatLng(r.latitude, r.longitude))
                        }

                        points.clickable(true)

                        val workoutPath:Polyline = map.addPolyline(points)

                        if(route.size > 0) {
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(route[0].latitude, route[0].longitude), 18.0f))
                        }


                        updateView(name, date, time, distance.toString().substring(0, distance.toString().indexOf(".") + 2) + "K", calories, speed)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("ERORR", "fail")
                    }

                }

                myRef.addListenerForSingleValueEvent(postListener)
            }


        }
    }


    fun updateView(name:String, date:String, time:String, distance:String, calories:String, speed:String){
        nameWorkout.text = name
        dateWorkout.text = date
        timeWorkout.text = time
        distanceWorkout.text = distance
        caloriesWorkout.text = calories
        speedWorkout.text = speed
    }
}