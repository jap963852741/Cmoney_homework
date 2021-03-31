package com.jap.cloudinteractiveFrank.util

import android.graphics.Bitmap
import android.util.LruCache
import com.android.volley.toolbox.ImageLoader

class LruCacheUtil private constructor(sizeInKB: Int = defaultLruCacheSize): LruCache<String, Bitmap>(sizeInKB), ImageLoader.ImageCache {

    override fun sizeOf(key: String, value: Bitmap): Int = value.rowBytes * value.height / 1024

    override fun getBitmap(id: String): Bitmap? {
        return get(id)
    }

    override fun putBitmap(id: String, bitmap: Bitmap?) {
        put(id, bitmap)
    }

    companion object {
        val defaultLruCacheSize: Int
            get() {
                val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
                val cacheSize = maxMemory / 8
                return cacheSize
            }

        val instance: LruCacheUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            LruCacheUtil(defaultLruCacheSize) }
    }
}