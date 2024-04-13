package com.example.myapplication.screens.badgeforuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.models.ActualRecordRegistration
import com.example.myapplication.models.FinalObjetiveForUser
import com.example.myapplication.screens.homescreen.HomeScreenViewModel
import com.example.myapplication.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class BadgeForUserViewModel {
    private val currentUser = FirebaseAuth.getInstance().currentUser?.uid
    val db = Firebase.firestore

    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    //Variables para leer las reservas
    private val _historyRecordRegistered = MutableLiveData<List<ActualRecordRegistration>>()
    val historyRecordRegistered: LiveData<List<ActualRecordRegistration>> = _historyRecordRegistered

    private val _objetiveUserGet = MutableLiveData<FinalObjetiveForUser>()
    val objetiveUserGet: LiveData<FinalObjetiveForUser> = _objetiveUserGet

    fun getHistoryRecords(){
        var actualRecordRegistrationFinal: ActualRecordRegistration
        val historyRecordAux: MutableList<ActualRecordRegistration> = mutableListOf()
        val gson = Gson()

        val doc = db.collection(Constants.REGISTER_RECORD).get().addOnCompleteListener(){ result ->
            for(historyRecordInstance in result.result){
                if (historyRecordInstance.exists()){
                    //Seleccionamos solamente las reservas hechas por nosotros

                    if (historyRecordInstance.data["id_user"] == currentUser){

                        val hRRMatch = historyRecordInstance.data as Map<*,*>
                        val hRRMatchJson = gson.toJson(hRRMatch)

                        actualRecordRegistrationFinal = gson.fromJson(hRRMatchJson, ActualRecordRegistration::class.java)
                        Log.d("Registro", actualRecordRegistrationFinal.toString() )

                        historyRecordAux.add(actualRecordRegistrationFinal)
                    }
                }
            }
            _historyRecordRegistered.value = historyRecordAux

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


}