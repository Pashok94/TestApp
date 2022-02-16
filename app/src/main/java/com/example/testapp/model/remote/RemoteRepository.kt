package com.example.testapp.model.remote

import com.example.testapp.model.models.UsersList
import retrofit2.Callback

class RemoteRepository(
    private val usersApi: UsersApi,
) : IRemoteRepository {
    override fun getUsersPage(callback: Callback<UsersList>, page: Int) {
        usersApi.getUsersPage(page).enqueue(callback)
    }
}