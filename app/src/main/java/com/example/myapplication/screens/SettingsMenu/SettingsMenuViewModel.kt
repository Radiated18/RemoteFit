package com.example.myapplication.screens.SettingsMenu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth

class SettingsMenuViewModel {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _typeMessageResponseDialog = MutableLiveData<Int>()
    val typeMessageResponseDialog: LiveData<Int> = _typeMessageResponseDialog

    private val _enableLogoutDialog = MutableLiveData<Boolean>()
    val enableLogoutDialog: LiveData<Boolean> = _enableLogoutDialog

    private val _activateSendRequestDialog = MutableLiveData<Boolean>()
    val activateSendRequestDialog: LiveData<Boolean> = _activateSendRequestDialog

    fun changeValueDialog(Boolean: Boolean){
        _enableLogoutDialog.value = !Boolean
    }

    fun changeValueSendEmailDialog(Boolean: Boolean){
        _activateSendRequestDialog.value = !Boolean
    }

    fun disconectUser() {
        auth.signOut()
    }

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