package com.example.worldwise.presentation.screens.authorization.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldwise.utils.noReply
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch


class LoginViewModel : ViewModel() {

    private val _isSuccessLogin: MutableSharedFlow<Boolean> = noReply()
    val isSuccessLogin: Flow<Boolean> get() = _isSuccessLogin

    fun onLoginClicked(email: String, password: String) {
        if (email.isNotBlank() && password.isNotBlank()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                viewModelScope.launch {
                    _isSuccessLogin.emit(task.isSuccessful)
                }
            }
        }
    }
}
