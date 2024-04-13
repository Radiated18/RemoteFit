package com.example.myapplication.screens.finalobjetive

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.models.FinalObjetiveForUser
import com.example.myapplication.screens.homescreen.HomeScreenViewModel
import com.example.myapplication.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.abs
import kotlin.math.log
import kotlin.math.pow

class FinalObjetiveViewModel {
    //Para crear el usuario en FireBase
    private val auth: FirebaseAuth = Firebase.auth
    val db = Firebase.firestore


    //Para Limitar decimales
    private val df = DecimalFormat("#.###")

    val currentUser = HomeScreenViewModel.currentUserLogged

    //Variables de Modificacion por el Ui
    private val _weight = MutableLiveData<String>()
    val weight: LiveData<String> = _weight

    private val _dateRegister = MutableLiveData<String>()
    val dateRegister: LiveData<String> = _dateRegister

    private val _per_adb = MutableLiveData<String>()
    val per_adb: LiveData<String> = _per_adb

    private val _per_hip = MutableLiveData<String>()
    val per_hip: LiveData<String> = _per_hip

    private val _per_neck = MutableLiveData<String>()
    val per_neck: LiveData<String> = _per_neck

    //Variables de moficacion interna
    private val _index_imc = MutableLiveData<Double>()
    val index_imc: LiveData<Double> = _index_imc

    private val _index_imm = MutableLiveData<Double>()
    val index_imm: LiveData<Double> = _index_imm

    private val _index_ica = MutableLiveData<Double>()
    val index_ica: LiveData<Double> = _index_ica

    private val _index_perc_bfat = MutableLiveData<Double>()
    val index_perc_bfat: LiveData<Double> = _index_perc_bfat

    private val _validComponentCreate = MutableLiveData<Boolean>()
    val validComponentCreate: LiveData<Boolean> = _validComponentCreate

    private val _enableUpdateDialog = MutableLiveData<Boolean>()
    val enableUpdateDialog: LiveData<Boolean> = _enableUpdateDialog

    private val _objetiveUserGet = MutableLiveData<FinalObjetiveForUser>()
    val objetiveUserGet: LiveData<FinalObjetiveForUser> = _objetiveUserGet

    fun changeValueDialog(Boolean: Boolean){
        _enableUpdateDialog.value = !Boolean
    }

    fun onTextFieldChangePersonalData(weight: String, per_adb: String, per_hip: String, per_neck: String, dateRegister: String) {

        _weight.value = weight
        _per_adb.value = per_adb
        _per_hip.value = per_hip
        _per_neck.value = per_neck
        _dateRegister.value = dateRegister

        updateIndexes(weight, per_adb, per_hip, per_neck)

        _validComponentCreate.value = (!_weight.value.isNullOrBlank() && !_per_adb.value.isNullOrBlank()
                && !_per_hip.value.isNullOrBlank() && !_per_neck.value.isNullOrBlank()
                && _index_perc_bfat.isInitialized)

    }

    fun getDailyDifferenceForFinalObjetive(): Int {
        val currentUser = HomeScreenViewModel.currentUserLogged

        if(!weight.value.isNullOrBlank()) {
            val diffenceWeight = abs(currentUser!!.weight.toInt() - weight.value!!.toInt())

            val dailyCaloriesUser = currentUser.getTotalDailyEnergyExpenditure()
            val daysToLoseOneKilogram = 7700 / dailyCaloriesUser

            return (diffenceWeight * daysToLoseOneKilogram).toInt()
        }
        return 0
    }

    fun updateIndexes(weight: String, per_adb: String, per_hip: String, per_neck: String){
        if (!weight.isNullOrBlank() && !per_adb.isNullOrBlank() && !per_hip.isNullOrBlank() && !per_neck.isNullOrBlank()){
            var indexWei = -1
            var indexPA = -1
            var indexHip = -1
            var indexNeck = -1

            try {
                indexWei = weight.toInt() //Los convertimos a enteros para poder operarlos
                indexPA = per_adb.toInt() //Los convertimos a enteros para poder operarlos
                indexHip = per_hip.toInt() //Los convertimos a enteros para poder operarlos
                indexNeck = per_neck.toInt() //Los convertimos a enteros para poder operarlos

            }catch (e: NumberFormatException){
                print(e)
            }

            if (indexPA != -1 && indexWei != -1 && indexHip != -1){
                //Formula de Indice de Masa Corporal
                _index_imc.value = getIndexImc(indexWei)

                //Formula de Indice de Masa Magra
                _index_imm.value = getIndexImm(indexWei)

                //Formula de Indice de Cintura - Altura
                _index_ica.value = getIndexIca(indexPA)

                //Formula para % de Grasa Corporal
                _index_perc_bfat.value = getIndexPercBfat(indexPA, indexHip, indexNeck)

                changeDateValue()
            }

        }
    }

    private fun getIndexImc(IndexW: Int): Double{
        df.roundingMode = RoundingMode.DOWN
        return df.format((IndexW).toDouble() / ((currentUser!!.height.toDouble()) / 100).pow(2.0)).toDouble()
    }

    private fun getIndexImm(IndexW: Int): Double{
        df.roundingMode = RoundingMode.DOWN
        if (currentUser!!.sex == "Masculino"){
            return df.format(((1.10 * IndexW) - (128 * ((IndexW.toDouble().pow(2.0)) / currentUser.height.toDouble()
                .pow(2.0))))).toDouble()

        } else return df.format(((1.07 * IndexW) - (148 * (IndexW.toDouble().pow(2.0)) / currentUser.height.toDouble()
            .pow(2.0)))).toDouble()
    }

    private fun getIndexIca(IndexPA: Int): Double{
        df.roundingMode = RoundingMode.DOWN
        return df.format((IndexPA).toDouble() / (currentUser!!.height).toDouble()).toDouble()
    }

    private fun getIndexPercBfat(IndexPA: Int, IndexHip: Int, IndexNeck: Int): Double{
        df.roundingMode = RoundingMode.DOWN
        if (currentUser!!.sex == "Masculino"){
            return df.format((495/(1.0324 - (0.19077 * log((IndexPA.toDouble() - IndexNeck.toDouble()), 10.0)) + (0.15456 * log(currentUser.height.toDouble(), 10.0)))) - 450).toDouble()
        } else return df.format((495/(1.29579 - (0.35004 * log((IndexPA.toDouble() + IndexHip.toDouble() - IndexNeck.toDouble()), 10.0)) + (0.22100 * log(currentUser.height.toDouble(), 10.0)))) - 450).toDouble()
    }

    @SuppressLint("SimpleDateFormat")
    fun changeDateValue() {
        val dailyDifference = getDailyDifferenceForFinalObjetive()
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, dailyDifference)
        val formattedDate = SimpleDateFormat("yyyy-MM-dd").format(calendar.time)
        _dateRegister.value = formattedDate
    }

    fun postDataNewRecord(){
        if (_validComponentCreate.value == true){
            //Lanzamos el llamado para crear el nuevo registro
            createNewRegisterRecord()
        }
    }

    private fun createNewRegisterRecord(){
        val userId = auth.currentUser?.uid
        //Fecha actual

        val registerRecord = FinalObjetiveForUser(
            userId!!, _dateRegister.value.toString(), _weight.value!!.toInt(), _per_adb.value!!.toInt(),
            _per_hip.value!!.toInt(), _per_neck.value!!.toInt(), _index_imc.value!!, index_imm.value!!,
            _index_ica.value!!, _index_perc_bfat.value!!
        ).toMap()

        FirebaseFirestore.getInstance().collection(Constants.OBJETIVE_RECORD_FINAL)
            .add(registerRecord)
            .addOnSuccessListener {
                Log.d("respuestadb", "Creado Correctamente")
            }
            .addOnFailureListener {
                Log.d("respuestadb:", "Fallo Con Ganas")
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

                    if (finalObjetiveInstance.data["id_user"] == currentUser!!.id_user){

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