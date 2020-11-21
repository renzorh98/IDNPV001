package com.example.Model.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.Model.Entities.Training
import com.example.Model.Entities.TrainingAndTrajectory

@Dao
interface TrainingDao {

    @Transaction
    @Query("SELECT * FROM Training")
    fun getTrainingAndTrajectory(): List<Training>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(training: Training)

    @Query("DELETE FROM Training")
    suspend fun deleteAll()

}