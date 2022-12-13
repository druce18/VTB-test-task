package by.vtb.test.data.local

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CachedVideoImpl @Inject constructor(private val context: Context) : CachedVideo {

    override suspend fun saveVideoInCache(filename: String, input: InputStream): String {
        val file = File(context.cacheDir, filename)
        val fos = FileOutputStream(file.absolutePath)
        fos.use { output ->
            val buffer = ByteArray(4096)
            var read: Int
            while (input.read(buffer).also { read = it } != -1) {
                output.write(buffer, 0, read)
            }
            output.flush()
        }
        return file.absolutePath
    }

    override suspend fun checkVideoInCache(filename: String): Boolean {
        val file = File(context.cacheDir, filename)
        return file.exists()
    }

    override suspend fun getVideoFromCache(filename: String): String {
        val file = File(context.cacheDir, filename)
        return file.absolutePath
    }
}
