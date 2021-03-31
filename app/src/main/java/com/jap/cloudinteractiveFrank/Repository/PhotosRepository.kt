package com.jap.cloudinteractiveFrank.Repository

import com.jap.cloudinteractiveFrank.Repository.network.PhotosDataSource

class PhotosRepository(private val photosDataSource: PhotosDataSource) {

    fun photos(): PhotosDataSource {
        return photosDataSource
    }

}