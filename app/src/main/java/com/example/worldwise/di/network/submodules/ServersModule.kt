package com.example.worldwise.di.network.submodules

import com.example.worldwise.di.network.qualifiers.ServerQualifier
import com.google.gson.GsonBuilder
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

const val MAP_URL = "https://maps.googleapis.com"
internal val serversModule = module {
    single(ServerQualifier.mapApi) {
        provideRetrofit(
            baseUrl = MAP_URL,
        )
    }
}

private fun provideRetrofit(
    baseUrl: String
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(createGsonFactory())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
}

private fun createGsonFactory(): GsonConverterFactory {
    val gson = GsonBuilder()
        .setLenient()
        .create()
    return GsonConverterFactory.create(gson)
}

