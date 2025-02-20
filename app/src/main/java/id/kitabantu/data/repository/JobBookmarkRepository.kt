package id.kitabantu.data.repository

import id.kitabantu.model.Job
import kotlinx.coroutines.flow.Flow
import id.kitabantu.data.Result

interface JobBookmarkRepository {

    fun getBookmarks(): Flow<Result<List<Job>>>

    fun addBookmark(job: Job)

    fun removeBookmark(job: Job)
}