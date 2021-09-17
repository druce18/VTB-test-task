package by.vtb.test.repository

import by.vtb.test.repository.model.VideoLinks

interface VideoRepository {

    suspend fun videoLinks(): VideoLinks

    suspend fun loadVideo(videoUrl: String): String
}
