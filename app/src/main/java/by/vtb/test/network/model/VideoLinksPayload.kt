package by.vtb.test.network.model

import com.google.gson.annotations.SerializedName

data class VideoLinksPayload(

    @SerializedName("single")
    val single: String,

    @SerializedName("split_v")
    val splitV: String,

    @SerializedName("split_h")
    val splitH: String,

    @SerializedName("src")
    val src: String
)
