package id.kitabantu.data.repository

import id.kitabantu.data.Result
import id.kitabantu.data.asResult
import id.kitabantu.model.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class DefaultJobBookmarkRepository : JobBookmarkRepository {

    private val bookmarks: MutableStateFlow<List<Job>> = MutableStateFlow(emptyList())

    override fun getBookmarks(): Flow<Result<List<Job>>> =
        bookmarks.asResult()

    override fun addBookmark(job: Job) {
        bookmarks.update { jobs ->
            val mutableJobs = jobs.toMutableList()
            mutableJobs.add(job)
            mutableJobs.toList()
        }
    }

    override fun removeBookmark(job: Job) {
        bookmarks.update { jobs ->
            val mutableJobs = jobs.toMutableList()
            mutableJobs.remove(job)
            mutableJobs.toList()
        }
    }

    override fun isBookmarked(job: Job): Boolean {
        return (bookmarks.value.contains(job))
    }
}