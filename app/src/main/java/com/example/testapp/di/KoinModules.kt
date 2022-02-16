package com.example.testapp.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.room.Room
import com.example.testapp.model.local.ILocalRepository
import com.example.testapp.model.local.LocalRepository
import com.example.testapp.model.local.UserDao
import com.example.testapp.model.local.UserDatabase
import com.example.testapp.model.remote.IRemoteRepository
import com.example.testapp.model.remote.RemoteRepository
import com.example.testapp.model.remote.UsersApi
import com.example.testapp.model.repository.IRepository
import com.example.testapp.model.repository.Repository
import com.example.testapp.ui.NetworkObserverImpl
import com.example.testapp.viewModel.DetailsViewModel
import com.example.testapp.viewModel.MainViewModel
import com.example.testapp.viewModel.utils.INetworkObserver
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val repositoryModule = module {
    fun provideRepository(
        localRepository: ILocalRepository,
        remoteRepository: IRemoteRepository
    ): IRepository {
        return Repository(localRepository, remoteRepository)
    }

    fun provideLocalRepository(userDao: UserDao): ILocalRepository {
        return LocalRepository(userDao)
    }

    fun provideRemoteRepository(
        usersApi: UsersApi
    ): IRemoteRepository {
        return RemoteRepository(usersApi)
    }

    fun provideUsersApi(
        client: OkHttpClient,
        baseUrl: String
    ): UsersApi {
        return Retrofit.Builder()
            .baseUrl(
                baseUrl
            )
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            )
            .client(client)
            .build().create(UsersApi::class.java)
    }

    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClient.build()
    }

    fun provideInterceptor(): Interceptor {
        return Interceptor {
            it.proceed(it.request())
        }
    }

    fun provideUsersBaseUrl(): String = "https://reqres.in/"

    single { provideOkHttpClient(interceptor = get()) }

    single { provideInterceptor() }

    single(named("baseUrl")) { provideUsersBaseUrl() }

    single {
        provideUsersApi(
            client = get(), baseUrl = get(named("baseUrl"))
        )
    }

    single {
        provideLocalRepository(userDao = get())
    }

    single {
        provideRemoteRepository(
            usersApi = get()
        )
    }

    single {
        Log.e("koin", "create new repo")
        provideRepository(localRepository = get(), remoteRepository = get())
    }
}

val databaseModule = module {
    fun provideDatabase(application: Application): UserDatabase {
        return Room.databaseBuilder(application, UserDatabase::class.java, "database")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideUserDao(database: UserDatabase): UserDao {
        return database.userDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideUserDao(database = get()) }
}

val viewModelsModule = module {
    fun provideConnectivityManager(context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    fun provideNetworkObserver(connectivityManager: ConnectivityManager): INetworkObserver {
        return NetworkObserverImpl(connectivityManager)
    }

    single {
        provideConnectivityManager(androidApplication())
    }

    single {
        provideNetworkObserver(connectivityManager = get())
    }

    viewModel {
        MainViewModel(
            repository = get(),
            networkObserver = get()
        )
    }

    viewModel {
        DetailsViewModel(
            repository = get(),
            networkObserver = get()
        )
    }
}
