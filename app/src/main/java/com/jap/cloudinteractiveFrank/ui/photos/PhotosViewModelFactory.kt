package com.jap.cloudinteractiveFrank.ui.photos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jap.cloudinteractiveFrank.Repository.PhotosRepository
import com.jap.cloudinteractiveFrank.Repository.network.PhotosDataSource

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class PhotosViewModelFactory() : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhotosViewModel::class.java)) {
            return PhotosViewModel(
                photosRepository = PhotosRepository(
                    photosDataSource = PhotosDataSource()
            )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}