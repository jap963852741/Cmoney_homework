package com.jap.cloudinteractiveFrank.Repository.network

import com.jap.cloudinteractiveFrank.util.URLtoBitmapUtil

class DetailDataSource {

    fun getBitmapUtil() : URLtoBitmapUtil{
        return URLtoBitmapUtil.instance
    }
}