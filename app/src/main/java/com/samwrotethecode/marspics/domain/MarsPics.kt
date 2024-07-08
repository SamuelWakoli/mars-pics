package com.samwrotethecode.marspics.domain

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class MarsPics(
    val id: String,
    @SerializedName("img_src")
    val imgSrc: String,
)
