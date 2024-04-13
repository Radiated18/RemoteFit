package com.example.myapplication.models

import android.util.Log

data class FoodModel(
    val nombre: String,
    val jornada: String,
    val receta: String,
    val img_url: String,
    val ingrediente1: Map<String, Any>,
    val ingrediente2: Map<String, Any>,
    val ingrediente3: Map<String, Any>,
    val ingrediente4: Map<String, Any>,
    val ingrediente5: Map<String, Any>,
    val ingrediente6: Map<String, Any>

){
    private fun getSumTotalOfCaloriesModifiableInFood(): Int {
        var sumTotalOfCaloriesModifibleFood = 0
        if (this.ingrediente1.isNotEmpty()){
            if (this.ingrediente1["cantidad"] is Number){
                sumTotalOfCaloriesModifibleFood += (this.ingrediente1["calorias"] as Double).toInt()
            }
        }
        if (this.ingrediente2.isNotEmpty()){
            if (this.ingrediente2["cantidad"] is Number){
                sumTotalOfCaloriesModifibleFood += (this.ingrediente2["calorias"] as Double).toInt()
            }        }
        if (this.ingrediente3.isNotEmpty()){
            if (this.ingrediente3["cantidad"] is Number){
                sumTotalOfCaloriesModifibleFood += (this.ingrediente3["calorias"] as Double).toInt()
            }        }
        if (this.ingrediente4.isNotEmpty()){
            if (this.ingrediente4["cantidad"] is Number){
                sumTotalOfCaloriesModifibleFood += (this.ingrediente4["calorias"] as Double).toInt()
            }        }
        if (this.ingrediente5.isNotEmpty()){
            if (this.ingrediente5["cantidad"] is Number){
                sumTotalOfCaloriesModifibleFood += (this.ingrediente5["calorias"] as Double).toInt()
            }        }
        if (this.ingrediente6.isNotEmpty()){
            if (this.ingrediente6["cantidad"] is Number){
                sumTotalOfCaloriesModifibleFood += (this.ingrediente6["calorias"] as Double).toInt()
            }        }

        return sumTotalOfCaloriesModifibleFood
    }

    private fun getSumTotalOfCaloriesFood(): Int {
        var sumTotalOfCaloriesFood = 0
        if (this.ingrediente1.isNotEmpty()){
            Log.d("ingrediente 1", this.ingrediente1.toString())
            sumTotalOfCaloriesFood += (this.ingrediente1["calorias"] as Double).toInt()
        }
        if (this.ingrediente2.isNotEmpty()){
            sumTotalOfCaloriesFood += (this.ingrediente2["calorias"] as Double).toInt()
        }
        if (this.ingrediente3.isNotEmpty()){
            sumTotalOfCaloriesFood += (this.ingrediente3["calorias"] as Double).toInt()
        }
        if (this.ingrediente4.isNotEmpty()){
            sumTotalOfCaloriesFood += (this.ingrediente4["calorias"] as Double).toInt()
        }
        if (this.ingrediente5.isNotEmpty()){
            sumTotalOfCaloriesFood += (this.ingrediente5["calorias"] as Double).toInt()
        }
        if (this.ingrediente6.isNotEmpty()){
            sumTotalOfCaloriesFood += (this.ingrediente6["calorias"] as Double).toInt()
        }

        return sumTotalOfCaloriesFood
    }

    fun adjustQuantityOfIngredients(userModel: UserModel): MutableList<MutableMap<String, Any>> {
        val sumTotalOfCaloriesFood = getSumTotalOfCaloriesFood()

        val percentOfFood = getValueDependFood(userModel, this.jornada)
        val valueOfFoodJourneyToEat = userModel.getTotalDailyEnergyExpenditure() * percentOfFood

        Log.d("Porcentaje de Comida", percentOfFood.toString())


        val copyMapIngredient1 = this.ingrediente1 as MutableMap<String, Any>
        val copyMapIngredient2 = this.ingrediente2 as MutableMap<String, Any>
        val copyMapIngredient3 = this.ingrediente3 as MutableMap<String, Any>
        val copyMapIngredient4 = this.ingrediente4 as MutableMap<String, Any>
        val copyMapIngredient5 = this.ingrediente5 as MutableMap<String, Any>
        val copyMapIngredient6 = this.ingrediente6 as MutableMap<String, Any>

        val mutableListOfMaps = mutableListOf(copyMapIngredient1, copyMapIngredient2, copyMapIngredient3, copyMapIngredient4, copyMapIngredient5, copyMapIngredient6)

        val sumOnlyCaloriesModifiable = getSumTotalOfCaloriesModifiableInFood()

        Log.d("Valor calorico de la jornada", valueOfFoodJourneyToEat.toString())
        Log.d("Valor calorico de los ingredientes modificables", sumOnlyCaloriesModifiable.toString())

        val adjustFactorForValuesModificable = valueOfFoodJourneyToEat / sumOnlyCaloriesModifiable

        Log.d("Valor de ajuste", adjustFactorForValuesModificable.toString())


        val mutableListOfMapsWithChanges = getMutableListWithNewValues(mutableListOfMaps, adjustFactorForValuesModificable)

        return mutableListOfMapsWithChanges


    }

    private fun getMutableListWithNewValues(mutableListOfMaps: MutableList<MutableMap<String, Any>>, adjustFactorForValuesModificable: Double): MutableList<MutableMap<String, Any>> {

        for (map in mutableListOfMaps){
            if (map["cantidad"] != null){
                if (map["cantidad"] is Number){
                    Log.d("Antes", map.toString())

                    map["cantidad"] = (map["cantidad"] as Double).toInt() * adjustFactorForValuesModificable
                    map["calorias"] = (map["calorias"] as Double).toInt() * adjustFactorForValuesModificable
                    map["carbohidratos"] = (map["carbohidratos"] as Double).toInt() * adjustFactorForValuesModificable
                    map["grasas"] = (map["grasas"] as Double).toInt() * adjustFactorForValuesModificable
                    map["proteinas"] = (map["proteinas"] as Double).toInt() * adjustFactorForValuesModificable

                    Log.d("Despues", map.toString())

                }
            }

        }
        return mutableListOfMaps
    }

    private fun getValueDependFood(userModel: UserModel, jornada: String): Double {
        return when(jornada){
            "Desayuno" -> userModel.gLimitCalorieBreakfast()
            "Almuerzo" -> userModel.gLimitCalorieLunch()
            "Media tarde" -> userModel.gLimitCalorieMiddleAfternoon()
            else -> userModel.gLimitCalorieDinner()
        }
    }

    fun getListOfIngredients(): MutableList<String> {
        val listOfIngredientes = mutableListOf<String>()
        if (this.ingrediente1.isNotEmpty()){
            listOfIngredientes.add(ingrediente1["nombre"] as String)
        }
        if (this.ingrediente2.isNotEmpty()){
            listOfIngredientes.add(ingrediente2["nombre"] as String)
        }
        if (this.ingrediente3.isNotEmpty()){
            listOfIngredientes.add(ingrediente3["nombre"] as String)
        }
        if (this.ingrediente4.isNotEmpty()){
            listOfIngredientes.add(ingrediente4["nombre"] as String)
        }
        if (this.ingrediente5.isNotEmpty()){
            listOfIngredientes.add(ingrediente5["nombre"] as String)
        }
        if (this.ingrediente6.isNotEmpty()){
            listOfIngredientes.add(ingrediente6["nombre"] as String)
        }
        return listOfIngredientes
    }

    companion object{
        fun obtainTotalOfCalories(mutableMap: MutableList<MutableMap<String, Any>>): Int {
            var sumTotal = 0
            for (map in mutableMap){
                if (map["calorias"] != null){
                    sumTotal += (map["calorias"] as Double).toInt()
                }

            }
            return sumTotal
        }
        fun obtainTotalOfProtein(mutableMap: MutableList<MutableMap<String, Any>>): Int {
            var sumTotal = 0
            for (map in mutableMap){
                if (map["proteinas"] != null){
                    sumTotal += (map["proteinas"] as Double).toInt()
                }

            }
            return sumTotal
        }
        fun obtainTotalOfCarbohidrats(mutableMap: MutableList<MutableMap<String, Any>>): Int {
            var sumTotal = 0
            for (map in mutableMap){
                if (map["carbohidratos"] != null){
                    sumTotal += (map["carbohidratos"] as Double).toInt()
                }

            }
            return sumTotal
        }
        fun obtainTotalOfGrasas(mutableMap: MutableList<MutableMap<String, Any>>): Int {
            var sumTotal = 0
            for (map in mutableMap){
                if (map["grasas"] != null){
                    sumTotal += (map["grasas"] as Double).toInt()
                }

            }
            return sumTotal
        }
    }
}

