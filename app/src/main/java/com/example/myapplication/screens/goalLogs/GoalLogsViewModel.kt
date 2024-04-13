package com.example.myapplication.screens.goalLogs

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.models.ActualRecordRegistration
import com.example.myapplication.utils.Constants.REGISTER_RECORD
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class GoalLogsViewModel {
    //Variable para la conexion de Base de Datos
    val db = Firebase.firestore
    private val currentUser = FirebaseAuth.getInstance().currentUser?.uid
    //Variables para leer las reservas
    private val _historyRecordRegistered = MutableLiveData<List<ActualRecordRegistration>>()
    val historyRecordRegistered: LiveData<List<ActualRecordRegistration>> = _historyRecordRegistered

    fun getHistoryRecords(){
        var actualRecordRegistrationFinal: ActualRecordRegistration
        val historyRecordAux: MutableList<ActualRecordRegistration> = mutableListOf()
        val gson = Gson()

        val doc = db.collection(REGISTER_RECORD).get().addOnCompleteListener(){ result ->
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


}