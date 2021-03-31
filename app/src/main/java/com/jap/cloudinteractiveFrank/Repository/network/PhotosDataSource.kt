package com.jap.cloudinteractiveFrank.Repository.network

import com.google.gson.Gson
import com.jap.cloudinteractiveFrank.Repository.bean.Photos
import com.jap.cloudinteractiveFrank.Repository.bean.PhotosResponse
import com.jap.cloudinteractiveFrank.ui.photos.GetPhotoResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL


class PhotosDataSource {

    fun get(task: PhotoTaskFinish) {

        GlobalScope.launch(Dispatchers.IO) {

            val baseUrl = "https://jsonplaceholder.typicode.com/photos"
            val connection = URL(baseUrl).openConnection() as HttpURLConnection
            try {
                val data = connection.inputStream.bufferedReader().use { it.readText() }
                val photosResponse  = jsonParser(data)
                task.onFinish(
                    GetPhotoResult(
                        success = photosResponse
                    )
                )
            } catch (e: Exception){
                task.onFinish(
                    GetPhotoResult(
                        error = e
                    )
                )
            }
            finally {
                connection.disconnect()
            }

        }

    }

    private fun jsonParser(S : Any) : PhotosResponse{
        val jsonString = S.toString()
        val photosResponse = PhotosResponse(results = arrayListOf())
        try {
            val photosJSONArray = JSONArray(jsonString)
            for(i in 0 until photosJSONArray.length()){
                photosResponse.results.add(Gson().fromJson(photosJSONArray.getJSONObject(i).toString(), Photos::class.java))
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return photosResponse
    }

    interface PhotoTaskFinish{
        fun onFinish(data: GetPhotoResult)
    }
}