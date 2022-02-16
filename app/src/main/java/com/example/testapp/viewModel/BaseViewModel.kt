package com.example.testapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.model.repository.IRepository
import com.example.testapp.viewModel.models.UserVM
import com.example.testapp.viewModel.utils.AppState
import com.example.testapp.viewModel.utils.INetworkObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class BaseViewModel(
    protected var repository: IRepository,
    protected var networkObserver: INetworkObserver,
    protected val liveData: MutableLiveData<AppState> = MutableLiveData()
) : ViewModel() {
    fun getLiveData(): LiveData<AppState> {
        return liveData
    }

    fun getLocalUsers() {
        loadLocalUsers()
    }

    protected fun loadLocalUsers() {
        viewModelScope.launch {
            launch(Dispatchers.IO) {
                val users = UserVM.convertFromUsersList(repository.getLocalUsersList())
                liveData.postValue(AppState.SuccessLoadUsers(users))
            }
        }
    }
}