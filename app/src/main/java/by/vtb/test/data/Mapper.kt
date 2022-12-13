package by.vtb.test.data

import by.vtb.test.data.network.model.ResultsVideoLinksPayload
import by.vtb.test.domain.model.VideoLinks
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
