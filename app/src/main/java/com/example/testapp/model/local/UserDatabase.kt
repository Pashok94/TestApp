package com.example.testapp.model.local

import androidx.room.RoomDatabase

@androidx.room.Database(
    entities = [
        UserEntity::class
    ],
    version = 1
)
abstract class UserDatabase : RoomDatabase() {
    abstract val userDao: UserDao
}