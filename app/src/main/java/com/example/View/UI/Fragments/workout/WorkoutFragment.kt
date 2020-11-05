package com.example.View.UI.Fragments.workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ViewModel.WorkoutViewModel
import com.example.idnpv001.R

class WorkoutFragment : Fragment() {

    private lateinit var workoutViewModel: WorkoutViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        workoutViewModel =
            ViewModelProvider(this).get(WorkoutViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_workout, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        workoutViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}