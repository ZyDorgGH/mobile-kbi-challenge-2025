package id.kitabantu.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class AuthService {

    private val auth = Firebase.auth

    val user = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { authentication ->
            trySend(authentication.currentUser)
        }

        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }

    fun createUserEmailAndPassword(email: String, password: String) {
        try {
            auth.createUserWithEmailAndPassword(email, password)
        } catch (e: Exception) {
            Log.d("Auth", "createUserEmailAndPassword: ${e.message}")
        }
    }

    fun authenticateUserEmailAndPassword(email: String, password: String) {
        try {
            auth.signInWithEmailAndPassword(email, password)
        } catch (e: Exception) {
            Log.d("Auth", "signInWithEmailAndPassword: ${e.message}")
        }
    }

}