package id.kitabantu.data.remote.retrofit

import id.kitabantu.data.remote.RemoteJobDataSource
import id.kitabantu.model.Job
import ir.logicbase.mockfit.Mock
import retrofit2.Retrofit
import retrofit2.http.GET
import javax.inject.Inject

const val baseUrl = "https://example.job.com/"

private interface JobApi {

    @Mock("jobs.json")
    @GET("jobs")
    suspend fun getJobs(): List<Job>
}

class JobRetrofit @Inject constructor(
    retrofit: Retrofit,
) : RemoteJobDataSource {

    private val api = retrofit.create(JobApi::class.java)

    override suspend fun getJobs(): List<Job> =
        api.getJobs()
}