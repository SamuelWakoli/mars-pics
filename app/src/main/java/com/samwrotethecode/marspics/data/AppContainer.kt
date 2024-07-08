package com.samwrotethecode.marspics.data

import com.samwrotethecode.marspics.network.MarsApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val marsPicsRepository: MarsPicsRepository
}

class DefaultAppContainer : AppContainer {
    private val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    override val marsPicsRepository: MarsPicsRepository by lazy {
        NetworkMarsPicsRepository(retrofit.create(MarsApiService::class.java))
    }
}