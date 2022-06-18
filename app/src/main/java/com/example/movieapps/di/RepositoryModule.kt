package com.example.movieapps.di

import com.example.movieapps.core.data.main.source.MainRepository
import com.example.movieapps.core.data.main.source.remote.MainRemoteDataSource
import com.example.movieapps.core.domain.main.repository.IMainRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { MainRemoteDataSource(mainService = get()) }
    single<IMainRepository> { MainRepository(mainRemoteDataSource = get()) }
}