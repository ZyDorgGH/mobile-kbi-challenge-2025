package id.kitabantu.data.remote

import id.kitabantu.model.Job

interface RemoteJobDataSource {

    suspend fun getJobs(): List<Job>
}