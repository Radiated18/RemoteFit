package com.example.myapplication.respository

import android.net.Uri
import com.example.myapplication.models.Response

typealias AddImageToStorageResponse = Response<Uri>
typealias AddImageToFirestoreResponse = Response<Boolean>
typealias GetImageFromFirestoreResponse = Response<String>


interface ImageRepository {
    suspend fun AddImageToStorageResponse(imageUri: Uri): AddImageToStorageResponse
    suspend fun AddImageToFirestoreResponse(download: Uri): AddImageToFirestoreResponse
    suspend fun GetImageFromFirestoreResponse(): GetImageFromFirestoreResponse

}