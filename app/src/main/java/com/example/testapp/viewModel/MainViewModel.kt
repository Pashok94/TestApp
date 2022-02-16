package com.example.testapp.viewModel

import androidx.lifecycle.viewModelScope
import com.example.testapp.model.models.User
import com.example.testapp.model.models.UsersList
import com.example.testapp.model.repository.IRepository
import com.example.testapp.viewModel.models.UserVM
import com.example.testapp.viewModel.utils.AppState
import com.example.testapp.viewModel.utils.INetworkObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(
    repository: IRepository,
    networkObserver: INetworkObserver
) : BaseViewModel(repository, networkObserver) {

    fun getUsers() {
        if (networkObserver.isUserOnline()) {
            repository.getUsersPage(callbackGetUser, 2)
        } else {
            loadLocalUsers()
        }
    }

    private fun saveUsersToDb(users: List<User>) {
        viewModelScope.launch {
            launch(Dispatchers.IO) {
                repository.saveUsersList(users)
            }
        }
    }

    private val callbackGetUser = object : Callback<UsersList> {
        override fun onResponse(call: Call<UsersList>, response: Response<UsersList>) {
            liveData.postValue(
                if (response.isSuccessful && response.body() != null) {
                    val users = UserVM.convertFromUsersList(response.body()!!.data)
                    saveUsersToDb(response.body()!!.data)
                    AppState.SuccessLoadUsers(users)
                } else
                    AppState.Error(Exception(response.message()))
            )
        }

        override fun onFailure(call: Call<UsersList>, t: Throwable) {
            liveData.postValue(
                AppState.Error(t)
            )
        }
    }
}