package id.kitabantu.data.repository

import id.kitabantu.data.Result
import id.kitabantu.model.Job
import kotlinx.coroutines.flow.Flow

interface JobRepository {

    fun getJobs(): Flow<Result<List<Job>>>
}