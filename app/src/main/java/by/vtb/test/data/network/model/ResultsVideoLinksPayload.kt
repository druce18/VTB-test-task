package by.vtb.test.data.network.model

import com.google.gson.annotations.SerializedName

data class ResultsVideoLinksPayload(

    @SerializedName("results")
    val results: VideoLinksPayload
)
