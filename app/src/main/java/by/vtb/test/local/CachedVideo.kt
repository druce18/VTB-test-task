package by.vtb.test.local

import java.io.InputStream

interface CachedVideo {

    suspend fun saveVideoInCache(filename: String, input: InputStream): String

    suspend fun checkVideoInCache(filename: String): Boolean

    suspend fun getVideoFromCache(filename: String): String
}
