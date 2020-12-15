package com.example.View.UI.Fragments.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.Model.Entities.Workout
import com.example.idnpv001.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_history_detail.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryDetail : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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

        val workoutId = arguments?.get("workoutId")

        val backButton = root.findViewById<ImageView>(R.id.imageViewBackHistoryDetail)
        val nameWorkout = root.findViewById<TextView>(R.id.textViewDetailNameWorkout)
        val dateWorkout = root.findViewById<TextView>(R.id.textViewDetailDate)
        val timeWorkout = root.findViewById<TextView>(R.id.textViewDetailTime)
        val distanceWorkout = root.findViewById<TextView>(R.id.textViewDetailDistance)
        val caloriesWorkout = root.findViewById<TextView>(R.id.textViewDetailCalories)
        val speedWorkout = root.findViewById<TextView>(R.id.textViewDetailSpeed)

        backButton.setOnClickListener {
            findNavController().navigate(R.id.navigation_history)
        }

        nameWorkout.setText(workoutId as String)
        /*
        dateWorkout.setText(workout.date)
        timeWorkout.setText(workout.time)
        distanceWorkout.setText(workout.distance)
        caloriesWorkout.setText(workout.calories)
        speedWorkout.setText(workout.speed)
        */
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
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HistoryDetail().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}