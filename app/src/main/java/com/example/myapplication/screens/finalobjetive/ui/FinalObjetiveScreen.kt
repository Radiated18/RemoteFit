package com.example.myapplication.screens.finalobjetive.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.models.FinalObjetiveForUser
import com.example.myapplication.screens.finalobjetive.FinalObjetiveViewModel
import com.example.myapplication.screens.goalLogs.ui.TextFieldOnlyForVisualRecordRegister
import com.example.myapplication.screens.homescreen.HomeScreenViewModel
import com.example.myapplication.screens.monitoringRegister.ui.TextFieldNumberMonitoring
import com.example.myapplication.screens.monitoringRegister.ui.TextFieldOnlyForVisual
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

@Composable
fun FinalObjetiveScreen(navController: NavController, viewModel: FinalObjetiveViewModel){
    val currentUser = HomeScreenViewModel.currentUserLogged
    viewModel.getFinalObjetive()
    val snackbarHostState = remember { SnackbarHostState() }
    val finalObjetiveForUser by viewModel.objetiveUserGet.observeAsState(initial = null)

    val enabledButtonPost by viewModel.validComponentCreate.observeAsState(initial = false)
    val enableUpdateDialog: Boolean by viewModel.enableUpdateDialog.observeAsState(initial = false)//Nombre mutable que se escribe

    AlertDialogSampleUploadFinalObjetive(enableUpdateDialog,
        funForEvery = {
            navController.navigate(ProjectScreens.GoalsSettingsScreen.name)
        }
    )


    Scaffold(
        topBar = { TopAppBarBackWithIconInRight(navController, currentUser!!, "Objetivo") },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { innerPaddin ->

            Box(modifier = Modifier
                .padding(innerPaddin)
                .fillMaxSize()){

                ImageBackgroundAllAppScreen()
                Spacer(modifier = Modifier.padding(10.dp))

                Column(modifier = Modifier.padding(horizontal = 20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    if (finalObjetiveForUser !== null){
                        Spacer(modifier = Modifier.padding(10.dp))

                        CardShowFinalObjetiveInfo(finalObjetiveForUser!!)

                    }else{
                        ColumnWithTextFields(viewModel){
                            CreateRecordButton(enabledButtonPost){
                                viewModel.postDataNewRecord()
                                viewModel.changeValueDialog(enableUpdateDialog)
                            }
                        }
                    }

                }

            }
        },
        bottomBar = { BottomNavComponent(navController) }

    )
}

@Composable
fun CardShowFinalObjetiveInfo(item: FinalObjetiveForUser) {
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = GrayForTopBars,
        ),
        modifier = Modifier
            .fillMaxHeight(0.75f)
            .fillMaxWidth(1f)

    ) {
        Box(modifier = Modifier.fillMaxSize()){
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TextForRegisterGoalDate(item.date_register)
                Spacer(modifier = Modifier.padding(5.dp))

                Row() {
                    TextFieldOnlyForVisualRecordRegister(item.weigh_register.toString(), "Peso", "Kg",
                        Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth(0.475f))

                    TextFieldOnlyForVisualRecordRegister(item.per_neck.toString(), "P. Cuello", "cm",
                        Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth(1f))
                }
                Spacer(modifier = Modifier.padding(10.dp))

                Row() {
                    TextFieldOnlyForVisualRecordRegister(item.per_adb.toString(), "P. Cintura", "cm",
                        Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth(0.475f))

                    TextFieldOnlyForVisualRecordRegister(item.per_hip.toString(), "P. Cadera", "cm",
                        Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth(1f))
                }
                Spacer(modifier = Modifier.padding(10.dp))

                Row() {
                    TextFieldOnlyForVisualRecordRegister(item.index_perc_bfat.toString(), "Porcentaje de Grasa Corporal", "%",
                        Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth(0.475f))

                    TextFieldOnlyForVisualRecordRegister(item.index_imc.toString(), "Masa Corporal", "",
                        Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth(1f))
                }
                Spacer(modifier = Modifier.padding(10.dp))

                Row() {
                    TextFieldOnlyForVisualRecordRegister(item.index_imm.toString(), "Masa Magra", "",
                        Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth(0.475f))

                    TextFieldOnlyForVisualRecordRegister(item.index_ica.toString(), "Cintura - Altura", "",
                        Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth(1f))
                }

                Spacer(modifier = Modifier.padding(10.dp))



            }
        }
    }

}

@Composable
fun TextForRegisterGoalDate(String: String){
    Text(
        text = "Objetivo para: $String",
        fontFamily = lusitanaFont,
        color = WhiteBone,
        fontSize = 20.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(top = 12.dp)
    )
}

@Composable
fun ColumnWithTextFields(viewModel: FinalObjetiveViewModel, function: @Composable () -> Unit) {
    //Persona
    val currentUser = HomeScreenViewModel.currentUserLogged

    //Variables declaratorias
    val weightObserver by viewModel.weight.observeAsState(initial = "")
    val perAbdObserver by viewModel.per_adb.observeAsState(initial = "")
    val perHipObserver by viewModel.per_hip.observeAsState(initial = "")
    val perNeckObserver by viewModel.per_neck.observeAsState(initial = "")
    val dateRegister by viewModel.dateRegister.observeAsState(initial = "")

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
                viewModel.onTextFieldChangePersonalData(it, perAbdObserver, perHipObserver, perNeckObserver, dateRegister)
            }

            TextFieldNumberMonitoring(perAbdObserver, "Perimetro Cintura", "Cintura", "cm",
                Modifier
                    .padding(horizontal = 5.dp)
                    .fillMaxWidth(1f)){
                viewModel.onTextFieldChangePersonalData(weightObserver, it, perHipObserver, perNeckObserver, dateRegister)
            }
        }
        Spacer(modifier = Modifier.padding(10.dp))

        Row() {
            TextFieldNumberMonitoring(perHipObserver, "Perimetro Cadera", "Cadera", "cm",
                Modifier
                    .padding(horizontal = 5.dp)
                    .fillMaxWidth(0.475f)){
                viewModel.onTextFieldChangePersonalData(weightObserver, perAbdObserver, it, perNeckObserver, dateRegister)
            }


            TextFieldNumberMonitoring(perNeckObserver, "Perimetro Cuello", "Cuello", "cm",
                Modifier
                    .padding(horizontal = 5.dp)
                    .fillMaxWidth(1f)){
                viewModel.onTextFieldChangePersonalData(weightObserver, perAbdObserver, perHipObserver, it, dateRegister)
            }
        }
        Spacer(modifier = Modifier.padding(10.dp))


        com.example.myapplication.screens.monitoringRegister.ui.TextForShowData()

        Spacer(modifier = Modifier.padding(10.dp))

        //TextField de Observadores
        DateRegisterTextField(viewModel, "Fecha Estimada", "Fecha Estimada"){
            viewModel.onTextFieldChangePersonalData(weightObserver, perAbdObserver, perHipObserver, perNeckObserver, it)

        }
        Spacer(modifier = Modifier.padding(10.dp))


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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRegisterTextField(
    viewModel: FinalObjetiveViewModel,
    labelText: String,
    placeholderText: String,
    onTextFieldChanged: (String) -> Unit
) {

    val dateDifference by viewModel.dateRegister.observeAsState(initial = "")


    TextField(
        value = dateDifference,
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
            unfocusedPlaceholderColor = WhiteBone
        ),
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "",
                tint = WhiteBone
            )

        },
        readOnly = true,
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .fillMaxWidth()
    )

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
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .padding(horizontal = 40.dp)
            .fillMaxWidth()
            .height(50.dp)

    ) {
        Text(
            text = "Establecer Meta",
            fontFamily = lusitanaBoldFont,
            color = WhiteBone,
            fontSize = 18.sp
        )
    }
}

@SuppressLint("RememberReturnType")
@Composable
fun AlertDialogSampleUploadFinalObjetive(stateDialog: Boolean, funForEvery:() -> Unit) {

    if (stateDialog) {
        AlertDialog(
            onDismissRequest = {
                funForEvery()
            },
            title = {
                Text(
                    text = "Meta Establecida!!",
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
                        text = "Tu meta ha sido establecida correctamente",
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
