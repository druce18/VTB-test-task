package by.vtb.test.repository

import by.vtb.test.extention.nameFromUrl
import by.vtb.test.local.CachedVideo
import by.vtb.test.network.VideoService
import by.vtb.test.repository.model.VideoLinks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoRepositoryImpl @Inject constructor(
    private val videoService: VideoService,
    private val cachedVideos: CachedVideo,
    private val mapper: Mapper
) : VideoRepository {

    override suspend fun videoLinks(): VideoLinks = withContext(Dispatchers.IO) {
        val linksResult = videoService.getResultsVideoLinks()
        mapper.videoLinksPayloadToRepoModel(linksResult)
    }

    override suspend fun loadVideo(videoUrl: String): String = withContext(Dispatchers.IO) {
        val filename = videoUrl.nameFromUrl()
        val checkVideoInCache = cachedVideos.checkVideoInCache(filename)
        if (checkVideoInCache) {
            cachedVideos.getVideoFromCache(filename)
        } else {
            val video = videoService.downloadVideo(videoUrl)
            val body = video.body() ?: throw NullPointerException()
            val input: InputStream = body.byteStream()
            cachedVideos.saveVideoInCache(filename, input)
        }
    }
}
