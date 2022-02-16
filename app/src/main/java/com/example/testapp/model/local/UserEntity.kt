package com.example.testapp.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_data")
data class UserEntity(
    @PrimaryKey val id: Int,
    val avatar: String,
    val email: String,
    val first_name: String,
    val last_name: String
)