package com.example.myapplication.screens.levelandfinal.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.models.SelectionOption
import com.example.myapplication.screens.finalobjetive.ui.AlertDialogSampleUploadFinalObjetive
import com.example.myapplication.screens.goalssettingsscreen.ui.ListItemGoalsSettings
import com.example.myapplication.screens.homescreen.HomeScreenViewModel
import com.example.myapplication.screens.levelandfinal.LevelAndFinalViewModel
import com.example.myapplication.screens.navigation.BottomNavComponent
import com.example.myapplication.screens.navigation.ImageBackgroundAllAppScreen
import com.example.myapplication.screens.navigation.ProjectScreens
import com.example.myapplication.screens.navigation.TopAppBarBackWithIconInRight
import com.example.myapplication.ui.theme.GoldenOpaque
import com.example.myapplication.ui.theme.GrayForTopBars
import com.example.myapplication.ui.theme.WhiteBone
import com.example.myapplication.ui.theme.lusitanaBoldFont
import com.example.myapplication.ui.theme.lusitanaFont
import kotlinx.coroutines.launch

@Composable
fun LevelAndFinalScreen(navController: NavController, viewModel: LevelAndFinalViewModel){
    val currentUser = HomeScreenViewModel.currentUserLogged
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()


    val ejercicio_ratioValue: List<SelectionOption> = viewModel.ejercicio_ratio
    val finalObjetiveValue: List<SelectionOption> = viewModel.finalObjetive

    val updatingEnable: Boolean by viewModel.enableUpdateChange.observeAsState(initial = false)
    val enableUpdateDialog: Boolean by viewModel.enableUpdateDialog.observeAsState(initial = false)//Nombre mutable que se escribe

    AlertDialogSampleUpdateChanges(enableUpdateDialog,
        funForEvery = {
            navController.navigate(ProjectScreens.GoalsSettingsScreen.name)
        }
    )


    Scaffold(
        topBar = { TopAppBarBackWithIconInRight(navController, currentUser!!, "Objetivos") },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { innerPaddin ->

            Box(modifier = Modifier
                .padding(innerPaddin)
                .fillMaxSize()){

                ImageBackgroundAllAppScreen()
                Spacer(modifier = Modifier.padding(10.dp))

                LazyColumn(modifier = Modifier.padding(horizontal = 20.dp)) {

                    item {
                        Spacer(modifier = Modifier.padding(10.dp))
                        TextShowLevelUser(currentUser!!.level_user)
                    }

                    item {
                        Spacer(modifier = Modifier.padding(10.dp))
                        TextLabelForRequestChange("Modifica la Cantidad de Entrenamiento")

                    }

                    items(ejercicio_ratioValue){ item ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(45.dp)
                                .padding(horizontal = 25.dp)
                                .clickable(
                                    true,
                                    onClick = { viewModel.selectionOptionEjercicioRatSelected(item) }),
                            color = if (item.selected) { GoldenOpaque } else { GrayForTopBars },
                            border = BorderStroke(1.dp, GoldenOpaque),
                            shape = RoundedCornerShape(4.dp)
                        ){
                            Column(horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center) {
                                Text(
                                    text = item.option,
                                    fontFamily = lusitanaFont,
                                    color = WhiteBone,
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                        Spacer(modifier = Modifier.padding(5.dp))
                    }

                    item {
                        Spacer(modifier = Modifier.padding(10.dp))
                        TextLabelForRequestChange("Modifica Tu Objetivo a Largo Plazo")

                    }

                    items(finalObjetiveValue){ item ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(45.dp)
                                .padding(horizontal = 25.dp)
                                .clickable(
                                    true,
                                    onClick = { viewModel.selectionOptionFinalObjetiveSelected(item) }),
                            color = if (item.selected) { GoldenOpaque } else { GrayForTopBars },
                            border = BorderStroke(1.dp, GoldenOpaque),
                            shape = RoundedCornerShape(4.dp)
                        ){
                            Column(horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center) {
                                Text(
                                    text = item.option,
                                    fontFamily = lusitanaFont,
                                    color = WhiteBone,
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                        Spacer(modifier = Modifier.padding(5.dp))
                    }

                    item {
                        Spacer(modifier = Modifier.padding(10.dp))

                        ButtonForUpdateChanges(updatingEnable){

                            coroutineScope.launch{
                                viewModel.updateInformationInDatabase()
                            }
                            viewModel.changeValueDialog(enableUpdateDialog)

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
fun TextShowLevelUser(headlineText: String){
    Text(
        text = "Nivel Actual: $headlineText",
        fontFamily = lusitanaBoldFont,
        fontSize = 20.sp,
        textAlign = TextAlign.Start,
        maxLines = 1,
        overflow = TextOverflow.Clip,
        color = WhiteBone,
    )
    Divider(thickness = 1.dp, color = GoldenOpaque)
}

@Composable
fun TextLabelForRequestChange(resumeQuest: String){
    Text(
        text = resumeQuest,
        fontFamily = lusitanaFont,
        color = WhiteBone,
        fontSize = 20.sp,
        textAlign = TextAlign.Center,
        lineHeight = 21.sp,
        modifier = Modifier.padding(start = 1.dp, end = 1.dp)
    )
    Spacer(modifier = Modifier.padding(8.dp))

}

@Composable
fun ButtonForUpdateChanges(enabled: Boolean, function: () -> Unit){
    Spacer(modifier = Modifier.padding(10.dp))

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
            .padding(horizontal = 60.dp)
            .fillMaxWidth()
            .height(45.dp)

    ) {
        Text(
            text = "Actualizar",
            fontFamily = lusitanaBoldFont,
            color = WhiteBone,
            fontSize = 16.sp
        )
    }
}

@SuppressLint("RememberReturnType")
@Composable
fun AlertDialogSampleUpdateChanges(stateDialog: Boolean, funForEvery:() -> Unit) {

    if (stateDialog) {
        AlertDialog(
            onDismissRequest = {
                funForEvery()
            },
            title = {
                Text(
                    text = "Cambios Realizados!!",
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
                        text = "Tus cambios han sido realizados existosamente",
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