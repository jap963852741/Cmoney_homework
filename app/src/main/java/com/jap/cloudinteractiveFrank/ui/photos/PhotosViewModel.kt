package com.jap.cloudinteractiveFrank.ui.photos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jap.cloudinteractiveFrank.Repository.PhotosRepository
import com.jap.cloudinteractiveFrank.Repository.network.PhotosDataSource

class PhotosViewModel(private val photosRepository : PhotosRepository) : ViewModel(){

    private val _photosResult = MutableLiveData<GetPhotoResult>()

    val photosResult: LiveData<GetPhotoResult> = _photosResult


    fun Get_api(){
        photosRepository.photos()
            .get(object :PhotosDataSource.PhotoTaskFinish{
                override fun onFinish(data: GetPhotoResult) {
                    _photosResult.postValue(data)
                }

            })
    }






}