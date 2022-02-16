package com.example.testapp.model.repository

import com.example.testapp.model.local.ILocalRepository
import com.example.testapp.model.remote.IRemoteRepository

interface IRepository : IRemoteRepository, ILocalRepository