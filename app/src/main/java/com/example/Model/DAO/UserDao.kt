package com.example.Model.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.Model.Entities.User
import com.example.Model.Entities.UserWithTraining

@Dao
interface UserDao {

    @Transaction
    @Query("SELECT * FROM User")
    fun getUserWithTraining(): List<UserWithTraining>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Update
    fun updateUser(vararg users: User)

    @Delete
    fun deleteUsers(vararg users: User)

    @Query("DELETE FROM User")
    suspend fun deleteAll()

}