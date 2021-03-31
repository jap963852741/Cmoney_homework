package com.jap.cloudinteractiveFrank.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import kotlinx.coroutines.*
import java.io.*
import java.net.MalformedURLException
import java.net.URL
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.cert.X509Certificate
import javax.net.ssl.*

class URLtoBitmapUtil {
    private val TAG = "URLtoBitmapUtil"
    private lateinit var job : Job
    companion object {
        val instance = URLtoBitmapUtil()
    }
    fun get(url: URL, task: URLtoBitmapTaskFinish) {
        job = GlobalScope.launch(Dispatchers.IO) {
                try {
                    val bitmap = BitmapFactory.decodeStream(passCA(url))
                    task.onFinish(bitmap)
                }catch (e :Exception){
                    Log.e(TAG,e.toString())
                    task.onFinish(null)
                }finally {
                    this.cancel()
                }
        }
    }
    //2021/03/30 三方api憑證過期
    fun passCA(url : URL) : InputStream?{
        try {
            val connection = url.openConnection() as HttpsURLConnection
            val trustManagers = arrayOf(
                    object :X509TrustManager{
                        override fun checkClientTrusted(
                            chain: Array<out X509Certificate>?,
                            authType: String?
                        ) {
                        }
                        override fun checkServerTrusted(
                            chain: Array<out X509Certificate>?,
                            authType: String?
                        ) {
                        }
                        override fun getAcceptedIssuers(): Array<X509Certificate>? {
                            return null
                        }
                    }
            )

            val ctx = SSLContext.getInstance("TLS")
            ctx.init(null,trustManagers,null)

            connection.sslSocketFactory = ctx.socketFactory
            connection.requestMethod = "GET";
            connection.addRequestProperty("User-Agent"
                ,"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.104 Safari/537.36")
//            println(connection.responseCode)
            return connection.inputStream
        } catch ( e : MalformedURLException) {
            e.printStackTrace();
        } catch ( e :IOException) {
            e.printStackTrace();
        } catch ( e: NoSuchAlgorithmException) {
            e.printStackTrace();
        } catch ( e: KeyManagementException) {
            e.printStackTrace();
        }

        return null
    }

    interface URLtoBitmapTaskFinish{
        fun onFinish(data: Bitmap?)
    }

}