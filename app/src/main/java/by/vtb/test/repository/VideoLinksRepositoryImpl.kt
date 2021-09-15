package by.vtb.test.repository

import by.vtb.test.network.VideoLinksService
import by.vtb.test.network.model.ResultsVideoLinksPayload
import by.vtb.test.repository.model.VideoLinks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoLinksRepositoryImpl @Inject constructor(
    private val videoLinksService: VideoLinksService
) : VideoLinksRepository {

    override suspend fun videoLinks(): VideoLinks = withContext(Dispatchers.IO) {
        val linksResult = videoLinksService.getResultsVideoLinks()
        toRepoModel(linksResult)
    }

    private fun toRepoModel(linksResult: ResultsVideoLinksPayload): VideoLinks {
        val links = linksResult.results
        return VideoLinks(
            single = links.single,
            splitV = links.splitV,
            splitH = links.splitH,
            src = links.src
        )
    }
}
