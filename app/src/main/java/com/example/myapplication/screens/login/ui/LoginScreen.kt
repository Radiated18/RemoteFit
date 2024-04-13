package com.example.myapplication.screens.login.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.textFieldColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.screens.login.LoginScreenViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.screens.finalobjetive.ui.AlertDialogSampleUploadFinalObjetive
import com.example.myapplication.screens.navigation.ProjectScreens
import com.example.myapplication.ui.theme.GoldenOpaque
import com.example.myapplication.ui.theme.GrayDark
import com.example.myapplication.ui.theme.GrayForTopBars
import com.example.myapplication.ui.theme.Purple40
import com.example.myapplication.ui.theme.WhiteBone
import com.example.myapplication.ui.theme.lusitanaBoldFont
import com.example.myapplication.ui.theme.lusitanaFont

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginScreenViewModel){
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        ImageBackgroundLoginScreen()
        LoginComponent(viewModel, navController)

    }
}

@Composable
fun LoginComponent(viewModel: LoginScreenViewModel, navController: NavController){
    //Variables para cada Elemento
    val username: String by viewModel.username.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")
    val loginEnable: Boolean by viewModel.loginEnable.observeAsState(initial = false)
    val messageAuth: String by viewModel.messageAuth.observeAsState(initial = "") //Mensaje para Inicio de sesion
    val context = LocalContext.current // El contexto Actual para mostrar los mensajes emegerntes

    val failedLoginDialog: Boolean by viewModel.failedLoginDialog.observeAsState(initial = false)//Nombre mutable que se escribe

    AlertDialogForBadCredentials(failedLoginDialog,
        funForEvery = {
            viewModel.changeValueDialog(failedLoginDialog)
        }
    )
    //Parte Grafica
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WelcomeText()
        Spacer(modifier = Modifier.padding(25.dp))

        UsernameField(username){ viewModel.onLoginChange(it, password) }
        
        Spacer(modifier = Modifier.padding(15.dp))
        
        PasswordField(password, viewModel){ viewModel.onLoginChange(username, it) }

        Spacer(modifier = Modifier.padding(5.dp))

        Text(text = "¿Olvidaste tu Contraseña?",
            fontFamily = lusitanaFont,
            color = Color.White,
            fontSize = 16.sp,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable {
                navController.navigate(ProjectScreens.RecoverPasswordScreen.name)
            }
        )
        Spacer(modifier = Modifier.padding(25.dp))

        BotonLoguearse(loginEnable) {
            viewModel.signInWithEmailAndPassword(username, password){

                navController.navigate(ProjectScreens.HomeScreen.name)
            }
        }
        Spacer(modifier = Modifier.padding(4.dp))

        RegisterText(navController = navController)

    }

}

@Composable
fun RegisterText(navController: NavController){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "¿No tienes cuenta?",
            fontFamily = lusitanaFont,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = WhiteBone,
        )

        Text(text = "Registrate aqui",
            fontFamily = lusitanaFont,
            color = Color.White,
            fontSize = 16.sp,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable {
                navController.navigate(ProjectScreens.RegisterScreen.name)
            }
        )
    }

}


@Composable
fun WelcomeText(){
    Text(
        text = "Bienvenido",
        fontFamily = lusitanaFont,
        fontSize = 48.sp,
        textAlign = TextAlign.Center,
        color = Color.White.copy(alpha = 0.8f),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsernameField(username: String, onTextFieldChanged: (String) -> Unit){
    TextField(
        value = username,
        onValueChange = { onTextFieldChanged(it) },
        label = { Text(text = "Correo", fontFamily = lusitanaBoldFont, fontSize = 16.sp) },
        placeholder = { Text(text = "Ingresa tu correo", fontFamily = lusitanaFont, fontSize = 14.sp) },
        colors = textFieldColors(
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
        maxLines = 1,
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .padding(horizontal = 50.dp)
            .fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(password: String, viewModel: LoginScreenViewModel, onTextFieldChanged: (String) -> Unit){
    //Variables
    val passwordVisible: Boolean by viewModel.passwordVisible.observeAsState(initial = false)

    //Para Cambio Visual
    val visualTransformation = if (passwordVisible)
        VisualTransformation.None
    else PasswordVisualTransformation()

    TextField(
        value = password,
        onValueChange = { onTextFieldChanged(it) },
        label = { Text(text = "Contraseña", fontFamily = lusitanaBoldFont, fontSize = 16.sp) },
        placeholder = { Text(text = "Ingresa tu contraseña", fontFamily = lusitanaFont, fontSize = 14.sp) },
        visualTransformation = visualTransformation,
        colors = textFieldColors(
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
        trailingIcon = {
            if(password.isNotEmpty()){
                PasswordVisibleIcon(passwordVisible, viewModel)
            }
        },
        maxLines = 1,
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .padding(horizontal = 50.dp)
            .fillMaxWidth()
    )
}

@Composable
fun BotonLoguearse(loginEnable: Boolean, function: () -> Unit){
    Button(
        onClick = {
            function()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = GoldenOpaque,
            disabledContainerColor = Color(0xFF73777F)
        ),
        enabled = loginEnable,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(horizontal = 80.dp)
            .fillMaxWidth()
            .height(45.dp)
    ) {
        Text(
            text = "Iniciar sesion",
            fontFamily = lusitanaFont,
            color = WhiteBone,
            fontSize = 18.sp
        )
    }
}

@Composable
fun PasswordVisibleIcon(passwordVisible: Boolean, viewModel: LoginScreenViewModel) {
    val image =
        if (passwordVisible)
            Icons.Default.VisibilityOff
        else Icons.Default.Visibility
    IconButton(onClick = { viewModel.changePasswordVisible(passwordVisible) }) {
        Icon(
            imageVector = image,
            contentDescription = "",
            tint = WhiteBone
        )
    }
}

@Composable
fun ImageBackgroundLoginScreen(){
    Image(painter = painterResource(
        id = R.drawable.menusplahsbg),
        contentDescription = "Background",
        alpha = 0.85f,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    )
}

@SuppressLint("RememberReturnType")
@Composable
fun AlertDialogForBadCredentials(stateDialog: Boolean, funForEvery:() -> Unit) {

    if (stateDialog) {
        AlertDialog(
            onDismissRequest = {
                funForEvery()
            },
            title = {
                Text(
                    text = "Ingreso Fallido",
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
                        text = "Tu correo y contraseña no coinciden",
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
                    Icons.Filled.Close,
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