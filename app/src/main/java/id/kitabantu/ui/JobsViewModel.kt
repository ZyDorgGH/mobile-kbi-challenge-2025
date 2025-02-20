package id.kitabantu.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.kitabantu.data.Result
import id.kitabantu.data.repository.JobBookmarkRepository
import id.kitabantu.data.repository.JobRepository
import id.kitabantu.model.Job
import id.kitabantu.model.JobType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobsViewModel @Inject constructor(
    jobRepository: JobRepository,
    private val jobBookmarkRepository: JobBookmarkRepository
) : ViewModel() {

    private val _jobsQuery: MutableStateFlow<String> = MutableStateFlow("")
    private val _sort: MutableStateFlow<String> = MutableStateFlow("")
    private val _jobsCategories: MutableStateFlow<Set<JobType>> = MutableStateFlow(emptySet())

    val jobsUiState: StateFlow<JobsUiState> =
        combine(_jobsQuery, _jobsCategories, _sort, jobRepository.getJobs()) { query, categories, sort,  result ->

            when (result) {
                is Result.Loading -> JobsUiState.Loading
                is Result.Success -> {
                    val jobs = result.data
                    Log.e("query" , query)
                    Log.e("categories" , categories.toString())
                    JobsUiState.Success(jobs.search(query, categories, sort))
                }

                is Result.Error -> JobsUiState.Error(result.message)
            }
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = JobsUiState.Loading
            )

    fun setSearchQuery(query: String) {
        _jobsQuery.value = query

    }
    fun setSort(sort: String) {
        _sort.value = sort

    }

    fun setCategories(categories: Set<JobType>) {
        _jobsCategories.value = categories
    }

    fun addBookmark(job: Job){
        viewModelScope.launch {
            jobBookmarkRepository.addBookmark(job)
        }
    }

    fun removeBookmark(job: Job) {
        viewModelScope.launch {
            jobBookmarkRepository.removeBookmark(job)
        }
    }

    fun isBookmark(job: Job): Boolean{
        return jobBookmarkRepository.isBookmarked(job)
    }

    private fun List<Job>.search(
        query: String,
        categories: Set<JobType>,
        sort: String

    ): List<Job> {
        return this.filter { job ->
            val searchByCategory = if (categories.isNotEmpty()) (job.type in categories) else true

            job.title.contains(query, ignoreCase = true) and searchByCategory

        }.sortedWith(
            compareByDescending {
                when (sort) {
                    "Salary" ->  it.salary
                    else ->  it.published
                }
            }
        )
    }

}

sealed interface JobsUiState {
    data object Loading : JobsUiState
    data class Success(val jobs: List<Job>) : JobsUiState
    data class Error(val message: String) : JobsUiState
}