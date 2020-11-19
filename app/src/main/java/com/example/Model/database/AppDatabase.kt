package com.example.Model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.Model.DAO.TrainingDao
import com.example.Model.DAO.TrajectoryDao
import com.example.Model.DAO.UserDao
import com.example.Model.Entities.Training
import com.example.Model.Entities.Trajectory
import com.example.Model.Entities.User


@Database(entities = [User:: class, Trajectory:: class, Training::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun trajectoryDao(): TrajectoryDao
    abstract fun trainingDao(): TrainingDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "app_database"
                ).build()
                INSTANCE = db
                return db
            }
        }
    }

}