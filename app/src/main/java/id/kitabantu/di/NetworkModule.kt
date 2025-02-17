package id.kitabantu.di

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.kitabantu.data.remote.RemoteJobDataSource
import id.kitabantu.data.remote.converter.CurrencyTypeAdapter
import id.kitabantu.data.remote.converter.DateTimeTypeAdapter
import id.kitabantu.data.remote.converter.JobTypeTypeAdapter
import id.kitabantu.data.remote.retrofit.JobRetrofit
import id.kitabantu.data.remote.retrofit.baseUrl
import id.kitabantu.model.JobType
import ir.logicbase.mockfit.MockFitConfig.REQUEST_TO_JSON
import ir.logicbase.mockfit.MockFitInterceptor
import kotlinx.datetime.LocalDateTime
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Currency
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val mockFitInterceptor = MockFitInterceptor(
            bodyFactory = { input -> context.resources.assets.open(input) },
            logger = { tag, message -> Log.d(tag, message) }, // Pass logger to log events in logcat
            baseUrl = baseUrl,
            requestPathToMockPathRule = REQUEST_TO_JSON,
            mockFilesPath = "mock"
        )

        return OkHttpClient.Builder()
            .addInterceptor(mockFitInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create(
            GsonBuilder()
                .registerTypeAdapter(JobType::class.java, JobTypeTypeAdapter())
                .registerTypeAdapter(LocalDateTime::class.java, DateTimeTypeAdapter())
                .registerTypeAdapter(Currency::class.java, CurrencyTypeAdapter())
                .create()
        )

    @Singleton
    @Provides
    fun provideRetrofit(
        client: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideRemoteWeatherDataSource(
        retrofit: Retrofit
    ): RemoteJobDataSource = JobRetrofit(retrofit)
}