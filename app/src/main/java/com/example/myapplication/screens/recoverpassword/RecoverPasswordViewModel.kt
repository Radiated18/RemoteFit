package com.example.myapplication.screens.recoverpassword

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth

class RecoverPasswordViewModel {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    //Atributos Utilizados
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _activateSendRequestDialog = MutableLiveData<Boolean>()
    val activateSendRequestDialog: LiveData<Boolean> = _activateSendRequestDialog

    private val _buttonEnable = MutableLiveData<Boolean>()
    val buttonEnable: LiveData<Boolean> = _buttonEnable

    private val _typeMessageResponseDialog = MutableLiveData<Int>()
    val typeMessageResponseDialog: LiveData<Int> = _typeMessageResponseDialog

    fun changeValueSendEmailDialog(Boolean: Boolean){
        _activateSendRequestDialog.value = !Boolean
    }

    fun onLoginChange(email: String){
        _username.value = email
        _buttonEnable.value = isValidEmail(email)
    }

    private fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()


    fun sendPasswordResetEmail(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    _typeMessageResponseDialog.value = 1
                    _activateSendRequestDialog.value = true
                } else {
                    _typeMessageResponseDialog.value = 0
                    _activateSendRequestDialog.value = true
                }
            }
    }
}