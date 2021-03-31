package com.jap.cloudinteractiveFrank.Repository.bean

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Photos (
    val id: String,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)