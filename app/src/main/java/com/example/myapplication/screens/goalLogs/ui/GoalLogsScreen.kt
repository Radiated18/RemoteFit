package com.example.myapplication.screens.goalLogs.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.models.ActualRecordRegistration
import com.example.myapplication.screens.goalLogs.GoalLogsViewModel
import com.example.myapplication.screens.homescreen.HomeScreenViewModel
import com.example.myapplication.screens.navigation.BottomNavComponent
import com.example.myapplication.screens.navigation.ImageBackgroundAllAppScreen
import com.example.myapplication.screens.navigation.TopAppBarBackWithIconInRight
import com.example.myapplication.ui.theme.GoldenOpaque
import com.example.myapplication.ui.theme.GrayDark
import com.example.myapplication.ui.theme.GrayForTopBars
import com.example.myapplication.ui.theme.WhiteBone
import com.example.myapplication.ui.theme.lusitanaFont

@Composable
fun GoalLogsScreen(navController: NavController, viewModel: GoalLogsViewModel){
    viewModel.getHistoryRecords()
    val currentUser = HomeScreenViewModel.currentUserLogged
    val snackbarHostState = remember { SnackbarHostState() }

    val historyRecords: List<ActualRecordRegistration> by viewModel.historyRecordRegistered.observeAsState(initial = mutableListOf())//Nombre mutable que se escribe

    Log.d("Lista", historyRecords.toString())

    Scaffold(
        topBar = { TopAppBarBackWithIconInRight(navController, currentUser!!, "Historial") },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { innerPaddin ->

            Box(modifier = Modifier
                .padding(innerPaddin)
                .fillMaxSize()){

                ImageBackgroundAllAppScreen()
                Spacer(modifier = Modifier.padding(10.dp))

                Column(modifier = Modifier.padding(horizontal = 20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    if (historyRecords.isEmpty()){
                        TextWhereIsNoRegister()
                    }else{
                        TextForShowData()
                        LazyColumnForRecords(historyRecords)
                    }

                }

            }
        },
        bottomBar = { BottomNavComponent(navController) }

    )
}

@Composable
fun TextForShowData(){
    Text(
        text ="Tus Registros",
        fontFamily = lusitanaFont,
        fontSize = 24.sp,
        overflow = TextOverflow.Clip,
        textAlign = TextAlign.Center,
        color = WhiteBone,
        modifier = Modifier.padding(top = 10.dp, bottom = 4.dp)
    )
    Divider(color = GoldenOpaque, thickness = 1.dp, modifier = Modifier.padding(start = 40.dp, end = 40.dp, bottom = 10.dp))
}

@Composable
fun TextWhereIsNoRegister(){
    Text(
        text ="No tienes registros",
        fontFamily = lusitanaFont,
        fontSize = 24.sp,
        overflow = TextOverflow.Clip,
        textAlign = TextAlign.Center,
        color = WhiteBone,
        modifier = Modifier.padding(top = 10.dp, bottom = 4.dp)
    )
}

@Composable
fun LazyColumnForRecords(historyRecords: List<ActualRecordRegistration>){
    LazyColumn(userScrollEnabled = true){

        items(historyRecords) { item ->
            CardForRecordRegistration(item)
            Spacer(modifier = Modifier.padding(10.dp))

        }
    }
}

@Composable
fun CardForRecordRegistration(item: ActualRecordRegistration) {
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = GrayForTopBars,
            ),
        modifier = Modifier
            .fillMaxHeight(0.95f)
            .fillMaxWidth()

    ) {
        Box(modifier = Modifier.fillMaxSize()){
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TextForRegisterDate(item.date_register)
                Spacer(modifier = Modifier.padding(5.dp))

                Row() {
                    TextFieldOnlyForVisualRecordRegister(item.weigh_register.toString(), "Peso", "Kg",
                        Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth(0.475f))

                    TextFieldOnlyForVisualRecordRegister(item.height_register.toString(), "Altura", "cm",
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
                    TextFieldOnlyForVisualRecordRegister(item.per_neck.toString(), "P. Cuello", "cm",
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


                TextFieldOnlyForVisualRecordRegister(item.index_perc_bfat.toString(), "Porcentaje de Grasa Corporal", "%",
                    Modifier
                        .padding(horizontal = 15.dp)
                        .fillMaxWidth(0.9f))

                Spacer(modifier = Modifier.padding(10.dp))
            }
        }
    }
}

@Composable
fun TextForRegisterDate(String: String){
    Text(
        text = "Fecha de Registro $String",
        fontFamily = lusitanaFont,
        color = WhiteBone,
        fontSize = 20.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(top = 12.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldOnlyForVisualRecordRegister(atributeRequired: String,
                           labelText:String,
                           suffix: String,
                           modifier: Modifier,
) {

    TextField(
        value = atributeRequired,
        onValueChange = { },
        label = {
            Text(
                text = labelText,
                fontFamily = lusitanaFont,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                fontSize = 14.sp
            )
        },

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
