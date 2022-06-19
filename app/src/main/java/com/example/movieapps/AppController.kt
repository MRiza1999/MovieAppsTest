package com.example.movieapps

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import com.example.movieapps.di.networkModule
import com.example.movieapps.di.repositoryModule
import com.example.movieapps.di.usecaseModule
import com.example.movieapps.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@ExperimentalPagingApi
open class AppController: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@AppController)
            koin.loadModules(listOf(
                networkModule,
                repositoryModule,
                usecaseModule,
                viewModelModule
            ))
        }
    }
    fun baseURL() : String = "https://api.themoviedb.org/"
}