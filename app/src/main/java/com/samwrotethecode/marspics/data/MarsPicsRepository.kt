package com.samwrotethecode.marspics.data

import com.samwrotethecode.marspics.domain.MarsPics
import com.samwrotethecode.marspics.network.MarsApiService


interface MarsPicsRepository {
    suspend fun getMarsPics(): List<MarsPics>
}

class NetworkMarsPicsRepository(private val marsApiService: MarsApiService) : MarsPicsRepository {
    override suspend fun getMarsPics() = marsApiService.getPhotos()
}