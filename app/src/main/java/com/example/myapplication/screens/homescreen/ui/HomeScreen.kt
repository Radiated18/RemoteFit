package com.example.myapplication.screens.homescreen.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.material.icons.filled.SportsScore
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.models.UserModel
import com.example.myapplication.screens.homescreen.HomeScreenViewModel
import com.example.myapplication.screens.navigation.BottomNavComponent
import com.example.myapplication.screens.navigation.ImageBackgroundAllAppScreen
import com.example.myapplication.screens.navigation.ProjectScreens
import com.example.myapplication.screens.navigation.TopAppBarHomeScreen
import com.example.myapplication.ui.theme.GoldenOpaque
import com.example.myapplication.ui.theme.GrayForTopBars
import com.example.myapplication.ui.theme.WhiteBone
import com.example.myapplication.ui.theme.lusitanaBoldFont
import com.example.myapplication.ui.theme.lusitanaFont

@Composable
    fun HomeScreen(viewModel: HomeScreenViewModel, navController: NavController){
    viewModel.getUserLogin()
    val currentUser: UserModel? by viewModel.UserInLogin.observeAsState(initial = null)//Nombre mutable que se escribe
    val snackbarHostState = remember { SnackbarHostState() }

    viewModel.getFinalObjetive()
    val finalObjetiveForUser by viewModel.objetiveUserGet.observeAsState(initial = null)
    val enableUpdateDialog: Boolean by viewModel.enableUpdateDialog.observeAsState(initial = false)//Nombre mutable que se escribe
    val finalObjetiveReachedDialog: Boolean by viewModel.finalObjetiveReachedDialog.observeAsState(initial = false)//Nombre mutable que se escribe


    if(finalObjetiveForUser != null){
        viewModel.changeValueDialog(false)
        viewModel.isDateTodayOrFuture(finalObjetiveForUser!!.date_register)
    }else{
        viewModel.changeValueDialog(true)
    }

    AlertDialogObjetiveReached(enableUpdateDialog && finalObjetiveReachedDialog){
        navController.navigate(ProjectScreens.CompareResultsScreen.name)
        viewModel.changeValueDialog(true)
    }


    Scaffold(
        topBar = { TopAppBarHomeScreen(navController, currentUser) },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { innerPaddin ->

            Box(modifier = Modifier
                .padding(innerPaddin)
                .fillMaxSize()){
                ImageBackgroundAllAppScreen()
                Spacer(modifier = Modifier.padding(10.dp))

                LazyColumn(content = {
                    item {
                        Spacer(modifier = Modifier.padding(10.dp))
                        CardForRecordatory()
                    }
                    item {
                        Spacer(modifier = Modifier.padding(10.dp))

                        TextDiaryTraining()
                        CardDailyTraining(){
                        navController.navigate(ProjectScreens.SelectPlaceTrainingScreen.name)
                        }
                    }
                    item{
                        Spacer(modifier = Modifier.padding(10.dp))

                        TextDiaryEating()
                        CardDailyEating(){
                            navController.navigate(ProjectScreens.ActivePausesScreen.name)

                        }
                    }
                }, userScrollEnabled = true

                )
            }

        },
        bottomBar = { BottomNavComponent(navController) }

        )



}

@Composable
fun CardDailyTraining(function: () -> Unit) {
    ElevatedCard(
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        modifier = Modifier
            .fillMaxHeight(0.25f)
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .clickable(){
                function()
            }
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            Image(painter = painterResource(
                id = R.drawable.imagehometrainee),
                contentDescription = "Meme",
                alpha = 0.9f,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            )
            Text(
                text = "COMENZAR",
                fontFamily = lusitanaBoldFont,
                fontSize = 26 .sp,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                color = WhiteBone,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun CardDailyEating(function: () -> Unit) {
    ElevatedCard(
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        modifier = Modifier
            .fillMaxHeight(0.25f)
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .clickable(){
                function()
            }

    ) {
        Box(modifier = Modifier.fillMaxSize()){
            Image(painter = painterResource(
                id = R.drawable.pausaactivacard),
                contentDescription = "Meme",
                alpha = 0.9f,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            )
            Text(
                text = "COMENZAR",
                fontFamily = lusitanaBoldFont,
                fontSize = 26.sp,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                color = WhiteBone,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun TextDiaryTraining(){
    Text(
        text = "Entrenamiento de Hoy",
        fontFamily = lusitanaFont,
        fontSize = 25.sp,
        textAlign = TextAlign.Start,
        overflow = TextOverflow.Ellipsis,
        color = WhiteBone,
    )
}

@Composable
fun TextDiaryEating(){
    Text(
        text = "Pausas Activas",
        fontFamily = lusitanaFont,
        fontSize = 25.sp,
        textAlign = TextAlign.Start,
        overflow = TextOverflow.Ellipsis,
        color = WhiteBone,
    )
}

@Composable
fun CardForRecordatory(){
    OutlinedCard(
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(2.dp, WhiteBone),
        colors = CardDefaults.outlinedCardColors(
            containerColor = Color.Transparent,
            ),
        modifier = Modifier
            .fillMaxHeight(0.30f)
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
        ) {
        Box(){
            Row(verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Warning Icon",
                    tint = WhiteBone,
                    modifier = Modifier
                        .padding(top = 13.dp, start = 10.dp,end = 5.dp)
                )

                Column {
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(
                        text = "¡Recordatorio!",
                        fontFamily = lusitanaFont,
                        fontSize = 25.sp,
                        textAlign = TextAlign.Start,
                        overflow = TextOverflow.Ellipsis,
                        color = WhiteBone,
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    Text(
                        text = "No olvides cuidarte. Toma un sorbo de agua ahora mismo \uD83D\uDCA7 y date una breve pausa activa. ¡Tu bienestar es importante!",
                        fontFamily = lusitanaFont,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start,
                        color = WhiteBone

                    )
                    Spacer(modifier = Modifier.padding(2.dp))

                }
            }
        }
    }
}


@SuppressLint("RememberReturnType")
@Composable
fun AlertDialogObjetiveReached(stateDialog: Boolean, funForEvery:() -> Unit) {

    if (stateDialog) {
        AlertDialog(
            onDismissRequest = {
                funForEvery()
            },
            title = {
                Text(
                    text = "Tu meta concluyó",
                    fontFamily = lusitanaBoldFont,
                    fontSize = 22.sp,
                    overflow = TextOverflow.Ellipsis,
                    color = GoldenOpaque,
                    fontWeight = FontWeight.Bold,
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Parece que hoy es el dia que alcanzaste tu meta\n¡Revisemos como te fue!",
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
                    Icons.Filled.SportsScore,
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