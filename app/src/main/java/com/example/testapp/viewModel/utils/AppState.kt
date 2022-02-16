package com.example.testapp.viewModel.utils

import com.example.testapp.viewModel.models.UserVM

sealed class AppState {
    data class Error(val error: Throwable) : AppState()
    data class SuccessLoadUsers(val users: List<UserVM>) : AppState()
}