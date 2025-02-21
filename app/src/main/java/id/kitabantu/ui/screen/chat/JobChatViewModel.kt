package id.kitabantu.ui.screen.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.kitabantu.data.Result
import id.kitabantu.data.repository.JobChatRepository
import id.kitabantu.model.Job
import id.kitabantu.model.JobChat
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class JobChatViewModel @Inject constructor(
    private val jobChatRepository: JobChatRepository,
) : ViewModel() {

    val jobChats: StateFlow<JobChatUiState> = jobChatRepository.getChats()
        .map { result ->
            when (result) {
                is Result.Loading -> JobChatUiState.Loading
                is Result.Success -> {
                    JobChatUiState.Success(result.data)
                }

                is Result.Error -> JobChatUiState.Error(result.message)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = JobChatUiState.Loading
        )
}

sealed interface JobChatUiState {
    data object Loading : JobChatUiState
    data class Success(val jobChats: List<JobChat>) : JobChatUiState
    data class Error(val message: String) : JobChatUiState
}