package com.example.worldwise.presentation.screens.authorization.signUp

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldwise.presentation.screens.authorization.signUp.model.User
import com.example.worldwise.utils.combine
import com.example.worldwise.utils.noReply
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern


class SignUpViewModel : ViewModel() {

    private val _isValidName: MutableSharedFlow<Boolean> = noReply()
    val isValidName: Flow<Boolean> get() = _isValidName

    private val _isValidSurname: MutableSharedFlow<Boolean> = noReply()
    val isValidSurname: Flow<Boolean> get() = _isValidSurname

    private val _isValidEmail: MutableSharedFlow<Boolean> = noReply()
    val isValidEmail: Flow<Boolean> get() = _isValidEmail

    private val _isValidNumber: MutableSharedFlow<Boolean> = noReply()
    val isValidNumber: Flow<Boolean> get() = _isValidNumber

    private val _isValidNickName: MutableSharedFlow<Boolean> = noReply()
    val isValidNickName: Flow<Boolean> get() = _isValidNickName

    private val _isValidPassword: MutableSharedFlow<Boolean> = noReply()
    val isValidPassword: Flow<Boolean> get() = _isValidPassword

    private val _isCheckedPrivacy: MutableSharedFlow<Boolean> = noReply()
    val isCheckedPrivacy: Flow<Boolean> get() = _isCheckedPrivacy

    val successValidations: Flow<Boolean> = combine(
        _isValidName, _isValidSurname, _isValidEmail, _isValidNumber, _isValidNickName, _isValidPassword, _isCheckedPrivacy
    ) { isValidName, isValidLastName, isValidEmail, isValidNumber, isValidNickName, isValidPassword, isCheckedPrivacy ->
        isValidName && isValidLastName && isValidEmail && isValidNumber && isValidNickName && isValidPassword && isCheckedPrivacy
    }

    private val _isSuccessSignUp: MutableSharedFlow<Boolean> = noReply()
    val isSuccessSignUp: Flow<Boolean> get() = _isSuccessSignUp

    fun validateInputs(
        name: String,
        lastName: String,
        email: String,
        phone: String,
        nickName: String,
        password: String,
        isCheckedPrivacy: Boolean,
    ) {
        viewModelScope.launch {
            _isValidName.emit(name.isNotBlank())
            _isValidSurname.emit(lastName.isNotBlank())
            _isValidEmail.emit(checkValidationEmail(email))
            _isValidNumber.emit(checkValidationNumber(phone))
            _isValidNickName.emit(nickName.isNotBlank())
            _isValidPassword.emit(checkValidationPassword(password))
            _isCheckedPrivacy.emit(isCheckedPrivacy)
        }
    }

    fun registerUser(
        name: String,
        lastName: String,
        email: String,
        phone: String,
        nickName: String,
        password: String
    ) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user =
                        User(name = name, lastName = lastName, photoId = null, email = email, number = phone, nickName = nickName)
                    FirebaseDatabase.getInstance().getReference("Users")
                        .child(checkNotNull(FirebaseAuth.getInstance().currentUser?.uid))
                        .setValue(user).addOnCompleteListener { task ->
                            viewModelScope.launch {
                                _isSuccessSignUp.emit(task.isSuccessful)
                            }
                        }
                }
            }
    }

    private fun checkValidationEmail(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    private fun checkValidationNumber(phone: String) = phone.length == NUMBER_LENGTH
    private fun checkValidationPassword(password: String) = Pattern.compile(PASSWORD_PATTERN).matcher(password).matches()


    companion object {
        private const val NUMBER_LENGTH = 13
        const val PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,64}$"
    }
}