package com.example.testapp.viewModel

import androidx.lifecycle.viewModelScope
import com.example.testapp.model.repository.IRepository
import com.example.testapp.viewModel.models.UserVM
import com.example.testapp.viewModel.utils.INetworkObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel(
    repository: IRepository,
    networkObserver: INetworkObserver
): BaseViewModel(repository, networkObserver) {
    fun updateUser(userVM: UserVM){
        viewModelScope.launch {
            launch(Dispatchers.IO) {
                repository.updateUser(UserVM.convertToUserModel(userVM))
            }
        }
        loadLocalUsers()
    }

    fun deleteUser(userVM: UserVM){
        viewModelScope.launch {
            launch(Dispatchers.IO) {
                repository.deleteUser(UserVM.convertToUserModel(userVM))
            }
        }
        loadLocalUsers()
    }
}