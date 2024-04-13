package com.example.myapplication.models

import java.time.Instant
import java.time.ZoneId
import java.util.regex.Pattern

data class UserModel(
    val id_user: String,
    val names: String,
    val email: String,
    val birthdate: String,
    val phone: String,
    val sex: String,
    val physical_build: String,
    val weight: Int,
    val height: Int,
    var img_link: String,
    val level_user: String,
    val objetiveProper: String,
    val intensity: String,
    val journeyAvailable: String

){
    fun gLimitCalorieBreakfast(): Double {
        return when(objetiveProper){
            "Definicion" -> 0.25
            "Volumen" -> 0.3
            else -> 0.25
        }
    }

    fun gLimitCalorieLunch(): Double {
        return when(this.objetiveProper){
            "Definicion" -> 0.3
            "Volumen" -> 0.35
            else -> 0.35
        }
    }

    fun gLimitCalorieMiddleAfternoon(): Double {
        return when(this.objetiveProper){
            "Definicion" -> 0.1
            "Volumen" -> 0.1
            else -> 0.15
        }
    }

    fun gLimitCalorieDinner(): Double {
        return when(this.objetiveProper){
            "Definicion" -> 0.35
            "Volumen" -> 0.25
            else -> 0.25
        }
    }



    fun toMap(): MutableMap<String, Any>{
        return mutableMapOf(
            "id_user" to this.id_user,
            "names" to this.names,
            "email" to this.email,
            "birthdate" to this.birthdate,
            "phone" to this.phone,
            "sex" to this.sex,
            "physical build" to this.physical_build,
            "weight" to this.weight,
            "height" to this.height,
            "img_link" to this.img_link,
            "level_user" to this.level_user,
            "objetiveProper" to this.objetiveProper,
            "intensity" to this.intensity,
            "journeyAvailable" to this.journeyAvailable
        )
    }

    fun getTotalDailyEnergyExpenditure(): Double{
        var BMR = 0.0
        var activityFactor = 1.0

        val sprbirtdateOfUser = Pattern.compile("-").split(this.birthdate)
        val yearOfUser = sprbirtdateOfUser[0]
        val actualYear = Instant.ofEpochMilli(Instant.now().toEpochMilli()).atZone(ZoneId.of("UTC")).toLocalDate().year
        val ageOfUser = actualYear - yearOfUser.toInt()

        if(this.sex == "Masculino"){
            BMR = 88.362 + (13.397 * this.weight) + (4.799 * this.height) - (5.677 * ageOfUser)
        }
        if(this.sex == "Femenino"){
            BMR = 447.593 + (9.247 * this.weight) + (3.098  * this.height) - (4.330  * ageOfUser)

        }

        when(this.intensity.toInt()){
            0 -> activityFactor = 1.2
            1 -> activityFactor = 1.375
            2 -> activityFactor = 1.55
            3 -> activityFactor = 1.725
            4 -> activityFactor = 1.9
        }

        val adjustInBaseOfObjetiveForUser = when(this.objetiveProper){
            "Definicion"  -> -500
            "Volumen" -> 500
            else -> 0

        }
        return (BMR * activityFactor) + adjustInBaseOfObjetiveForUser
    }


}
