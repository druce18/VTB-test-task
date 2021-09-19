package by.vtb.test.network

import by.vtb.test.network.model.ResultsVideoLinksPayload
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface VideoService {

    @GET("/test/item")
    suspend fun getResultsVideoLinks(): ResultsVideoLinksPayload

    @Streaming
    @GET
    suspend fun downloadVideo(@Url videoUrl: String): Response<ResponseBody>
}
