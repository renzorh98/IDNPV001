package com.example.Model.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.Model.Entities.Training
import com.example.Model.Entities.TrainingAndTrajectory
import com.example.Model.Entities.Trajectory
import com.example.Model.Entities.TrajectoryWithCoordinates

@Dao
interface TrajectoryDao {

    @Transaction
    @Query("SELECT * FROM Trajectory")
    fun getTrajectories(): List<Trajectory>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(trajectory: Trajectory)

    @Query("DELETE FROM Trajectory")
    suspend fun deleteAll()

}