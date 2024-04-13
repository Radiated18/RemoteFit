package com.example.myapplication.screens.formpage

import android.util.Log
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.models.SelectionOption
import com.example.myapplication.models.UserModel
import com.example.myapplication.screens.register.RegisterViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class FormPageViewModel {
    //Para crear el usuario en FireBase
    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)

    //Atributos Utilizados
    private val _physical_build = MutableLiveData<String>()
    val physical_build: LiveData<String> = _physical_build

    private val _weight = MutableLiveData<String>()
    val weight: LiveData<String> = _weight

    private val _height = MutableLiveData<String>()
    val height: LiveData<String> = _height

    private val _levelUser = MutableLiveData<String>()

    private val _enableRegister = MutableLiveData<Boolean>()
    val enableRegister: LiveData<Boolean> = _enableRegister

    private val _habitos_al = listOf(
        SelectionOption("Nunca", false, 0),
        SelectionOption("Ocasionalmente", false, 1),
        SelectionOption("Diariamente", false, 2),
        SelectionOption("Semanalmente", false, 3),
        ).toMutableStateList()

    val habitos_al: List<SelectionOption>
        get() = _habitos_al

    private val _frutas_por = listOf(
        SelectionOption("Ninguna", false, 0),
        SelectionOption("1 - 2 Porciones", false, 1),
        SelectionOption("3 - 4 Porciones", false, 2),
        SelectionOption("Mas de 4 Porciones", false, 3),
    ).toMutableStateList()

    val frutas_por: List<SelectionOption>
        get() = _frutas_por

    private val _ejercicio_rat = listOf(
        SelectionOption("Nunca", false, 0),
        SelectionOption("1 - 3 Veces Por Semana", false, 1),
        SelectionOption("3 - 5 Veces Por Semana", false, 2),
        SelectionOption("6 - 7 Veces Por Semana", false, 3),
        SelectionOption("2 Veces Por Dia", false, 4),
        ).toMutableStateList()

    val ejercicio_rat: List<SelectionOption>
        get() = _ejercicio_rat

    private val _ejercicio_xtime = listOf(
        SelectionOption("Menos de 30 Minutos", false, 0),
        SelectionOption("30 - 60 Minutos", false, 1),
        SelectionOption("1 - 2 Horas", false, 2),
        SelectionOption("Mas de 2 Horas", false, 3),
    ).toMutableStateList()

    val ejercicio_xtime: List<SelectionOption>
        get() = _ejercicio_xtime

    private val _objetive_selection = listOf(
        SelectionOption("Definicion", false, 0),
        SelectionOption("Mantenimiento", false, 1),
        SelectionOption("Volumen", false, 2),
    ).toMutableStateList()

    val objetive_selection: List<SelectionOption>
        get() = _objetive_selection

    private val _journeyAvailable_seleccion = listOf(
        SelectionOption("Mañana", false, 0),
        SelectionOption("Tarde", false, 1),
        SelectionOption("Noche", false, 2),
    ).toMutableStateList()

    val journeyAvailable_seleccion: List<SelectionOption>
        get() = _journeyAvailable_seleccion


    //Funciones para Las Opciones de Seleccion
    fun selectionOptionHabitsSelected(selectedOption: SelectionOption) {
        _habitos_al.forEach { it.selected = false }
        _habitos_al.find { it.option == selectedOption.option }?.selected = true

        _enableRegister.value = confirmEnabledRegister()
    }

    fun selectionOptionFrutasPorSelected(selectedOption: SelectionOption) {
        _frutas_por.forEach { it.selected = false }
        _frutas_por.find { it.option == selectedOption.option }?.selected = true

        _enableRegister.value = confirmEnabledRegister()
    }

    fun selectionOptionEjercicioRatSelected(selectedOption: SelectionOption) {
        _ejercicio_rat.forEach { it.selected = false }
        _ejercicio_rat.find { it.option == selectedOption.option }?.selected = true

        _enableRegister.value = confirmEnabledRegister()
    }

    fun selectionOptionEjercicioTimePorSelected(selectedOption: SelectionOption) {
        _ejercicio_xtime.forEach { it.selected = false }
        _ejercicio_xtime.find { it.option == selectedOption.option }?.selected = true

        _enableRegister.value = confirmEnabledRegister()
    }

    fun selectionOptionObjetiveSelected(selectedOption: SelectionOption) {
        _objetive_selection.forEach { it.selected = false }
        _objetive_selection.find { it.option == selectedOption.option }?.selected = true

        _enableRegister.value = confirmEnabledRegister()
    }

    fun selectionJourneyAvailableSelected(selectedOption: SelectionOption) {
        _journeyAvailable_seleccion.forEach { it.selected = false }
        _journeyAvailable_seleccion.find { it.option == selectedOption.option }?.selected = true

        _enableRegister.value = confirmEnabledRegister()
    }

    //Funciones
    fun onTextFieldChangePersonalData(gender: String, weight: String, height: String) {

        _weight.value = weight
        _height.value = height

        _enableRegister.value = confirmEnabledRegister()
    }

    fun setLevelProfile(){
        val Index1 = _ejercicio_rat.indexOf(_ejercicio_rat.find { it.selected })
        val Index2 = _ejercicio_rat.indexOf(_ejercicio_xtime.find { it.selected }) + 1

        val Index3 = Index1 + Index2

        when(Index3){
            0, 1, 2 -> _levelUser.value = "Principiante"
            3, 4 -> _levelUser.value = "Intermedio"
            else -> _levelUser.value = "Avanzado"
        }
    }

    fun getObjetiveProfile(): SelectionOption? {
        return _objetive_selection.find{ it.selected }
    }

    fun getJourneyProfile(): SelectionOption? {
        return _journeyAvailable_seleccion.find{ it.selected }
    }

    fun confirmEnabledRegister(): Boolean {
        val Index1 = if (_habitos_al.find { it.selected } != null) 1 else 0
        val Index2 = if (_frutas_por.find { it.selected } != null) 1 else 0
        val Index3 = if (_ejercicio_rat.find { it.selected } != null) 1 else 0
        val Index4 = if (_ejercicio_xtime.find { it.selected } != null) 1 else 0
        val Index6 = if (_objetive_selection.find { it.selected } != null) 1 else 0
        val Index7 = if (_journeyAvailable_seleccion.find { it.selected } != null) 1 else 0


        val Index5 = Index1 + Index2 + Index3 + Index4 + Index6 + Index7

        var IndexW = -1
        var IndexH = -1

        if (_weight.isInitialized && _height.isInitialized){
            try {
                IndexW = _weight.value!!.toInt()
                IndexH = _height.value!!.toInt()
            }catch (e: NumberFormatException){
                print(e)
            }
        }

        return IndexH != -1 && IndexW != -1 && Index5 == 6
    }

    fun changePhysicalValue(physical: String){
        _physical_build.value = physical
    }

    fun createAuth(home: () -> Unit){

        val mapPreviewData = RegisterViewModel.userTemp
        val email = mapPreviewData.get("email")
        val password = mapPreviewData.get("password")


        if(_loading.value == false){
            _loading.value = true
            auth.createUserWithEmailAndPassword(email!!, password!!).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    createUser(mapPreviewData)
                    home()
                }
                else{
                    Log.d("", "La tarea fallo")
                }
                _loading.value = false
            }
        }
    }


    //Creacion de Usuario en Firebase

    private fun createUser(map: MutableMap<String,String?>) {
        setLevelProfile()
        val objetiveOption = getObjetiveProfile()!!.option
        val intensityValue = _ejercicio_rat.find { it.selected }!!.value
        val JourneyValue = getJourneyProfile()!!.option


        val userId = auth.currentUser?.uid

        val user = UserModel(userId.toString(),
            map.get("names").toString(),
            map.get("email").toString(),
            map.get("birthDate").toString(),
            map.get("phoneNumber").toString(),
            map.get("sexGender").toString(),
            _physical_build.value.toString(),
            _weight.value!!.toInt(),
            _height.value!!.toInt(),
            "",
            _levelUser.value.toString(),
            objetiveOption,
            intensityValue.toString(),
            JourneyValue
            ).toMap()

        FirebaseFirestore.getInstance().collection("users")
            .add(user)
            .addOnSuccessListener {
                Log.d("respuestadb", "Creado Correctamente")
            }
            .addOnFailureListener {
                Log.d("respuestadb:", "Fallo Con Ganas")
            }
    }
}