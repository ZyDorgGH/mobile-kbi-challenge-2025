package id.kitabantu.ui.screen.profile

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.kitabantu.auth.AuthService
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authService: AuthService,
) : ViewModel() {

    val user = authService.user

    fun logout() {
        authService.unauthenticateUser()
    }
}