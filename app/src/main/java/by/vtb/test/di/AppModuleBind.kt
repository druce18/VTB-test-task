package by.vtb.test.di

import by.vtb.test.data.local.CachedVideo
import by.vtb.test.data.local.CachedVideoImpl
import by.vtb.test.domain.VideoRepository
import by.vtb.test.data.VideoRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class AppModuleBind {

    @Binds
    abstract fun bindVideoRepository(videoRepository: VideoRepositoryImpl): VideoRepository

    @Binds
    abstract fun bindCachedVideo(cachedVideo: CachedVideoImpl): CachedVideo
}
