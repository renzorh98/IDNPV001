package com.example.View.UI.Fragments.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Model.Adapters.WorkoutHistoryAdapter
import com.example.Model.Entities.Workout
import com.example.View.UI.MainActivityView
import com.example.ViewModel.HistoryViewModel
import com.example.ViewModel.LoginViewModel
import com.example.idnpv001.R

class HistoryFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel
    private var myworkouts: List<Workout>? = null
    private var workouts: RecyclerView? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var workoutAdapter: WorkoutHistoryAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //GET DATA FROM DATABASE


        historyViewModel =
            ViewModelProvider(this).get(HistoryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_history, container, false)
        val textView: TextView = root.findViewById(R.id.text_history_title)

        historyViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        //logout button
        val logOutBtn: ImageView = root.findViewById(R.id.logOutButton)

        logOutBtn.setOnClickListener {
            var loginViewModel: LoginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
            loginViewModel.logout()
            val intent = Intent(activity, MainActivityView::class.java)
            (activity as MainActivityView).musicService?.stop()
            (activity as MainActivityView).playerList?.restartPositionPlayList()
            startActivity(intent)
            activity?.finish()
        }

        myworkouts = listOf(
            Workout(null, "Wake up", "12-12-2020", "00:45:00", "1,40km", "1000", "10.5km"),
            Workout(null, "Wake up", "12-12-2020", "00:45:00", "1,40km", "1000", "10.5km"),
            Workout(null, "Wake up", "12-12-2020", "00:45:00", "1,40km", "1000", "10.5km"),
            Workout(null, "Wake up", "12-12-2020", "00:45:00", "1,40km", "1000", "10.5km"),
            Workout(null, "Wake up", "12-12-2020", "00:45:00", "1,40km", "1000", "10.5km")
        )

        workouts = root.findViewById(R.id.recyclerViewHistory)
        layoutManager = LinearLayoutManager(root.context)
        workoutAdapter = WorkoutHistoryAdapter(myworkouts!!)
        workouts?.layoutManager = layoutManager
        workouts?.adapter = workoutAdapter

        root.findViewById<TextView>(R.id.text_history_totalTime)

        root.findViewById<TextView>(R.id.text_history_totalCalories)

        root.findViewById<TextView>(R.id.text_history_totalDistance)

        root.findViewById<TextView>(R.id.text_history_totalRuns)

        return root
    }
}