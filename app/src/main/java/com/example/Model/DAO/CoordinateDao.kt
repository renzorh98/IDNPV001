package com.example.Model.DAO

import androidx.room.*
import com.example.Model.Entities.Training
import com.example.Model.Entities.TrainingAndTrajectory

@Dao
interface CoordinateDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(training: Training)

    @Query("DELETE FROM Training")
    suspend fun deleteAll()

}