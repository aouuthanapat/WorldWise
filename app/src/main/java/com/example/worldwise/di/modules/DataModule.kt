package com.example.worldwise.di.modules

import com.example.worldwise.data.map.GoogleMapApi
import com.example.worldwise.di.network.provideApi
import com.example.worldwise.di.network.qualifiers.ServerQualifier
import org.koin.dsl.module

val dataModule = module {

    single<GoogleMapApi> {
        provideApi(retrofit = get(ServerQualifier.mapApi))
    }
}