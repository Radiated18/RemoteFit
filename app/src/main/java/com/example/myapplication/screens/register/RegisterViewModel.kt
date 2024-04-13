package com.example.myapplication.screens.register

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.models.UserModel
import com.google.type.Date
import kotlin.math.log

class RegisterViewModel {

    //Atributos Utilizados
    private val _names = MutableLiveData<String>()
    val name: LiveData<String> = _names

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _birthDate = MutableLiveData<String>()
    val birthDate: LiveData<String> = _birthDate

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _confirmPassword = MutableLiveData<String>()
    val confirmPassword: LiveData<String> = _confirmPassword

    private val _passwordVisible1 = MutableLiveData<Boolean>()
    val passwordVisible1: LiveData<Boolean> = _passwordVisible1

    private val _passwordVisible2 = MutableLiveData<Boolean>()
    val passwordVisible2: LiveData<Boolean> = _passwordVisible2

    private val _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String> = _phoneNumber

    private val _sexGender = MutableLiveData<String>()
    val sexGender: LiveData<String> = _sexGender

    private val _registerEnable = MutableLiveData<Boolean>()
    val registerEnable: LiveData<Boolean> = _registerEnable

    private val _validNumber = MutableLiveData<Boolean>()
    val validNumber: LiveData<Boolean> = _validNumber

    private val _correctPasswords = MutableLiveData<Boolean>()
    val correctPasswords: LiveData<Boolean> = _correctPasswords


    private fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidPassword(password: String): Boolean = password.length > 6

    private fun isEqualPassword(p1: String, p2: String): Boolean = p1 == p2

    private fun isValidNumber(number: String): Boolean = number.length > 6




    fun onTextFieldChange(nameUser: String, email: String, birthDate: String, password: String,
                          confirmPassword: String, phoneNumberUser: String, sexGender: String) {

        _names.value = nameUser
        _email.value = email
        _birthDate.value = birthDate
        _password.value = password
        _confirmPassword.value = confirmPassword
        _phoneNumber.value = phoneNumberUser
        _sexGender.value = sexGender

        _registerEnable.value = !_names.value.isNullOrBlank() && !_email.value.isNullOrBlank() &&
                !_sexGender.value.isNullOrBlank() && !_sexGender.value.isNullOrBlank() &&
                isValidEmail(email) && isValidPassword(password) &&
                isValidPassword(confirmPassword) && isEqualPassword(password, confirmPassword)

        //Registros de modificadores para los errores de los TextField
        numberChangesValue()


    }

    fun numberChangesValue(){
        _validNumber.value = _phoneNumber.value?.let { !isValidNumber(it) }
    }

    fun passwordChangesValue(){
        if (_password.value.isNullOrBlank() || _confirmPassword.value.isNullOrBlank()){
            _correctPasswords.value = false
        }else _correctPasswords.value = _password.value != _confirmPassword.value

    }

    fun changeSexValue(sexGender: String){
        _sexGender.value = sexGender
    }

    fun changeDateValue(dateBirth: String){
        _birthDate.value = dateBirth
    }

    fun changePasswordVisible1(passwordVisual: Boolean){
        val newPasswordVisible = !passwordVisual
        _passwordVisible1.value = newPasswordVisible
    }
    fun changePasswordVisible2(passwordVisual: Boolean){
        val newPasswordVisible = !passwordVisual
        _passwordVisible2.value = newPasswordVisible
    }

    fun createTempMap(): MutableMap<String, String?> {
        return mutableMapOf(
            "names" to _names.value,
            "email" to _email.value,
            "phoneNumber" to _phoneNumber.value,
            "sexGender" to _sexGender.value,
            "password" to _password.value,
            "birthDate" to _birthDate.value

        )
    }

    companion object {

        var userTemp: MutableMap<String, String?> = mutableMapOf()

    }
}