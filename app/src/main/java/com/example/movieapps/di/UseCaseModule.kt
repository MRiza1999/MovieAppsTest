package com.example.movieapps.di

import com.example.movieapps.core.domain.main.usecase.MainInteractor
import com.example.movieapps.core.domain.main.usecase.MainUseCase
import org.koin.dsl.module

val usecaseModule = module {
    factory<MainUseCase> { MainInteractor(get()) }
}