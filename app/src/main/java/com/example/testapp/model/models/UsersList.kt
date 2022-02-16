package com.example.testapp.model.models

data class UsersList(
    val `data`: List<User>,
    val page: Int,
    val per_page: Int,
    val support: Support,
    val total: Int,
    val total_pages: Int
)