package com.example.testapp.model.repository

import com.example.testapp.model.local.ILocalRepository
import com.example.testapp.model.remote.IRemoteRepository

class Repository(
    private val localRepository: ILocalRepository,
    private val remoteRepository: IRemoteRepository
) : IRepository, IRemoteRepository by remoteRepository, ILocalRepository by localRepository