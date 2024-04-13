package com.example.myapplication.screens.activepauses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.models.TrainingExercises
import com.example.myapplication.utils.Constants
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class ActivePausesViewModel {
    val db = Firebase.firestore

    //Lista de Ejercicios
    private val _activePausesList = MutableLiveData<List<TrainingExercises>>()
    val activePausesList: LiveData<List<TrainingExercises>> = _activePausesList

    private val _enableShowDialog = MutableLiveData<Boolean>()
    val enableShowDialog: LiveData<Boolean> = _enableShowDialog

    private val _activePausesShower = MutableLiveData<TrainingExercises>()
    val activePausesShower: LiveData<TrainingExercises> = _activePausesShower

    fun selectableActivePauseShower(TrainingExercise: TrainingExercises){
        _activePausesShower.value = TrainingExercise
    }

    fun changeValueDialog(Boolean: Boolean){
        _enableShowDialog.value = !Boolean
    }

    fun getActivePausesList(){
        var trainingExercisesAux: TrainingExercises
        val trainingExercisesList= mutableListOf<TrainingExercises>()
        val gson = Gson()

        val doc = db.collection(Constants.TRAINING_EXERCISES).get().addOnCompleteListener(){ result ->
            for(trainingExercisesInstance in result.result){

                if (trainingExercisesInstance.exists()){

                    val trainingExercisesMatch = trainingExercisesInstance.data as Map<*,*>
                    val trainingExercisesJson =  gson.toJson(trainingExercisesMatch)

                    trainingExercisesAux = gson.fromJson(trainingExercisesJson, TrainingExercises::class.java)

                    trainingExercisesList.add(trainingExercisesAux)
                }
            }
            trainingExercisesList.sortBy { it.secuencia.toInt() }
            _activePausesList.value = trainingExercisesList
        }
    }
}