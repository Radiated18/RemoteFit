package com.example.myapplication.screens.monitoringRegister.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.screens.homescreen.HomeScreenViewModel
import com.example.myapplication.screens.monitoringRegister.MonitoringRegisterViewModel
import com.example.myapplication.screens.navigation.BottomNavComponent
import com.example.myapplication.screens.navigation.ImageBackgroundAllAppScreen
import com.example.myapplication.screens.navigation.ProjectScreens
import com.example.myapplication.screens.navigation.TopAppBarBackWithIconInRight
import com.example.myapplication.ui.theme.GoldenOpaque
import com.example.myapplication.ui.theme.GrayDark
import com.example.myapplication.ui.theme.GrayForTopBars
import com.example.myapplication.ui.theme.WhiteBone
import com.example.myapplication.ui.theme.lusitanaBoldFont
import com.example.myapplication.ui.theme.lusitanaFont
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId

@Composable
fun MonitoringRegisterScreen(navController: NavController, viewModel: MonitoringRegisterViewModel){
    val currentUser = HomeScreenViewModel.currentUserLogged
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    //Variable para el boton
    val enabledButtonPost by viewModel.validComponentCreate.observeAsState(initial = false)
    val enableUpdateDialog: Boolean by viewModel.enableUpdateDialog.observeAsState(initial = false)//Nombre mutable que se escribe

    AlertDialogSampleUploadInformation(enableUpdateDialog,
        funForEvery = {
            navController.navigate(ProjectScreens.GoalsSettingsScreen.name)
        }
    )

    Scaffold(
        topBar = { TopAppBarBackWithIconInRight(navController, currentUser!!, "Registro") },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { innerPaddin ->

            Box(modifier = Modifier
                .padding(innerPaddin)
                .fillMaxSize()){

                ImageBackgroundAllAppScreen()
                Spacer(modifier = Modifier.padding(10.dp))

                ColumnWithTextFields(viewModel){
                    CreateRecordButton(enabledButtonPost){
                        viewModel.postDataNewRecord()
                        viewModel.changeValueDialog(enableUpdateDialog)
                    }
                }

                Spacer(modifier = Modifier.padding(innerPaddin))
            }
        },
        bottomBar = { BottomNavComponent(navController) }
    )
}

@Composable
fun ColumnWithTextFields(viewModel: MonitoringRegisterViewModel,  function: @Composable () -> Unit){
    //Persona
    val currentUser = HomeScreenViewModel.currentUserLogged

    //Fecha Actual
    val localDate = Instant.ofEpochMilli(Instant.now().toEpochMilli()).atZone(ZoneId.of("UTC")).toLocalDate()
    val dateShow = "${localDate.dayOfMonth}/${localDate.monthValue}/${localDate.year}"

    //Variables declaratorias
    val weightObserver by viewModel.weight.observeAsState(initial = "")
    val perAbdObserver by viewModel.per_adb.observeAsState(initial = "")
    val perHipObserver by viewModel.per_hip.observeAsState(initial = "")
    val perNeckObserver by viewModel.per_neck.observeAsState(initial = "")

    //Variables de observacion
    val indexImcObs by viewModel.index_imc.observeAsState(initial = "")
    val indexImmObs by viewModel.index_imm.observeAsState(initial = "")
    val indexIcaObs by viewModel.index_ica.observeAsState(initial = "")
    val indexPercBFatObs by viewModel.index_perc_bfat.observeAsState(initial = "")




    Column(modifier = Modifier.padding(horizontal = 20.dp), horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(modifier = Modifier.padding(10.dp))

        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            TextFieldNumberMonitoring(weightObserver, "Peso", "Peso", "Kg",
                Modifier
                    .padding(horizontal = 5.dp)
                    .fillMaxWidth(0.475f)){
                viewModel.onTextFieldChangePersonalData(it, perAbdObserver, perHipObserver, perNeckObserver)
            }

            TextFieldNumberMonitoring(perAbdObserver, "Perimetro Cintura", "Cintura", "cm",
                Modifier
                    .padding(horizontal = 5.dp)
                    .fillMaxWidth(1f)){
                viewModel.onTextFieldChangePersonalData(weightObserver, it, perHipObserver, perNeckObserver)
            }
        }
        Spacer(modifier = Modifier.padding(10.dp))

        Row() {
            TextFieldNumberMonitoring(perHipObserver, "Perimetro Cadera", "Cadera", "cm",
                Modifier
                    .padding(horizontal = 5.dp)
                    .fillMaxWidth(0.475f)){
                viewModel.onTextFieldChangePersonalData(weightObserver, perAbdObserver, it, perNeckObserver)
            }


            TextFieldNumberMonitoring(perNeckObserver, "Perimetro Cuello", "Cuello", "cm",
                Modifier
                    .padding(horizontal = 5.dp)
                    .fillMaxWidth(1f)){
                viewModel.onTextFieldChangePersonalData(weightObserver, perAbdObserver, perHipObserver, it)
            }
        }
        Spacer(modifier = Modifier.padding(10.dp))

        Row() {
            TextFieldOnlyForVisual(currentUser!!.height.toString(), "Altura", "", "",
                Modifier
                    .padding(horizontal = 5.dp)
                    .fillMaxWidth(0.475f))

            TextFieldOnlyForVisual(dateShow, "Fecha", "", "",
                Modifier
                    .padding(horizontal = 5.dp)
                    .fillMaxWidth(1f))
        }

        Spacer(modifier = Modifier.padding(10.dp))

        TextForShowData()

        Spacer(modifier = Modifier.padding(10.dp))

        //TextField de Observadores
        Row() {
            TextFieldOnlyForVisual(indexImcObs.toString(), "I. Masa Corporal", "", "",
                Modifier
                    .padding(horizontal = 5.dp)
                    .fillMaxWidth(0.475f))

            TextFieldOnlyForVisual(indexImmObs.toString(), "I. Masa Magro", "", "",
                Modifier
                    .padding(horizontal = 5.dp)
                    .fillMaxWidth(1f))
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Row() {
            TextFieldOnlyForVisual(indexIcaObs.toString(), "I. Cintura - Altura", "", "",
                Modifier
                    .padding(horizontal = 5.dp)
                    .fillMaxWidth(0.475f))

            TextFieldOnlyForVisual(indexPercBFatObs.toString(), "% de Grasa", "", "%",
                Modifier
                    .padding(horizontal = 5.dp)
                    .fillMaxWidth(1f))
        }

        Spacer(modifier = Modifier.padding(20.dp))

        function()

    }

}

@Composable
fun CreateRecordButton(enabled: Boolean, function: () -> Unit){
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
            .padding(horizontal = 50.dp)
            .fillMaxWidth()
            .height(45.dp)

    ) {
        Text(
            text = "Crear Registro",
            fontFamily = lusitanaBoldFont,
            color = WhiteBone,
            fontSize = 18.sp
        )
    }
}

@Composable
fun TextForShowData(){
    Text(
        text ="Resultados",
        fontFamily = lusitanaFont,
        fontSize = 20.sp,
        overflow = TextOverflow.Clip,
        textAlign = TextAlign.Center,
        color = WhiteBone
    )
    Divider(color = GoldenOpaque, thickness = 2.dp, modifier = Modifier.padding(horizontal = 40.dp))

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldOnlyForVisual(atributeRequired: String,
                           labelText:String,
                           placeholderText: String,
                           suffix: String,
                           modifier: Modifier,
                           ){

    TextField(
        value = atributeRequired, //Atributo que se requiere para el cambio
        onValueChange = { },
        label = { Text(text = labelText, fontFamily = lusitanaFont, overflow = TextOverflow.Ellipsis, maxLines = 1) },
        placeholder = { Text(text = placeholderText, fontFamily = lusitanaFont, overflow = TextOverflow.Ellipsis, maxLines = 1) },
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
            unfocusedPlaceholderColor = WhiteBone
        ),
        suffix = {
            Text(
                text = suffix,
                fontFamily = lusitanaFont,
                color = WhiteBone,
                textAlign = TextAlign.Start,
            )

        },
        maxLines = 1,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldNumberMonitoring(atributeRequired: String,
                              labelText:String,
                              placeholderText: String,
                              suffix: String,
                              modifier: Modifier,
                              onTextFieldChanged: (String) -> Unit){
    TextField(
        value = atributeRequired, //Atributo que se requiere para el cambio
        onValueChange = { onTextFieldChanged(it) },
        label = { Text(text = labelText, fontFamily = lusitanaFont, overflow = TextOverflow.Ellipsis, maxLines = 1) },
        placeholder = { Text(text = placeholderText, fontFamily = lusitanaFont, overflow = TextOverflow.Ellipsis, maxLines = 1) },
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
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.NumberPassword
        ),
        suffix = {
            Text(
                text = suffix,
                fontFamily = lusitanaFont,
                color = WhiteBone,
                textAlign = TextAlign.Start,
            )

        },
        maxLines = 1,
        modifier = modifier
    )
}

@SuppressLint("RememberReturnType")
@Composable
fun AlertDialogSampleUploadInformation(stateDialog: Boolean, funForEvery:() -> Unit) {

    if (stateDialog) {
        AlertDialog(
            onDismissRequest = {
                funForEvery()
            },
            title = {
                Text(
                    text = "Registro Exitoso!!",
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
                        text = "Se ha cargado correctamente tu registro",
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
                    Icons.Filled.SentimentVerySatisfied,
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