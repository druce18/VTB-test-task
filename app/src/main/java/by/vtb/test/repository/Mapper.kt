package by.vtb.test.repository

import by.vtb.test.network.model.ResultsVideoLinksPayload
import by.vtb.test.repository.model.VideoLinks
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Mapper @Inject constructor() {

    fun videoLinksPayloadToRepoModel(linksResult: ResultsVideoLinksPayload): VideoLinks {
        val links = linksResult.results
        return VideoLinks(
            single = links.single,
            splitV = links.splitV,
            splitH = links.splitH,
            src = links.src
        )
    }
}
