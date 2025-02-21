package id.kitabantu.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.kitabantu.data.remote.RemoteJobDataSource
import id.kitabantu.data.repository.DefaultJobBookmarkRepository
import id.kitabantu.data.repository.DefaultJobChatRepository
import id.kitabantu.data.repository.DefaultJobRepository
import id.kitabantu.data.repository.JobBookmarkRepository
import id.kitabantu.data.repository.JobChatRepository
import id.kitabantu.data.repository.JobRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideJobRepository(remoteJobDataSource: RemoteJobDataSource): JobRepository {
        return DefaultJobRepository(remoteJobDataSource)
    }

    @Singleton
    @Provides
    fun provideJobBookmarkRepository(): JobBookmarkRepository {
        return DefaultJobBookmarkRepository()
    }

    @Singleton
    @Provides
    fun provideJobChatRepository(): JobChatRepository {
        return DefaultJobChatRepository()
    }
}