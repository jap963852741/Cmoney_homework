package com.jap.cloudinteractiveFrank.util


import android.graphics.Bitmap
import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.net.URL

@RunWith(AndroidJUnit4::class)
class URLtoBitmapUtilTest {
    @Test
    fun get(){
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("com.jap.cloudinteractiveFrank", appContext.packageName)
        URLtoBitmapUtil.instance.get(URL("https://via.placeholder.com/150/cc00ff")
            ,object :URLtoBitmapUtil.URLtoBitmapTaskFinish {
                override fun onFinish(data: Bitmap?) {
                    Log.e("onFinish", data.toString())
                }
        })

        try {
            Thread.sleep(4000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.e("finish","finish")
    }
}