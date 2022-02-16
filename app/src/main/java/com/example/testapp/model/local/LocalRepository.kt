package com.example.testapp.model.local

import com.example.testapp.model.models.User

class LocalRepository(private val userDao: UserDao) : ILocalRepository {
    override fun saveUsersList(users: List<User>) {
        for (user in users) {
            userDao.insertUser(convertToRoomUser(user))
        }
    }

    override fun updateUser(user: User) {
        userDao.updateUser(convertToRoomUser(user))
    }

    override fun deleteUser(user: User) {
        userDao.deleteUser(convertToRoomUser(user))
    }

    override fun getLocalUsersList(): List<User> {
        return convertToUsersList(userDao.getUser())
    }

    private fun convertToUsersList(users: List<UserEntity>): List<User> {
        val usersModel = arrayListOf<User>()
        for (user in users) {
            usersModel.add(
                User(
                    user.avatar,
                    user.email,
                    user.first_name,
                    user.id,
                    user.last_name
            ))
        }
        return usersModel
    }

    private fun convertToRoomUser(userModel: User): UserEntity {
        return UserEntity(
            userModel.id,
            userModel.avatar,
            userModel.email,
            userModel.first_name,
            userModel.last_name
        )
    }
}