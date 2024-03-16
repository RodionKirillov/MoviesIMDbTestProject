package com.example.moviesimdb.di

import com.example.moviesimdb.navigation.Router
import com.example.moviesimdb.navigation.RouterImpl
import org.koin.dsl.module

val navigationModule = module {
    val router = RouterImpl()

    single<Router> { router }
    single { router.navigatorHolder }
}