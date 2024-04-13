package com.example.myapplication.screens.foodsvisualizerlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.models.FoodModel
import com.example.myapplication.models.TrainingExercises
import com.example.myapplication.utils.Constants
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class FoodsVisualizerListViewModel {
    val db = Firebase.firestore

    private val _foodList = MutableLiveData<List<FoodModel>>()
    val foodList: LiveData<List<FoodModel>> = _foodList

    //Lista de Ejercicios


    fun getFoodList(){
        var foodModelAux: FoodModel
        val foodModelAuxList= mutableListOf<FoodModel>()
        val gson = Gson()

        val doc = db.collection(Constants.FOOD_LIST).get().addOnCompleteListener(){ result ->
            for(foodInstance in result.result){

                if (foodInstance.exists()){

                    val foodMatch = foodInstance.data as Map<*,*>
                    val foodJson =  gson.toJson(foodMatch)

                    foodModelAux = gson.fromJson(foodJson, FoodModel::class.java)

                    foodModelAuxList.add(foodModelAux)
                }
            }
            _foodList.value = foodModelAuxList
        }
    }

    companion object{
        lateinit var foodSelectedForDescription: FoodModel
    }
}