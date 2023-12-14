package com.example.worldwise.di.modules

import com.example.worldwise.domain.repositories.GoogleMapRepository
import org.koin.dsl.module

val domainModule = module {
    factory {
        GoogleMapRepository(api = get())
    }
}