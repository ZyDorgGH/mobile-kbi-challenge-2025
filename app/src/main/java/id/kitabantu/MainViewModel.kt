package id.kitabantu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.kitabantu.auth.AuthService
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authService: AuthService,
) : ViewModel() {

    val user = authService.user
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = null
        )

    fun createUserEmailAndPassword(email: String, password: String) {
        authService.createUserEmailAndPassword(email, password)
    }

    fun authenticateUserEmailAndPassword(email: String, password: String) {
        authService.authenticateUserEmailAndPassword(email, password)
    }
}