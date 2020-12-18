package com.example.Model.Adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.os.persistableBundleOf
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.Model.Entities.Workout
import com.example.idnpv001.R
import kotlinx.android.synthetic.main.music_list_item.view.*
import kotlinx.android.synthetic.main.workout_list_item.view.*

class WorkoutHistoryAdapter(private val workouts: List<Workout>) : RecyclerView.Adapter<WorkoutHistoryAdapter.WorkoutHistoryHolder>() {

    class WorkoutHistoryHolder(v: View): RecyclerView.ViewHolder(v){
        var view: View = v
        var workoutDistance: TextView? = null
        var workoutTime: TextView? = null
        var workoutDate: TextView? = null

        init {
            workoutDistance = view.workout_list_item_distance
            workoutTime = view.workout_list_item_time
            workoutDate = view.workout_list_item_date
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutHistoryAdapter.WorkoutHistoryHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.workout_list_item, parent, false)
        return WorkoutHistoryHolder(view)
    }

    override fun onBindViewHolder(holder: WorkoutHistoryAdapter.WorkoutHistoryHolder, position: Int) {
        val workout = workouts.get(position)

        holder.itemView.setOnClickListener {
            val workoutId = workout.trainnig
            val bundle = bundleOf("workoutId" to workoutId)
            it.findNavController().navigate(R.id.action_navigation_history_to_fragment_history_detail, bundle)
        }

        holder.workoutDistance?.text = workout.distance
        holder.workoutTime?.text = workout.time
        holder.workoutDate?.text = workout.date

    }

    override fun getItemCount() = workouts.size
}