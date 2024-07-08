package com.samwrotethecode.marspics.network

import com.samwrotethecode.marspics.domain.MarsPics
import retrofit2.http.GET

interface MarsApiService {
    @GET("photos")
    suspend fun getPhotos(): List<MarsPics>
}