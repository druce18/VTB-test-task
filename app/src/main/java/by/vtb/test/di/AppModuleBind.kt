package by.vtb.test.di

import by.vtb.test.repository.VideoLinksRepository
import by.vtb.test.repository.VideoLinksRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class AppModuleBind {

    @Binds
    abstract fun bindRepository(videoLinksRepository: VideoLinksRepositoryImpl): VideoLinksRepository
}
