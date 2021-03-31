package com.jap.cloudinteractiveFrank.Repository.bean

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotosResponse (
    var results : ArrayList<Photos>
)