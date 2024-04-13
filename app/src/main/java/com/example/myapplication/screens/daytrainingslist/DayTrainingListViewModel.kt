package com.example.myapplication.screens.daytrainingslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.models.TrainingExercises
import com.example.myapplication.utils.Constants
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class DayTrainingListViewModel {
    val db = Firebase.firestore

    //Lista de Ejercicios
    private val _exercisesTrainingsList = MutableLiveData<List<TrainingExercises>>()
    val exercisesTrainingsList: LiveData<List<TrainingExercises>> = _exercisesTrainingsList

    private val _enableShowDialog = MutableLiveData<Boolean>()
    val enableShowDialog: LiveData<Boolean> = _enableShowDialog

    private val _exerciseTrainingShower = MutableLiveData<TrainingExercises>()
    val exerciseTrainingShower: LiveData<TrainingExercises> = _exerciseTrainingShower

    fun selectableTrainingShower(TrainingExercise: TrainingExercises){
        _exerciseTrainingShower.value = TrainingExercise
    }

    fun changeValueDialog(Boolean: Boolean){
        _enableShowDialog.value = !Boolean
    }


    fun getExercisesList(){
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
            _exercisesTrainingsList.value = trainingExercisesList
        }
    }

    fun getTrainingTextForDay(selectedDay: String): String {
        return when (selectedDay.lowercase()) {
            "lunes" -> "Espalda y biceps"
            "martes" -> "Pantorrilla Pierna Abdomen y hombro"
            "miercoles" -> "Pecho y triceps"
            "jueves" -> "Espalda y biceps"
            "viernes" -> "Pierna Abdomen y hombro"
            "sabado" -> "Pantorrilla pecho y triceps"
            else -> "?"
        }
    }
}