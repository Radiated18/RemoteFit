package com.example.myapplication.screens.editinformation.ui

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditOff
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material.icons.filled.NoPhotography
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Accessibility
import androidx.compose.material.icons.outlined.AccessibilityNew
import androidx.compose.material.icons.outlined.EmojiPeople
import androidx.compose.material.icons.outlined.Nightlight
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material.icons.outlined.WbTwilight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.example.myapplication.models.Response
import com.example.myapplication.models.UserModel
import com.example.myapplication.screens.editinformation.EditInformationViewModel
import com.example.myapplication.screens.homescreen.HomeScreenViewModel
import com.example.myapplication.screens.login.ui.AlertDialogForBadCredentials
import com.example.myapplication.screens.navigation.BottomNavComponent
import com.example.myapplication.screens.navigation.ImageBackgroundAllAppScreen
import com.example.myapplication.screens.navigation.ProjectScreens
import com.example.myapplication.screens.navigation.TopAppBarBackWithIconInRight
import com.example.myapplication.ui.theme.GoldenOpaque
import com.example.myapplication.ui.theme.GrayDark
import com.example.myapplication.ui.theme.GrayForTopBars
import com.example.myapplication.ui.theme.RedCarmesi
import com.example.myapplication.ui.theme.WhiteBone
import com.example.myapplication.ui.theme.lusitanaBoldFont
import com.example.myapplication.ui.theme.lusitanaFont
import com.example.myapplication.utils.Constants.ALL_IMAGES
import kotlinx.coroutines.launch


@Composable
fun EditInformationScreen(navController: NavController, viewModel: EditInformationViewModel = hiltViewModel()){
    val currentUser = HomeScreenViewModel.currentUserLogged
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val updatedImgFinalDialog: Boolean by viewModel.updatedImgFinalDialog.observeAsState(initial = false)//Nombre mutable que se escribe
    val availableButtonToUpdate: Boolean by viewModel.editInformationButton.observeAsState(initial = false)//Nombre mutable que se escribe
    val updatedInfoFinalDialog: Boolean by viewModel.updatedInfoFinalDialog.observeAsState(initial = false)//Nombre mutable que se escribe

    AlertDialogForUpdateImage(updatedImgFinalDialog,
        funForEvery = {
            viewModel.changeValueDialog(updatedImgFinalDialog)
            navController.navigate(ProjectScreens.EditInformationScreen.name)
        }
    )

    AlertDialogForUpdateInformation(updatedInfoFinalDialog,
        funForEvery = {
            viewModel.changeValueDialog2(updatedInfoFinalDialog)
            navController.navigate(ProjectScreens.HomeScreen.name)
        }
    )

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = GetContent()
    ){imageUri ->
        imageUri?.let {
            viewModel.addImageToStorage(imageUri)
        }

    }

    Scaffold(
        topBar = { TopAppBarBackWithIconInRight(navController, currentUser!!,"Editar Información") },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { innerPaddin ->

            Box(
                modifier = Modifier
                    .padding(innerPaddin)
                    .fillMaxSize()
            ) {
                ImageBackgroundAllAppScreen()
                Spacer(modifier = Modifier.padding(10.dp))

                LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {

                    item {
                        Box(modifier = Modifier
                            .padding(vertical = 25.dp)

                            .fillMaxSize(0.3f)
                        ){
                            CircleImageProfile(currentUser!!, viewModel){
                                galleryLauncher.launch(ALL_IMAGES)

                            }
                            AddImageToStorageResponse(viewModel){ downloadUrl ->
                                viewModel.addImageToDatabase(downloadUrl)

                            }
                            UpdateInformationFromDatabaseAndCircle(viewModel)
                        }
                    }

                    item {
                        //Componentes de Text Field
                        TextFieldToChangeName(viewModel)
                        Spacer(modifier = Modifier.padding(10.dp))

                        TextFieldToChangeNumber(viewModel)

                        TextFieldToChangeJourney(viewModel)
                        Spacer(modifier = Modifier.padding(10.dp))

                        TextFieldToViewInformation("Sexo", "Sexo", currentUser!!.sex)
                        Spacer(modifier = Modifier.padding(10.dp))
                        Spacer(modifier = Modifier.padding(10.dp))
                    }

                    item {
                        ButtonToUpdateUserInfo(availableButtonToUpdate){
                            coroutineScope.launch{
                                viewModel.updateInformationInDatabase()
                            }
                            viewModel.changeValueDialog2(updatedInfoFinalDialog)
                        }
                    }


                }
            }
        },
        bottomBar = { BottomNavComponent(navController)},

    )

}

@Composable
fun AlertDialogForUpdateInformation(updatedImgFinalDialog: Boolean, funForEvery: () -> Unit) {
    if (updatedImgFinalDialog) {
        AlertDialog(
            onDismissRequest = {
                funForEvery()
            },
            title = {
                Text(
                    text = "Datos Actualizados",
                    fontFamily = lusitanaBoldFont,
                    fontSize = 22.sp,
                    overflow = TextOverflow.Ellipsis,
                    color = WhiteBone,
                    fontWeight = FontWeight.Bold,
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Tus datos se han actualizado correctamente",
                        fontFamily = lusitanaFont,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        color = WhiteBone,
                    )

                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        funForEvery()
                    }
                ) {
                    Text(
                        text = "Continuar",
                        fontFamily = lusitanaFont,
                        fontSize = 16.sp,
                        overflow = TextOverflow.Ellipsis,
                        color = GoldenOpaque,
                    )
                }
            },
            icon = {
                Icon(
                    Icons.Filled.TaskAlt,
                    contentDescription = "Quest",
                    tint = GoldenOpaque,
                    modifier = Modifier.fillMaxSize(0.2f)
                )
            },
            containerColor = GrayForTopBars,
            shape = RoundedCornerShape(10.dp),
            tonalElevation = 10.dp
        )
    }
}

@Composable
fun ButtonToUpdateUserInfo(availableButtonToUpdate: Boolean, function: () -> Unit) {
    Button(
        onClick = {
            function()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = GoldenOpaque,
            disabledContainerColor = Color(0xFF73777F)
        ),
        enabled = availableButtonToUpdate,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(horizontal = 80.dp)
            .fillMaxWidth()
            .height(45.dp)
    ) {
        Text(
            text = "Actualizar Datos",
            fontFamily = lusitanaBoldFont,
            color = WhiteBone,
            fontSize = 18.sp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldToChangeJourney(viewModel: EditInformationViewModel) {
    val currentUser = HomeScreenViewModel.currentUserLogged

    var expanded by remember { mutableStateOf(false) }
    var journeySelect by remember { mutableStateOf(currentUser!!.journeyAvailable) }

    val placeholderText = "Selecciona Tu Jornada con Mayor disponibilidad"
    val labelText = "Jornada"

    TextField(
        value = journeySelect, //Atributo que se requiere para el cambio
        onValueChange = { },
        label = { Text(text = labelText, fontFamily = lusitanaFont) },
        placeholder = { Text(text = placeholderText, fontFamily = lusitanaFont) },
        readOnly = true,
        colors = ExposedDropdownMenuDefaults.textFieldColors(
            focusedContainerColor = GrayDark.copy(alpha = 0.9f),
            unfocusedContainerColor = GrayDark.copy(alpha = 0.9f),
            focusedTextColor = WhiteBone,
            unfocusedTextColor = WhiteBone,
            focusedIndicatorColor = GoldenOpaque,
            unfocusedIndicatorColor = GoldenOpaque,
            cursorColor = GoldenOpaque,
            focusedLabelColor = GoldenOpaque,
            unfocusedLabelColor = WhiteBone,
            focusedPlaceholderColor = WhiteBone,
            unfocusedPlaceholderColor = WhiteBone,
            //Colores para los errores
            errorContainerColor = GrayDark.copy(alpha = 0.9f),
            errorTextColor = WhiteBone,
            errorCursorColor = RedCarmesi,
            errorLabelColor = RedCarmesi
        ),
        trailingIcon = {
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Default.ArrowDownward,
                    contentDescription = "",
                    tint = WhiteBone
                )
            }
        },
        modifier = Modifier
            .padding(horizontal = 25.dp)
            .fillMaxWidth()
    )
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        offset = DpOffset(500.dp, 10.dp)
    ) {
        DropdownMenuItem(
            text = { Text("Mañana") },
            onClick = {
                journeySelect = "Mañana"
                expanded = false
                viewModel.changeJourneylValue(journeySelect)
            },
            trailingIcon = {
                Icon(
                    Icons.Outlined.WbTwilight,
                    contentDescription = null,
                    tint = GoldenOpaque
                )
            })

        DropdownMenuItem(
            text = { Text("Tarde") },
            onClick = {
                journeySelect = "Tarde"
                expanded = false
                viewModel.changeJourneylValue(journeySelect)
            },
            trailingIcon = {
                Icon(
                    Icons.Outlined.WbSunny,
                    contentDescription = null,
                    tint = GoldenOpaque
                )
            })
        DropdownMenuItem(
            text = { Text("Noche") },
            onClick = {
                journeySelect = "Noche"
                expanded = false
                viewModel.changeJourneylValue(journeySelect)
            },
            trailingIcon = {
                Icon(
                    Icons.Outlined.Nightlight,
                    contentDescription = null,
                    tint = GoldenOpaque
                )
            })
    }
}

@Composable
fun UpdateInformationFromDatabaseAndCircle(viewModel: EditInformationViewModel){
    when (val getImageFromDatabaseResponse = viewModel.getImageFromDatabaseResponse){
        is Response.Loading -> CircularProgressIndicator(color = GoldenOpaque)
        is Response.Success -> getImageFromDatabaseResponse.data?.let { imgUrl ->
            LaunchedEffect(imgUrl){
                viewModel.changeValueImgLinkUser(imgUrl)
                viewModel.changeValueDialog(false)
            }
        }
        is Response.Failure -> print(getImageFromDatabaseResponse.e)
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CircleImageProfile(currentUser: UserModel, viewModel: EditInformationViewModel, functionOpenGallery:() -> Unit){
    val valueFirstClickOnImage by viewModel.firstClickOnImage.observeAsState(initial = false)//Nombre mutable que se escribe

    val imgLinkPhotoProfile by viewModel.imgLinkUser.observeAsState(initial = currentUser.img_link)//Nombre mutable que se escribe
    val ImageUrlExist by remember { mutableStateOf(imgLinkPhotoProfile.isBlank())  }


    if (ImageUrlExist){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter){
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                FloatingActionButton(onClick = { functionOpenGallery() },
                    shape = CircleShape,
                    containerColor = GrayForTopBars,
                    modifier = Modifier
                        .size(120.dp)
                        .border(
                            BorderStroke(1.dp, GoldenOpaque),
                            CircleShape
                        )
                        .clip(CircleShape)

                ) {
                    Icon(
                        imageVector = Icons.Filled.NoPhotography,
                        contentDescription = "Edit Icon",
                        tint = GoldenOpaque,
                        modifier = Modifier
                            .fillMaxSize(0.4f)
                            .clip(CircleShape)

                    )
                }
                Spacer(modifier = Modifier.padding(2.dp))
                Text(
                    text = "Agregar Foto",
                    fontFamily = lusitanaFont,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White.copy(alpha = 0.8f),
                )
            }


        }

    }else {

        if (!valueFirstClickOnImage){
            SubcomposeAsyncImage(
                model = imgLinkPhotoProfile,
                contentDescription = "Imagen Icon",
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                loading = {
                    CircularProgressIndicator(
                        modifier = Modifier.size(15.dp),
                        color = GoldenOpaque
                    )
                },
                modifier = Modifier
                    .size(120.dp)
                    .border(
                        BorderStroke(1.dp, GoldenOpaque),
                        CircleShape
                    )
                    .clip(CircleShape)
                    .clickable() {
                        viewModel.changeValueFirstClickOnImage(valueFirstClickOnImage)
                    }
            )
        }else{
            SubcomposeAsyncImage(
                model = imgLinkPhotoProfile,
                contentDescription = "Imagen Icon",
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                loading = {
                    CircularProgressIndicator(
                        modifier = Modifier.size(5.dp),
                        color = GoldenOpaque
                    )
                },
                alpha = 0.4f,
                modifier = Modifier
                    .size(120.dp)
                    .border(
                        BorderStroke(1.dp, GoldenOpaque),
                        CircleShape
                    )
                    .clip(CircleShape)
            )

            IconButton(onClick = { functionOpenGallery() },
            modifier = Modifier.fillMaxSize(0.9f)) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit Icon",
                    tint = WhiteBone,
                    modifier = Modifier
                        .fillMaxSize(0.5f)
                        .padding(top = 10.dp, bottom = 38.dp, start = 20.dp, end = 12.dp)

                )
            }

        }


    }
}

@Composable
fun AddImageToStorageResponse(viewModel: EditInformationViewModel = hiltViewModel(), addImageToDatabase: (downloadUrl: Uri) -> Unit){
    when (val addImageToStorageResponse = viewModel.addImageToStorageResponse){
        is Response.Loading -> CircularProgressIndicator(color = GoldenOpaque)
        is Response.Success -> addImageToStorageResponse.data?.let { downloadUrl ->
            LaunchedEffect(downloadUrl){
                viewModel.addImageToDatabase(downloadUrl)
                viewModel.changeValueDialog(false)

            }
        }
        is Response.Failure -> print(addImageToStorageResponse.e)
    }

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TextFieldToChangeName(viewModel: EditInformationViewModel){
    val currentUser = HomeScreenViewModel.currentUserLogged

    val atributeRequired: String by viewModel.userName.observeAsState(currentUser!!.names)//Nombre mutable que se escribe
    val valueToEditButton: Boolean by viewModel.editInformationOption1.observeAsState(initial = true)

    val labelText = "Nombre"
    val placeholderText = "Ingresa Un Nuevo Nombre"


    TextField(
        value = atributeRequired, //Atributo que se requiere para el cambio
        onValueChange = { viewModel.onTextFieldChange(it) },
        label = { Text(text = labelText, fontFamily = lusitanaFont) },
        placeholder = { Text(text = placeholderText, fontFamily = lusitanaFont) },
        colors = ExposedDropdownMenuDefaults.textFieldColors(
            focusedContainerColor = GrayDark.copy(alpha = 0.9f),
            unfocusedContainerColor = GrayDark.copy(alpha = 0.9f),
            focusedTextColor = WhiteBone,
            unfocusedTextColor = WhiteBone,
            focusedIndicatorColor = GoldenOpaque,
            unfocusedIndicatorColor = GoldenOpaque,
            cursorColor = GoldenOpaque,
            focusedLabelColor = GoldenOpaque,
            unfocusedLabelColor = WhiteBone,
            focusedPlaceholderColor = WhiteBone,
            unfocusedPlaceholderColor = WhiteBone
        ),
        readOnly = valueToEditButton,
        trailingIcon = {
            val image =
                if (valueToEditButton)
                    Icons.Default.Edit
                else Icons.Default.EditOff

            IconButton(onClick = { viewModel.changeValueEditInfo1(valueToEditButton) }) {
                Icon(
                    imageVector = image,
                    contentDescription = "",
                    tint = WhiteBone
                )
            }
        },
        modifier = Modifier
            .padding(horizontal = 25.dp)
            .fillMaxWidth()
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TextFieldToViewInformation(labelText: String, placeholderText: String, userInfo: String){

    TextField(
        value = userInfo, //Atributo que se requiere para el cambio
        onValueChange = {  },
        label = { Text(text = labelText, fontFamily = lusitanaFont) },
        placeholder = { Text(text = placeholderText, fontFamily = lusitanaFont) },
        colors = ExposedDropdownMenuDefaults.textFieldColors(
            focusedContainerColor = GrayDark.copy(alpha = 0.9f),
            unfocusedContainerColor = GrayDark.copy(alpha = 0.9f),
            focusedTextColor = WhiteBone,
            unfocusedTextColor = WhiteBone,
            focusedIndicatorColor = GoldenOpaque,
            unfocusedIndicatorColor = GoldenOpaque,
            cursorColor = GoldenOpaque,
            focusedLabelColor = GoldenOpaque,
            unfocusedLabelColor = WhiteBone,
            focusedPlaceholderColor = WhiteBone,
            unfocusedPlaceholderColor = WhiteBone
        ),
        readOnly = true,
        modifier = Modifier
            .padding(horizontal = 25.dp)
            .fillMaxWidth()
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TextFieldToChangeNumber(viewModel: EditInformationViewModel){
    val currentUser = HomeScreenViewModel.currentUserLogged

    val atributeRequired: String by viewModel.userNumber.observeAsState(initial = currentUser!!.phone)//Nombre mutable que se escribe
    val valueToEditButton: Boolean by viewModel.editInformationOption2.observeAsState(initial = true)
    var isValidNumber by remember { mutableStateOf(false) }


    val labelText = "Numero"
    val placeholderText = "Ingresa Un Nuevo Numero"


    TextField(
        value = atributeRequired, //Atributo que se requiere para el cambio
        onValueChange = { viewModel.onTextFieldChangeForNumber(it)
            isValidNumber = !(it.isBlank() || it.length > 6 && viewModel.isValidOnlyNumbers(it))
        },
        label = { Text(text = labelText, fontFamily = lusitanaFont) },
        placeholder = { Text(text = placeholderText, fontFamily = lusitanaFont) },
        colors = ExposedDropdownMenuDefaults.textFieldColors(
            focusedContainerColor = GrayDark.copy(alpha = 0.9f),
            unfocusedContainerColor = GrayDark.copy(alpha = 0.9f),
            focusedTextColor = WhiteBone,
            unfocusedTextColor = WhiteBone,
            focusedIndicatorColor = GoldenOpaque,
            unfocusedIndicatorColor = GoldenOpaque,
            cursorColor = GoldenOpaque,
            focusedLabelColor = GoldenOpaque,
            unfocusedLabelColor = WhiteBone,
            focusedPlaceholderColor = WhiteBone,
            unfocusedPlaceholderColor = WhiteBone,
            //Colores para los errores
            errorContainerColor = GrayDark.copy(alpha = 0.9f),
            errorTextColor = WhiteBone,
            errorCursorColor = RedCarmesi,
            errorLabelColor = RedCarmesi
        ),
        isError = isValidNumber,
        supportingText = {
            if(isValidNumber){
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Debe de ser un numero valido",
                    fontFamily = lusitanaFont,
                    textAlign = TextAlign.End,
                )
            }
        },
        readOnly = valueToEditButton,
        trailingIcon = {
            val image =
                if (valueToEditButton)
                    Icons.Default.Edit
                else Icons.Default.EditOff

            IconButton(onClick = { viewModel.changeValueEditInfo2(valueToEditButton) }) {
                Icon(
                    imageVector = image,
                    contentDescription = "",
                    tint = WhiteBone
                )
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.NumberPassword
        ),
        modifier = Modifier
            .padding(horizontal = 25.dp)
            .fillMaxWidth()
    )
}

@SuppressLint("RememberReturnType")
@Composable
fun AlertDialogForUpdateImage(stateDialog: Boolean, funForEvery:() -> Unit) {

    if (stateDialog) {
        AlertDialog(
            onDismissRequest = {
                funForEvery()
            },
            title = {
                Text(
                    text = "Foto Actualizada",
                    fontFamily = lusitanaBoldFont,
                    fontSize = 22.sp,
                    overflow = TextOverflow.Ellipsis,
                    color = WhiteBone,
                    fontWeight = FontWeight.Bold,
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Tu foto de perfil se ha actualizado",
                        fontFamily = lusitanaFont,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        color = WhiteBone,
                    )

                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        funForEvery()
                    }
                ) {
                    Text(
                        text = "Continuar",
                        fontFamily = lusitanaFont,
                        fontSize = 16.sp,
                        overflow = TextOverflow.Ellipsis,
                        color = GoldenOpaque,
                    )
                }
            },
            icon = {
                Icon(
                    Icons.Filled.TaskAlt,
                    contentDescription = "Quest",
                    tint = GoldenOpaque,
                    modifier = Modifier.fillMaxSize(0.2f)
                )
            },
            containerColor = GrayForTopBars,
            shape = RoundedCornerShape(10.dp),
            tonalElevation = 10.dp
        )
    }
}



