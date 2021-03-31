package com.jap.cloudinteractiveFrank.ui.detail

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jap.cloudinteractiveFrank.Repository.DetailRepository
import com.jap.cloudinteractiveFrank.util.URLtoBitmapUtil
import java.net.URL

class DetailViewModel(private val detailRepository : DetailRepository,val context: Context) : ViewModel(){

    private val _detailPhoto = MutableLiveData<Bitmap>()
    val detailPhoto: LiveData<Bitmap> = _detailPhoto

    fun getUrlBitmap(url :String){
        detailRepository.detail().getBitmapUtil().get(
                URL(url),
                object : URLtoBitmapUtil.URLtoBitmapTaskFinish {
                    override fun onFinish(data: Bitmap?) {
                        _detailPhoto.postValue(data)
                    }
                }
            )
    }

}