package by.vtb.test.domain

import by.vtb.test.domain.model.VideoLinks

interface VideoRepository {

    suspend fun videoLinks(): VideoLinks

    suspend fun loadVideo(videoUrl: String): String
}
