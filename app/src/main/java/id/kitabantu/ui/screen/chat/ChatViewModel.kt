package id.kitabantu.ui.screen.chat

import androidx.lifecycle.SavedStateHandle
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
class ChatViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val jobChatRepository: JobChatRepository,
) : ViewModel() {

    private val chatId: Long? = savedStateHandle["id"]

    val chat: StateFlow<ChatUiState> = jobChatRepository.getChats()
        .map { result ->
            if (chatId != null) {
                when (result) {
                    is Result.Loading -> ChatUiState.Loading
                    is Result.Success -> {
                        ChatUiState.Success(result.data.first { it.job.id == chatId })
                    }

                    is Result.Error -> ChatUiState.Error(result.message)
                }
            } else {
                ChatUiState.Error("No chat selected")
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = ChatUiState.Loading
        )

    fun sendMessage(job: Job, message: String) {
        jobChatRepository.addMessage(job, message)
    }
}

sealed interface ChatUiState {
    data object Loading : ChatUiState
    data class Success(val chat: JobChat) : ChatUiState
    data class Error(val message: String) : ChatUiState
}