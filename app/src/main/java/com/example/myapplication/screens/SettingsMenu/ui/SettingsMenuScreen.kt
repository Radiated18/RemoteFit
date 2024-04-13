package com.example.myapplication.screens

import android.annotation.SuppressLint
import android.app.ProgressDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MarkEmailRead
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.models.UserModel
import com.example.myapplication.screens.SettingsMenu.SettingsMenuViewModel
import com.example.myapplication.screens.homescreen.HomeScreenViewModel
import com.example.myapplication.screens.navigation.BottomNavComponent
import com.example.myapplication.screens.navigation.ImageBackgroundAllAppScreen
import com.example.myapplication.screens.navigation.ProjectScreens
import com.example.myapplication.screens.navigation.TopAppBarHomeScreen
import com.example.myapplication.ui.theme.BlueCapri
import com.example.myapplication.ui.theme.GoldenOpaque
import com.example.myapplication.ui.theme.GrayDark
import com.example.myapplication.ui.theme.GrayForTopBars
import com.example.myapplication.ui.theme.WhiteBone
import com.example.myapplication.ui.theme.lusitanaBoldFont
import com.example.myapplication.ui.theme.lusitanaFont
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@Composable
fun SettingsMenuScreen(navController: NavController, viewModel: SettingsMenuViewModel){
    val currentUser = HomeScreenViewModel.currentUserLogged
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var loading by remember { mutableStateOf(false) }

    val enableLogoutDialog: Boolean by viewModel.enableLogoutDialog.observeAsState(initial = false)//Nombre mutable que se escribe
    val activateSendRequestDialog: Boolean by viewModel.activateSendRequestDialog.observeAsState(initial = false)//Nombre mutable que se escribe
    val valueMessageForRequestChange: Int by viewModel.typeMessageResponseDialog.observeAsState(initial = 1)//Nombre mutable que se escribe

    AlertDialogResquestChangePassword(activateSendRequestDialog, valueMessageForRequestChange){

        viewModel.changeValueSendEmailDialog(true)
    }

    AlertDialogSample(enableLogoutDialog,
            funDismiss= { viewModel.changeValueDialog(enableLogoutDialog) },
            funConfirm = {
                navController.navigate(ProjectScreens.LoginScreen.name)
                viewModel.disconectUser()
                viewModel.changeValueDialog(enableLogoutDialog)
            }
        )

    Scaffold(
        topBar = { TopAppBarHomeScreen(navController, currentUser) },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = {innerPaddin ->

            Box(modifier = Modifier
                .padding(innerPaddin)
                .fillMaxSize()){
                ImageBackgroundAllAppScreen()

                Spacer(modifier = Modifier.padding(10.dp))

                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Spacer(modifier = Modifier.padding(10.dp))
                    TextMainSettings()
                    Spacer(modifier = Modifier.padding(10.dp))
                    ItemButtonDesign("Editar Información", "Actualiza tus datos", Icons.Filled.Edit){
                        navController.navigate(ProjectScreens.EditInformationScreen.name)

                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    ItemButtonDesign("Objetivos", "Cambia tus objetivos", Icons.Filled.TrackChanges){
                        navController.navigate(ProjectScreens.GoalsSettingsScreen.name)

                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    ItemButtonDesign("Insignias", "Revisa tus logros", Icons.Filled.MilitaryTech){
                        navController.navigate(ProjectScreens.BadgeForUserScreen.name)
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    ItemButtonDesign("Contraseña", "Cambia tu contraseña", Icons.Filled.Lock){
                        viewModel.sendPasswordResetEmail(currentUser!!.email)

                        viewModel.changeValueSendEmailDialog(false)

                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    ItemButtonDesign("Cerrar Sesion", "Vuelve pronto", Icons.Filled.Logout){
                        viewModel.changeValueDialog(enableLogoutDialog)
                    }

                }

            }
        },
        bottomBar = { BottomNavComponent(navController) }
    )
}

@SuppressLint("RememberReturnType")
@Composable
fun AlertDialogSample(stateDialog: Boolean, funDismiss:() -> Unit, funConfirm:() -> Unit) {

    if (stateDialog) {
        AlertDialog(
            onDismissRequest = {
                funDismiss()
            },
            title = {
                Text(
                    text = "Cerrar Sesion",
                    fontFamily = lusitanaBoldFont,
                    fontSize = 20.sp,
                    overflow = TextOverflow.Ellipsis,
                    color = WhiteBone,
                    fontWeight = FontWeight.Bold,
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Estas a punto de cerrar sesión, ¿Estas seguro de querer salir?",
                        fontFamily = lusitanaFont,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        color = WhiteBone,
                    )

                }

            },
            confirmButton = {
                TextButton(
                    onClick = {
                        funConfirm()
                    }
                ) {
                    Text(
                        text = "Si, salir",
                        fontFamily = lusitanaFont,
                        fontSize = 14.sp,
                        overflow = TextOverflow.Ellipsis,
                        color = WhiteBone,
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        funDismiss()
                    }
                ) {
                    Text(
                        text = "Permanecer Aqui",
                        fontFamily = lusitanaFont,
                        fontSize = 14.sp,
                        overflow = TextOverflow.Ellipsis,
                        color = GoldenOpaque,
                    )
                }
            },
            icon = {
                Icon(
                    Icons.Filled.SentimentVeryDissatisfied,
                    contentDescription = "Quest",
                    tint = GoldenOpaque,
                )
            },
            containerColor = GrayForTopBars,
            shape = RoundedCornerShape(10.dp),
            tonalElevation = 10.dp
        )
    }
}


@Composable
fun ItemButtonDesign(headlineText:String, supportingText:String, leadingIcon: ImageVector, functionNavigate:() -> Unit){
    ListItem(
        headlineContent = {
            Text(
                text = headlineText,
                fontFamily = lusitanaBoldFont,
                fontSize = 20.sp,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                color = WhiteBone,
            )
        },
        supportingContent = {
            Text(
                text = supportingText,
                fontFamily = lusitanaFont,
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                color = WhiteBone,
            )
        },
        trailingContent = {
            IconButton(onClick = { functionNavigate() }) {
                Icon(
                    Icons.Filled.ArrowForwardIos,
                    contentDescription = "Edit",
                    tint = WhiteBone,
                    modifier = Modifier.fillMaxSize(0.85f)
                )
            } },
        leadingContent = {

            Icon(
                imageVector = leadingIcon,
                contentDescription = "Edit",
                tint = WhiteBone,
                modifier = Modifier.size(30.dp)
            )
        },
        colors = ListItemDefaults.colors(
            containerColor = GrayDark,
            overlineColor = WhiteBone
        )
    )

}

@Composable
fun TextMainSettings(){
    Text(
        text = "Configuración",
        fontFamily = lusitanaFont,
        fontSize = 25.sp,
        textAlign = TextAlign.Start,
        overflow = TextOverflow.Ellipsis,
        color = WhiteBone,
    )
}

@SuppressLint("RememberReturnType")
@Composable
fun AlertDialogResquestChangePassword(stateDialog: Boolean, valueMessage: Int, funForEvery:() -> Unit) {


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