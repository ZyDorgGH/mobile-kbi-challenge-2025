package id.kitabantu.ui.screen.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.kitabantu.data.Result
import id.kitabantu.data.repository.JobBookmarkRepository
import id.kitabantu.model.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val jobBookmarkRepository: JobBookmarkRepository,
): ViewModel() {

    val bookmarkUiState: Flow<BookmarkUiState> =
        jobBookmarkRepository.getBookmarks()
            .map { result ->
                when (result) {
                    is Result.Loading -> BookmarkUiState.Loading
                    is Result.Success -> {
                        val jobs = result.data
                        BookmarkUiState.Success(jobs)
                    }
                    is Result.Error -> BookmarkUiState.Error(result.message)
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = BookmarkUiState.Loading
            )
}

sealed interface BookmarkUiState {
    data object Loading : BookmarkUiState
    data class Success(val jobs: List<Job>) : BookmarkUiState
    data class Error(val message: String) : BookmarkUiState
}