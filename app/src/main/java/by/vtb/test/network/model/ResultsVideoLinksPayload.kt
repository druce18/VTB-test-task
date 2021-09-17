package by.vtb.test.network.model

import com.google.gson.annotations.SerializedName

data class ResultsVideoLinksPayload(

    @SerializedName("results")
    val results: VideoLinksPayload
)
