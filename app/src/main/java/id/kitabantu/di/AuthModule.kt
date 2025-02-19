package id.kitabantu.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.kitabantu.auth.AuthService
import id.kitabantu.data.remote.RemoteJobDataSource
import id.kitabantu.data.repository.DefaultJobRepository
import id.kitabantu.data.repository.JobRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Singleton
    @Provides
    fun provideAuthService(): AuthService {
        return AuthService()
    }
}