package com.example.myapplication.screens.recoverpassword.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.MarkEmailRead
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.screens.AlertDialogResquestChangePassword
import com.example.myapplication.screens.navigation.ProjectScreens
import com.example.myapplication.screens.recoverpassword.RecoverPasswordViewModel
import com.example.myapplication.screens.register.ui.TopAppBarBack
import com.example.myapplication.ui.theme.BackGroundColor
import com.example.myapplication.ui.theme.GoldenOpaque
import com.example.myapplication.ui.theme.GrayDark
import com.example.myapplication.ui.theme.GrayForTopBars
import com.example.myapplication.ui.theme.RedCarmesi
import com.example.myapplication.ui.theme.WhiteBone
import com.example.myapplication.ui.theme.lusitanaBoldFont
import com.example.myapplication.ui.theme.lusitanaFont

@Composable
fun RecoverPasswordScreen(navController: NavController, viewModel: RecoverPasswordViewModel){
    val username: String by viewModel.username.observeAsState(initial = "")
    val buttonEnable: Boolean by viewModel.buttonEnable.observeAsState(initial = false)
    val activateSendRequestDialog: Boolean by viewModel.activateSendRequestDialog.observeAsState(initial = false)//Nombre mutable que se escribe
    val valueMessageForRequestChange: Int by viewModel.typeMessageResponseDialog.observeAsState(initial = 1)//Nombre mutable que se escribe


    AlertDialogResquestRecoverPassword(activateSendRequestDialog, valueMessageForRequestChange){
        viewModel.changeValueSendEmailDialog(true)
        navController.navigate(ProjectScreens.LoginScreen.name)
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(BackGroundColor)
    ) {
        Column() {
            TopAppBarRecover(navController)
            Spacer(modifier = Modifier.padding(10.dp))

            RecoverText()
            Spacer(modifier = Modifier.padding(10.dp))

            TextFieldCommonRegister(username, "Correo electronico", "Ingresa tu correo", buttonEnable){
                viewModel.onLoginChange(it)
            }

            Spacer(modifier = Modifier.padding(10.dp))


            SendEmailButton(buttonEnable){
                viewModel.sendPasswordResetEmail(username)
                viewModel.changeValueSendEmailDialog(activateSendRequestDialog)
            }
        }
    }
}

@Composable
fun RecoverText(){
    Text(
        text = "Ingresa tu Correo",
        fontFamily = lusitanaFont,
        fontSize = 22.sp,
        textAlign = TextAlign.Start,
        color = Color.White.copy(alpha = 0.8f),
        modifier = Modifier
            .padding(start = 15.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldCommonRegister(atributeRequired: String,
                            labelText:String,
                            placeholderText: String,
                            errorLabel: Boolean,
                            onTextFieldChanged: (String) -> Unit){
    TextField(
        value = atributeRequired, //Atributo que se requiere para el cambio
        onValueChange = { onTextFieldChanged(it) },
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
        isError = !errorLabel,
        supportingText = {
            if (!errorLabel){
                Text(text = "El correo ingresado no existe", fontFamily = lusitanaFont)
            }
        },
        enabled = true,
        modifier = Modifier
            .padding(horizontal = 25.dp)
            .fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarRecover(navController: NavController){

    TopAppBar(
        title = {
            Text(
                text ="Recuperar Contraseña",
                fontFamily = lusitanaFont,
                fontSize = 22.sp,
                overflow = TextOverflow.Clip
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.navigateUp()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Arrow Icon",
                    modifier = Modifier
                        .padding(start = 0.dp)
                )
            }

        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = GrayForTopBars,
            navigationIconContentColor = WhiteBone,
            titleContentColor = WhiteBone,
            actionIconContentColor = GrayDark
        )
    )
}


@SuppressLint("RememberReturnType")
@Composable
fun AlertDialogResquestRecoverPassword(stateDialog: Boolean, valueMessage: Int, funForEvery:() -> Unit) {


    val titleMessage =  if (valueMessage == 0) "Algo Fallo" else "Solicitud Enviada"
    val bodyMessage =  if (valueMessage == 0) "Tu solicitud no ha sido enviado\n" +
            "Intentalo mas tarde" else "Tu solicitud ha sido enviada exitosamente a tu correo"
    val iconSelected: ImageVector = if (valueMessage == 0) Icons.Filled.Error else Icons.Filled.MarkEmailRead


    if (stateDialog) {
        AlertDialog(
            onDismissRequest = {

                funForEvery()
            },
            title = {
                Text(
                    text = titleMessage,
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
                        text = bodyMessage,
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
                    iconSelected,
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
fun SendEmailButton(enabled: Boolean, function: () -> Unit){

    Button(
        onClick = {
            function()
        },
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = GoldenOpaque,
            disabledContainerColor = Color(0xFF73777F)
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(horizontal = 80.dp)
            .fillMaxWidth()
            .height(45.dp)

    ) {
        Text(
            text = "Enviar",
            fontFamily = lusitanaBoldFont,
            color = WhiteBone,
            fontSize = 18.sp
        )
    }
}

