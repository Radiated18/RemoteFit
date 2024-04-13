package com.example.myapplication.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginScreenViewModel: ViewModel() {
    //Autenticaciones de FireBase
    private val auth: FirebaseAuth = Firebase.auth

    //Atributos Utilizados
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _passwordVisible = MutableLiveData<Boolean>()
    val passwordVisible: LiveData<Boolean> = _passwordVisible

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable: LiveData<Boolean> = _loginEnable

    //Mensaje de Autenticacion
    private val _messageAuth = MutableLiveData<String>()
    val messageAuth: LiveData<String> = _messageAuth

    private val _failedLoginDialog = MutableLiveData<Boolean>()
    val failedLoginDialog: LiveData<Boolean> = _failedLoginDialog

    fun changeValueDialog(Boolean: Boolean){
        _failedLoginDialog.value = !Boolean
    }

    //Funciones de Clase
    fun onLoginChange(email: String, password: String){
        _username.value = email
        _password.value = password
        _loginEnable.value = isValidPassword(password)
    }

    private fun isValidPassword(password: String): Boolean = password.length > 6

    fun changePasswordVisible(passwordVisual: Boolean){
        val newPasswordVisible = !passwordVisual
        _passwordVisible.value = newPasswordVisible
    }

    fun signInWithEmailAndPassword(email: String, password: String, function: ()->Unit)
            = viewModelScope.launch {
        try{
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener{ task ->
                    if(task.isSuccessful){
                        _messageAuth.value = "Login Exitoso"
                        function()
                    }else{
                        _failedLoginDialog.value = true
                    }
                }
        }catch (ex:Exception){
            Log.d("Excepcion","Except")
        }
    }

}