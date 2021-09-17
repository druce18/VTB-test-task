package by.vtb.test.local

import java.io.InputStream

interface CachedVideo {

    fun saveVideoInCache(filename: String, input: InputStream): String

    fun checkVideoInCache(filename: String): Boolean

    fun getVideoFromCache(filename: String): String
}