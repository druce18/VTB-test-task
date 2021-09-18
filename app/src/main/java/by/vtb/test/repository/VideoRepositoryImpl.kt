package by.vtb.test.repository

import by.vtb.test.di.IoDispatcher
import by.vtb.test.extention.nameFromUrl
import by.vtb.test.local.CachedVideo
import by.vtb.test.network.VideoService
import by.vtb.test.repository.model.VideoLinks
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoRepositoryImpl @Inject constructor(
    private val videoService: VideoService,
    private val cachedVideos: CachedVideo,
    private val mapper: Mapper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : VideoRepository {

    override suspend fun videoLinks(): VideoLinks = withContext(ioDispatcher) {
        val linksResult = videoService.getResultsVideoLinks()
        mapper.videoLinksPayloadToRepoModel(linksResult)
    }

    /**
     * @return absolutePath to file (video)
     */
    override suspend fun loadVideo(videoUrl: String): String = withContext(ioDispatcher) {
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
