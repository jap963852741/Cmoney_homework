package com.jap.cloudinteractiveFrank.ui.photos

import com.jap.cloudinteractiveFrank.Repository.bean.PhotosResponse
import java.io.Serializable
import java.lang.Exception

/**
 * Authentication result : success (user details) or error message.
 */
data class GetPhotoResult (
        val success: PhotosResponse? = null,
        val error: Exception? = null
): Serializable
