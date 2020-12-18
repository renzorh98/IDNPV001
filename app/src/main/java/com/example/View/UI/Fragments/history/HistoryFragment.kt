package com.example.View.UI.Fragments.history

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.Repository.LoginRepository
import com.example.View.UI.MainActivityView
import com.example.ViewModel.HistoryViewModel
import com.example.ViewModel.LoginViewModel
import com.example.dto.UserWithTraining
import com.example.dto.WorkoutFirebase
import com.example.idnpv001.R
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import java.text.SimpleDateFormat
import java.util.*

class HistoryFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var myworkouts: MutableList<Workout>
    private var workouts: RecyclerView? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var workoutAdapter: WorkoutHistoryAdapter? = null
    private lateinit var database: FirebaseDatabase
    private lateinit var myRef: DatabaseReference
    private lateinit var loginRepository: LoginRepository
    private var totalTime = 0
    private var totalRuns = 0
    private var calories = 0
    private var totalDistance = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //GET DATA FROM DATABASE
        val root = inflater.inflate(R.layout.fragment_history, container, false)

        historyViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        val textView: TextView = root.findViewById(R.id.text_history_title)

        historyViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        loginRepository = LoginRepository(root.context)
        val user_key = loginRepository.getCurrentUser()

        database = FirebaseDatabase.getInstance()
        myRef = database.getReference("userWithTrainings").child("-MOnoNhYGdrNY_UTv6Re")

        var trainings = mutableListOf<String>()
        myworkouts = mutableListOf<Workout>()

        val postListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for( wId in snapshot.children){
                    wId.key?.let { trainings.add(it) }
                }
                totalRuns = trainings.size
                readWorkouts(trainings)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ERR", "F")
            }

        }

        myRef.addValueEventListener(postListener)

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

/*
        myworkouts = listOf(
            Workout(null, "Wake up", "12-12-2020", "00:45:00", "1,40km", "1000", "10.5km"),
            Workout(null, "Wake up", "12-12-2020", "00:45:00", "1,40km", "1000", "10.5km"),
            Workout(null, "Wake up", "12-12-2020", "00:45:00", "1,40km", "1000", "10.5km"),
            Workout(null, "Wake up", "12-12-2020", "00:45:00", "1,40km", "1000", "10.5km"),
            Workout(null, "Wake up", "12-12-2020", "00:45:00", "1,40km", "1000", "10.5km")
        )
*/
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

    fun readWorkouts(trainings: MutableList<String>){

        for(t in trainings){
            myRef = database.getReference("trainings").child(t)

            val postListener = object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    var date = ""
                    var distance = 0.0
                    var trainingId = t
                    var time = "00:00"
                    var type = "Workout"

                    snapshot.child("date").getValue<Long>()?.let {
                        val aux = Date(it)
                        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
                        date = format.format(aux)
                    }

                    snapshot.child("distance").getValue<Double>()?.let {
                        distance = it
                    }

                    snapshot.child("trainingId").getValue<String>()?.let {
                        trainingId = it
                    }

                    snapshot.child("time").getValue<String>()?.let {
                        time = it
                    }

                    snapshot.child("type").getValue<String>()?.let {
                        type = it
                    }

                    val newWorkout = Workout(trainingId, type, date, time, "", "", distance.toString())

                    myworkouts.add(newWorkout)

                    workoutAdapter?.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("ERROR", "Workouts")
                }

            }

            myRef.addValueEventListener(postListener)
        }
    }
}