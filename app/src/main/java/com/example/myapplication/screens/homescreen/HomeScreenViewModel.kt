package com.example.myapplication.screens.homescreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.models.FinalObjetiveForUser
import com.example.myapplication.models.UserModel
import com.example.myapplication.screens.register.RegisterViewModel
import com.example.myapplication.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import java.time.LocalDate

class HomeScreenViewModel {
    val db = Firebase.firestore
    private val currentUser = FirebaseAuth.getInstance().currentUser?.uid

    private val _UserInLogin = MutableLiveData<UserModel>()
    val UserInLogin: LiveData<UserModel> = _UserInLogin

    private val _finalObjetiveReachedDialog = MutableLiveData<Boolean>()
    val finalObjetiveReachedDialog: LiveData<Boolean> = _finalObjetiveReachedDialog

    private val _enableUpdateDialog = MutableLiveData<Boolean>()
    val enableUpdateDialog: LiveData<Boolean> = _enableUpdateDialog

    private val _objetiveUserGet = MutableLiveData<FinalObjetiveForUser>()
    val objetiveUserGet: LiveData<FinalObjetiveForUser> = _objetiveUserGet

    fun getUserLogin(){
        var userFinal: UserModel? = null
        val gson = Gson()

        val doc = db.collection("users").get().addOnCompleteListener(){ result ->
            for(userInstance in result.result){
                if (userInstance.exists()){

                    if (userInstance.data["id_user"] == currentUser){

                        val userMatch = userInstance.data as Map<*,*>
                        val userMatchJson = gson.toJson(userMatch)

                        Log.d("Usuario", userInstance.toString())

                        userFinal = gson.fromJson(userMatchJson, UserModel::class.java)
                    }
                }
            }
            Log.d("Usuario Final", userFinal.toString())
            _UserInLogin.value = userFinal
            currentUserLogged = userFinal
        }

    }

    fun getFinalObjetive(){
        var finalObjetiveForUserAux: FinalObjetiveForUser
        val gson = Gson()

        val doc = db.collection(Constants.OBJETIVE_RECORD_FINAL).get().addOnCompleteListener(){ result ->
            for(finalObjetiveInstance in result.result){
                Log.d("Entra", "llama")

                if (finalObjetiveInstance.exists()){
                    //Seleccionamos solamente las reservas hechas por nosotros

                    if (finalObjetiveInstance.data["id_user"] == currentUser){

                        val hRRMatch = finalObjetiveInstance.data as Map<*,*>
                        val hRRMatchJson = gson.toJson(hRRMatch)

                        finalObjetiveForUserAux = gson.fromJson(hRRMatchJson, FinalObjetiveForUser::class.java)
                        Log.d("Entra", finalObjetiveForUserAux.toString())

                        _objetiveUserGet.value = finalObjetiveForUserAux

                    }
                }
            }
        }
    }

    fun isDateTodayOrFuture(dateString: String){
        val currentDate = LocalDate.now()
        val dateToCompare = LocalDate.parse(dateString)

        _finalObjetiveReachedDialog.value = !dateToCompare.isAfter( currentDate)
    }

    fun changeValueDialog(Boolean: Boolean){
        _enableUpdateDialog.value = !Boolean
    }





    companion object{
        var currentUserLogged: UserModel? = null
    }
}