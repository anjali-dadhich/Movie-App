package com.example.movieapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movieapp.utils.SessionManager

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val sessionManager = SessionManager(application.applicationContext)


    fun register(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Registration successful
                    onSuccess.invoke() // Navigate to MovieActivity
                } else {
                    // Registration failed
                    val exception = task.exception
                    onError.invoke(exception?.message ?: "Registration failed")
                }
            }
    }

    fun login(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Login successful
                    sessionManager.authToken = firebaseAuth.currentUser?.uid
                    onSuccess.invoke() // Navigate to MovieActivity
                } else {
                    // Login failed
                    val exception = task.exception
                    onError.invoke(exception?.message ?: "Login failed")
                }
            }
    }

    fun logout() {
        firebaseAuth.signOut()
        sessionManager.clearSession()
        // You can perform any cleanup or navigate to a login screen after logout
    }
}

