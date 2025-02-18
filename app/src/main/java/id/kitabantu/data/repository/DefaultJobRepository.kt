package id.kitabantu.data.repository

import id.kitabantu.data.Result
import id.kitabantu.data.asResult
import id.kitabantu.data.remote.RemoteJobDataSource
import id.kitabantu.model.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultJobRepository @Inject constructor(
    private val remoteJobDataSource: RemoteJobDataSource,
) : JobRepository {

    override fun getJobs(): Flow<Result<List<Job>>> = flow {
        emit(remoteJobDataSource.getJobs())
    }.asResult()
}