package com.example.myapplication.screens.editinformation

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.Response
import com.example.myapplication.models.Response.Success
import com.example.myapplication.models.Response.Failure
import com.example.myapplication.models.Response.Loading

import com.example.myapplication.respository.ImageRepository
import com.example.myapplication.screens.homescreen.HomeScreenViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Provides
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class EditInformationViewModel @Inject constructor(
    private val repo: ImageRepository
): ViewModel() {

    var addImageToStorageResponse by mutableStateOf<Response<Uri>>(Success(null))
        private set
    var addImageToDatabaseResponse by mutableStateOf<Response<Boolean>>(Success(null))
        private set
    var getImageFromDatabaseResponse by mutableStateOf<Response<String>>(Success(null))
        private set
    val currentUser = HomeScreenViewModel.currentUserLogged

    private val _firstClickOnImage = MutableLiveData<Boolean>()
    val firstClickOnImage: LiveData<Boolean> = _firstClickOnImage

    private val _imgLinkUser = MutableLiveData<String>()
    val imgLinkUser: LiveData<String> = _imgLinkUser

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    private val _userNumber = MutableLiveData<String>()
    val userNumber: LiveData<String> = _userNumber

    private val _journeySelected = MutableLiveData<String>()
    val journeySelected: LiveData<String> = _journeySelected

    private val _updatedImgFinalDialog = MutableLiveData<Boolean>()
    val updatedImgFinalDialog: LiveData<Boolean> = _updatedImgFinalDialog

    private val _updatedInfoFinalDialog = MutableLiveData<Boolean>()
    val updatedInfoFinalDialog: LiveData<Boolean> = _updatedInfoFinalDialog

    private val _editInformationOption1 = MutableLiveData<Boolean>()
    val editInformationOption1: LiveData<Boolean> = _editInformationOption1

    private val _editInformationOption2 = MutableLiveData<Boolean>()
    val editInformationOption2: LiveData<Boolean> = _editInformationOption2

    private val _editInformationButton = MutableLiveData<Boolean>()
    val editInformationButton: LiveData<Boolean> = _editInformationButton

    fun changeValueEditInfo1(Boolean: Boolean){
        _editInformationOption1.value = !Boolean
    }

    fun changeValueEditInfo2(Boolean: Boolean){
        _editInformationOption2.value = !Boolean
    }

    fun changeValueDialog(Boolean: Boolean){
        _updatedImgFinalDialog.value = !Boolean
    }

    fun changeValueImgLinkUser(String: String){
        _imgLinkUser.value = String
    }
    fun changeValueDialog2(Boolean: Boolean){
        _updatedInfoFinalDialog.value = !Boolean
    }


    fun changeValueCanUpdate(){
        if (_userName.value?.isNotEmpty() == true && _userNumber.value?.isNotEmpty() == true && _journeySelected.value?.isNotEmpty() == true){
            _editInformationButton.value = _userNumber.value!!.length > 6 && isValidOnlyNumbers(_userNumber.value!!)
    }else _editInformationButton.value = false
    }

    fun changeValueFirstClickOnImage(Boolean: Boolean){
        _firstClickOnImage.value = !Boolean
    }

    fun addImageToStorage(imageUri: Uri) = viewModelScope.launch {
        addImageToDatabaseResponse = Loading
        addImageToStorageResponse = repo.AddImageToStorageResponse(imageUri)
    }

    fun addImageToDatabase(downloadUrl: Uri) = viewModelScope.launch {
        addImageToDatabaseResponse = Loading
        addImageToDatabaseResponse = repo.AddImageToFirestoreResponse(downloadUrl)
    }

    fun getImageFromDatabase() = viewModelScope.launch {
        getImageFromDatabaseResponse = Loading
        getImageFromDatabaseResponse = repo.GetImageFromFirestoreResponse()
    }

    fun onTextFieldChange(nameUser: String){
        _userName.value = nameUser
        changeValueCanUpdate()
    }

    fun onTextFieldChangeForNumber(it: String) {
        _userNumber.value = it
        changeValueCanUpdate()
    }

    fun isValidOnlyNumbers(it: String): Boolean {
        return try {
            it.toDouble() // Intenta convertir el String a Double
            true // Si no hay excepción, el String es un número
        } catch (e: NumberFormatException) {
            false // Si ocurre una excepción, el String no es un número
        }
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
                document.reference.update("names", _userName.value).await()
                document.reference.update("phone", _userNumber.value).await()
                document.reference.update("journeyAvailable", _journeySelected.value).await()



            }
        } catch (e: Exception) {
            print(e)
        }
    }

    fun changeJourneylValue(journeySelect: String) {
        _journeySelected.value = journeySelect
        changeValueCanUpdate()

    }

}