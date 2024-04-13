package com.example.myapplication.data

import android.net.Uri
import com.example.myapplication.models.Response
import com.example.myapplication.respository.AddImageToFirestoreResponse
import com.example.myapplication.respository.AddImageToStorageResponse
import com.example.myapplication.respository.GetImageFromFirestoreResponse
import com.example.myapplication.respository.ImageRepository
import com.example.myapplication.screens.homescreen.HomeScreenViewModel
import com.example.myapplication.utils.Constants.CREATED_AT
import com.example.myapplication.utils.Constants.IMAGES
import com.example.myapplication.utils.Constants.IMAGE_NAME
import com.example.myapplication.utils.Constants.UID
import com.example.myapplication.utils.Constants.URL
import com.example.myapplication.utils.Constants.USERS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class ImageRespositoryImpl @Inject constructor(
    private val storage: FirebaseStorage,
    private val db: FirebaseFirestore
): ImageRepository {
    override suspend fun AddImageToStorageResponse(imageUri: Uri): AddImageToStorageResponse {
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        val userId: String? = auth.currentUser?.uid

        val ImageName = "$userId.jpg"

        return try{
            val downloadUrl = storage.reference.child(IMAGES).child(ImageName)
                .putFile(imageUri).await()
                .storage.downloadUrl.await()
            Response.Success(downloadUrl)
        } catch (e: Exception){
            Response.Failure(e)
        }
    }

    override suspend fun AddImageToFirestoreResponse(download: Uri): AddImageToFirestoreResponse {
        // Obtener la referencia a la colección 'users'
        val usersCollection: CollectionReference = FirebaseFirestore.getInstance().collection("users")

        // Obtener el ID de usuario actual
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        val userId: String? = auth.currentUser?.uid

        HomeScreenViewModel.currentUserLogged!!.img_link = download.toString()
        // Verificar que el usuario esté autenticado
        return try {
            // Realizar una consulta para encontrar el documento del usuario por su 'id_user'
            val querySnapshot = usersCollection.whereEqualTo("id_user", userId).get().await()

            // Iterar sobre los documentos encontrados (debería ser solo uno, ya que 'id_user' debería ser único)
            for (document in querySnapshot.documents) {
                // Actualizar el campo 'img_link' con el valor del Uri
                document.reference.update("img_link", download.toString()).await()
            }
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)

        }

    }

    override suspend fun GetImageFromFirestoreResponse(): GetImageFromFirestoreResponse {
        // Obtener la referencia a la colección 'users'
        val usersCollection: CollectionReference = FirebaseFirestore.getInstance().collection("users")

        // Obtener el ID de usuario actual
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        val userId: String? = auth.currentUser?.uid

        return try{
            val ImageUrl = db.collection(USERS).document(userId!!).get().await().getString("img_link")
            Response.Success(ImageUrl)
        } catch (e:Exception){
            Response.Failure(e)
        }
    }
}