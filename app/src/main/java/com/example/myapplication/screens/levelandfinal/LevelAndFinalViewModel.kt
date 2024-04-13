package com.example.myapplication.screens.levelandfinal

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.models.Response
import com.example.myapplication.models.SelectionOption
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class LevelAndFinalViewModel {
    private val _ejercicio_ratio = listOf(
        SelectionOption("Nunca", false, 0),
        SelectionOption("1 - 3 Veces Por Semana", false, 1),
        SelectionOption("3 - 5 Veces Por Semana", false, 2),
        SelectionOption("6 - 7 Veces Por Semana", false, 3),
        SelectionOption("2 Veces Por Dia", false, 4),
    ).toMutableStateList()

    val ejercicio_ratio: List<SelectionOption>
        get() = _ejercicio_ratio

    private val _finalObjetive = listOf(
        SelectionOption("Definicion", false, 0),
        SelectionOption("Mantenimiento", false, 1),
        SelectionOption("Volumen", false, 2),
    ).toMutableStateList()

    val finalObjetive: List<SelectionOption>
        get() = _finalObjetive

    private val _enableUpdateChange = MutableLiveData<Boolean>()
    val enableUpdateChange: LiveData<Boolean> = _enableUpdateChange

    private val _enableUpdateDialog = MutableLiveData<Boolean>()
    val enableUpdateDialog: LiveData<Boolean> = _enableUpdateDialog

    fun changeValueDialog(Boolean: Boolean){
        _enableUpdateDialog.value = !Boolean
    }

    fun selectionOptionEjercicioRatSelected(selectedOption: SelectionOption) {
        _ejercicio_ratio.forEach { it.selected = false }
        _ejercicio_ratio.find { it.option == selectedOption.option }?.selected = true

        _enableUpdateChange.value = confirmEnabledUpdate()
    }

    fun selectionOptionFinalObjetiveSelected(selectedOption: SelectionOption) {
        _finalObjetive.forEach { it.selected = false }
        _finalObjetive.find { it.option == selectedOption.option }?.selected = true

        _enableUpdateChange.value = confirmEnabledUpdate()
    }

    private fun confirmEnabledUpdate(): Boolean{
        return _ejercicio_ratio.find { it.selected } != null && _finalObjetive.find { it.selected } != null
    }

    suspend fun updateInformationInDatabase(){
        val usersCollection: CollectionReference = FirebaseFirestore.getInstance().collection("users")

        // Obtener el ID de usuario actual
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        val userId: String? = auth.currentUser?.uid
        try {
            // Realizar una consulta para encontrar el documento del usuario por su 'id_user'
            val querySnapshot = usersCollection.whereEqualTo("id_user", userId).get().await()

            // Iterar sobre los documentos encontrados (debería ser solo uno, ya que 'id_user' debería ser único)
            for (document in querySnapshot.documents) {
                // Actualizar el campo 'img_link' con el valor del Uri
                document.reference.update("objetiveProper", _finalObjetive.find { it.selected }!!.option).await()
                document.reference.update("intensity", _ejercicio_ratio.find { it.selected }!!.value).await()

            }
        } catch (e: Exception) {
            print(e)
        }
    }
}