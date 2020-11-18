package com.example.Model.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.Model.Entities.User

@Dao
interface UserDao {

    @Query("SELECT * FROM users ORDER BY userId ASC")
    fun getAlphabetizedWords(): List<User>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Query("DELETE FROM users")
    suspend fun deleteAll()

}