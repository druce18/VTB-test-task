package by.vtb.test.repository

import by.vtb.test.network.VideoLinksService
import by.vtb.test.repository.model.VideoLinks
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoLinksRepositoryImpl @Inject constructor(
    private val videoLinksService: VideoLinksService
) : VideoLinksRepository {

    override suspend fun videoLinks(): VideoLinks {
        val linksResult = videoLinksService.getResultsVideoLinks().results
        return VideoLinks(
            single = linksResult.single,
            splitV = linksResult.splitV,
            splitH = linksResult.splitH,
            src = linksResult.src
        )
    }
}
