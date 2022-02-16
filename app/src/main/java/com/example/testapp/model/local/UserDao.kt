package com.example.testapp.model.local

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface UserDao {
    @Insert(onConflict = REPLACE)
    fun insertUser(user: UserEntity)

    @Query("SELECT * FROM user_data")
    fun getUser(): List<UserEntity>

    @Update
    fun updateUser(user: UserEntity)

    @Delete
    fun deleteUser(user: UserEntity)
}