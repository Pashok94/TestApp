package com.example.testapp.viewModel.models

import android.os.Parcelable
import com.example.testapp.model.models.User
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserVM(
    val avatar: String,
    val email: String,
    var first_name: String,
    val id: Int,
    var last_name: String
) : Parcelable {
    companion object {
        fun convertFromUsersList(users: List<User>): List<UserVM> {
            val usersVM = arrayListOf<UserVM>()
            for (user in users) {
                usersVM.add(
                    UserVM(
                        user.avatar,
                        user.email,
                        user.first_name,
                        user.id,
                        user.last_name
                    )
                )
            }
            return usersVM
        }

        fun convertToUserModel(userVM: UserVM): User {
            return User(
                userVM.avatar,
                userVM.email,
                userVM.first_name,
                userVM.id,
                userVM.last_name
            )
        }
    }
}