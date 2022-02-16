package com.example.testapp.model.remote

import com.example.testapp.model.models.UsersList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersApi {
    @GET("api/users")
    fun getUsersPage(
        @Query("page") page: Int,
    ): Call<UsersList>
}