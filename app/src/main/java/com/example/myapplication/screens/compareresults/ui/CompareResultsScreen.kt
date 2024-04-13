package com.example.myapplication.screens.compareresults.ui

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SentimentNeutral
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
import com.example.myapplication.screens.compareresults.CompareResultsViewModel
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
fun CompareResultsScreen(navController: NavController, viewModel: CompareResultsViewModel){
    val snackbarHostState = remember { SnackbarHostState() }
    val currentUser = HomeScreenViewModel.currentUserLogged

    viewModel.getFinalObjetive()
    val finalObjetiveForUser by viewModel.objetiveUserGet.observeAsState(initial = null)
    val enableUpdateDialog: Boolean by viewModel.enableUpdateDialog.observeAsState(initial = false)//Nombre mutable que se escribe
    val finalObjetiveReachedDialog: Boolean by viewModel.finalObjetiveReachedDialog.observeAsState(initial = false)//Nombre mutable que se escribe

    val valueTypeComparacion: Int by viewModel.valueTypeComparacion.observeAsState(initial = 0)//Nombre mutable que se escribe
    val enabledButtonPost by viewModel.validComponentCreate.observeAsState(initial = false)

    FinalDialogThatCompareResults(enableUpdateDialog, viewModel.getTypeOfCompareResults()){
        viewModel.changeValueDialog(true)
        viewModel.deleteFinalObjectivesForCurrentUser()
        navController.navigate(ProjectScreens.HomeScreen.name)
    }
    Scaffold(
        topBar = { TopAppBarBackWithIconInRight(navController, currentUser!!, "Comparación") },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { innerPaddin ->

            Box(modifier = Modifier
                .padding(innerPaddin)
                .fillMaxSize()){

                ImageBackgroundAllAppScreen()
                Spacer(modifier = Modifier.padding(10.dp))

                LazyColumn(horizontalAlignment = Alignment.CenterHorizontally){
                    item{
                        TextForDescribeBothTypes("Datos Actuales")
                        ColumnWithTextFields(viewModel){

                        }
                    }

                    item{
                        TextForDescribeBothTypes("Datos Objetivos")
                        Spacer(modifier = Modifier.padding(10.dp))

                        finalObjetiveForUser?.let { CardShowFinalObjetiveInfo(it) }
                    }

                    item {
                        Spacer(modifier = Modifier.padding(20.dp))
                    }

                    item {
                        CompareRecordButton(enabledButtonPost){
                            viewModel.changeValueDialog(false)
                            viewModel.getTypeOfCompareResults()
                        }
                        Spacer(modifier = Modifier.padding(10.dp))

                    }
                }

            }
        },
        bottomBar = { BottomNavComponent(navController) }

    )

}

@Composable
fun FinalDialogThatCompareResults(stateDialog: Boolean, typeCompare:Int, funForEvery:() -> Unit) {

    val valueMainText = when(typeCompare){
        0 -> "Felicitaciones"
        1 -> "Por poco"
        2 -> "No te desanimes"
        3 -> "Sigue intentando"
        else -> "?"
    }

    val valueDescriptionText = when(typeCompare){
        0 -> "En hora buena, has alcanzado conseguir tu meta.\n\nEstamos orgullos por tu nuevo logro."
        1 -> "Has hecho un gran avance, casi lo consigues.\n\nVas por buen camino"
        2 -> "Por lo menos lo intentaste.\n\nA la proxima seguro lo logras"
        3 -> "Bueno.\n\nIntentarlo no es pecado"
        else -> "?"
    }

    val valueIcon = when(typeCompare){
        0 -> Icons.Filled.SentimentVerySatisfied
        1 -> Icons.Filled.SentimentSatisfied
        2 -> Icons.Filled.SentimentNeutral
        3 -> Icons.Filled.SentimentDissatisfied
        else -> Icons.Filled.SentimentVeryDissatisfied
    }

    if (stateDialog) {
        AlertDialog(
            onDismissRequest = {
                funForEvery()
            },
            title = {
                Text(
                    text = valueMainText,
                    fontFamily = lusitanaBoldFont,
                    fontSize = 22.sp,
                    overflow = TextOverflow.Ellipsis,
                    color = WhiteBone,
                    fontWeight = FontWeight.Bold,
                )
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = valueDescriptionText,
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
                    valueIcon,
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
fun CompareRecordButton(enabled: Boolean, function: () -> Unit){
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
            text = "Comparar",
            fontFamily = lusitanaBoldFont,
            color = WhiteBone,
            fontSize = 18.sp
        )
    }
}


@Composable
fun ColumnWithTextFields(viewModel: CompareResultsViewModel, function: @Composable () -> Unit) {
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

        Spacer(modifier = Modifier.padding(15.dp))

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

    }

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
            .padding(horizontal = 20.dp)

    ) {
        Box(modifier = Modifier.fillMaxSize()){
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                com.example.myapplication.screens.finalobjetive.ui.TextForRegisterGoalDate(item.date_register)
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
fun TextForDescribeBothTypes(String: String) {
    Text(
        text = String,
        fontFamily = lusitanaFont,
        color = WhiteBone,
        fontSize = 22.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(top = 12.dp, bottom = 2.dp)
    )
    Divider(thickness = 2.dp, color = GoldenOpaque, modifier = Modifier.padding(horizontal = 5.dp))
}
