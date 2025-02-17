package id.kitabantu.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.kitabantu.data.Result
import id.kitabantu.data.repository.JobRepository
import id.kitabantu.model.Job
import id.kitabantu.model.JobType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class JobsViewModel @Inject constructor(
    jobRepository: JobRepository,
) : ViewModel() {

    private val _jobsQuery: MutableStateFlow<String> = MutableStateFlow("")
    private val _jobsCategories: MutableStateFlow<Set<JobType>> = MutableStateFlow(emptySet())

    val jobsUiState: StateFlow<JobsUiState> =
        combine(_jobsQuery, _jobsCategories, jobRepository.getJobs()) { query, categories, result ->
            when (result) {
                is Result.Loading -> JobsUiState.Loading
                is Result.Success -> {
                    val jobs = result.data
                    JobsUiState.Success(jobs.search(query, categories))
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

    fun setCategories(categories: Set<JobType>) {
        _jobsCategories.value = categories
    }

    private fun List<Job>.search(query: String, categories: Set<JobType>) = this.filter { job ->
        val searchByCategory = if (categories.isNotEmpty()) (job.type in categories) else true

        job.title.contains(query) or searchByCategory
    }
}

sealed interface JobsUiState {
    data object Loading : JobsUiState
    data class Success(val jobs: List<Job>) : JobsUiState
    data class Error(val message: String) : JobsUiState
}