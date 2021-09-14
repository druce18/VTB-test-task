package by.vtb.test.network

import by.vtb.test.network.model.ResultsVideoLinksPayload
import retrofit2.http.GET

interface VideoLinksService {

    @GET("/test/item")
    suspend fun getResultsVideoLinks(): ResultsVideoLinksPayload
}
