package com.example.testapp.model.local

import com.example.testapp.model.models.User

interface ILocalRepository {
    fun saveUsersList(users: List<User>)
    fun updateUser(user: User)
    fun deleteUser(user: User)
    fun getLocalUsersList(): List<User>
}