package com.jap.cloudinteractiveFrank.Repository
import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jap.cloudinteractiveFrank.Repository.network.PhotosDataSource
import com.jap.cloudinteractiveFrank.ui.photos.GetPhotoResult

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class PhotosDataSourceTest {
    @Test
    fun get() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.jap.cloudinteractiveFrank", appContext.packageName)

        val photosDataSourceTest  = PhotosDataSource().get(object :
            PhotosDataSource.PhotoTaskFinish{
            override fun onFinish(data: GetPhotoResult) {
                TODO("Not yet implemented")
            }
        }
        )

        //等callback
        try {
            Thread.sleep(4000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.e("finish","finish")

    }
}