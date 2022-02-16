package com.example.testapp.model.remote

import com.example.testapp.model.models.UsersList
import retrofit2.Callback

interface IRemoteRepository {
    fun getUsersPage(callback: Callback<UsersList>, page: Int)
}