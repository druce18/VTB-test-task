package by.vtb.test.di

import by.vtb.test.local.CachedVideo
import by.vtb.test.local.CachedVideoImpl
import by.vtb.test.repository.VideoRepository
import by.vtb.test.repository.VideoRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class AppModuleBind {

    @Binds
    abstract fun bindVideoRepository(videoRepository: VideoRepositoryImpl): VideoRepository

    @Binds
    abstract fun bindCachedVideo(cachedVideo: CachedVideoImpl): CachedVideo
}
