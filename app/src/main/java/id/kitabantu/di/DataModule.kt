package id.kitabantu.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.kitabantu.data.remote.RemoteJobDataSource
import id.kitabantu.data.repository.DefaultJobRepository
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
}