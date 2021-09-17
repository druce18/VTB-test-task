package by.vtb.test.repository

import by.vtb.test.repository.model.VideoLinks

interface VideoLinksRepository {

    suspend fun videoLinks(): VideoLinks
}
