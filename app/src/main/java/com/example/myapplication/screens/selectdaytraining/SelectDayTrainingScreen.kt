package com.example.myapplication.screens.selectdaytraining

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.screens.homescreen.HomeScreenViewModel
import com.example.myapplication.screens.homescreen.ui.CardDailyEating
import com.example.myapplication.screens.homescreen.ui.CardDailyTraining
import com.example.myapplication.screens.homescreen.ui.CardForRecordatory
import com.example.myapplication.screens.homescreen.ui.TextDiaryEating
import com.example.myapplication.screens.homescreen.ui.TextDiaryTraining
import com.example.myapplication.screens.navigation.BottomNavComponent
import com.example.myapplication.screens.navigation.ImageBackgroundAllAppScreen
import com.example.myapplication.screens.navigation.ProjectScreens
import com.example.myapplication.screens.navigation.TopAppBarBackWithIconInRight
import com.example.myapplication.screens.navigation.TopAppBarHomeScreen
import com.example.myapplication.ui.theme.WhiteBone
import com.example.myapplication.ui.theme.lusitanaBoldFont
import com.example.myapplication.ui.theme.lusitanaFont

@Composable
fun SelectDayTrainingScreen(navController: NavController){
    val snackbarHostState = remember { SnackbarHostState() }
    val currentUser = HomeScreenViewModel.currentUserLogged

    Scaffold(
        topBar = { TopAppBarBackWithIconInRight(navController, currentUser!!, "Dia De Entrenamiento") },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { innerPaddin ->

            Box(modifier = Modifier
                .padding(innerPaddin)
                .fillMaxSize()){
                ImageBackgroundAllAppScreen()
                Spacer(modifier = Modifier.padding(10.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(modifier = Modifier.padding(5.dp))

                    CardForDictDates("Lunes", R.drawable.lunesimgcard, Modifier.height(85.dp)) {
                        SelectDayTrainingViewModel.trainingDaySelect = "Lunes"
                        navController.navigate(ProjectScreens.DayTrainingListScreen.name)
                    }
                    Spacer(modifier = Modifier.padding(5.dp))
                    CardForDictDates("Martes", R.drawable.martesimgcard, Modifier.height(85.dp)) {
                        SelectDayTrainingViewModel.trainingDaySelect = "Martes"
                        navController.navigate(ProjectScreens.DayTrainingListScreen.name)

                    }
                    Spacer(modifier = Modifier.padding(5.dp))
                    CardForDictDates("Miercoles", R.drawable.miercolesimagecard, Modifier.height(85.dp)) {
                        SelectDayTrainingViewModel.trainingDaySelect = "Miercoles"
                        navController.navigate(ProjectScreens.DayTrainingListScreen.name)

                    }
                    Spacer(modifier = Modifier.padding(5.dp))
                    CardForDictDates("Jueves", R.drawable.juevesimagecard, Modifier.height(85.dp)) {
                        SelectDayTrainingViewModel.trainingDaySelect = "Jueves"
                        navController.navigate(ProjectScreens.DayTrainingListScreen.name)

                    }
                    Spacer(modifier = Modifier.padding(5.dp))
                    CardForDictDates("Viernes", R.drawable.viernesimagecard, Modifier.height(85.dp)) {
                        SelectDayTrainingViewModel.trainingDaySelect = "Viernes"
                        navController.navigate(ProjectScreens.DayTrainingListScreen.name)

                    }
                    Spacer(modifier = Modifier.padding(5.dp))
                    CardForDictDates("Sabado", R.drawable.sabadoimagecard, Modifier.height(85.dp)) {
                        SelectDayTrainingViewModel.trainingDaySelect = "Sabado"
                        navController.navigate(ProjectScreens.DayTrainingListScreen.name)

                    }
                    Spacer(modifier = Modifier.padding(10.dp))


                }

            }

        },
        bottomBar = { BottomNavComponent(navController) }

    )
}

@Composable
fun CardForDictDates(message: String, idImage: Int, modifier: Modifier, function: () -> Unit) {
    ElevatedCard(
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .clickable() {
                function()
            }
    ) {
        Box(){
            Image(painter = painterResource(
                id = idImage),
                contentDescription = "Meme",
                alpha = 0.9f,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            )
            Text(
                text = message,
                fontFamily = lusitanaFont,
                fontSize = 26.sp,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                color = WhiteBone,
                style = TextStyle(
                    shadow = Shadow(color = Color.Black,
                        offset = Offset.Zero,
                        blurRadius = 25f)
                ),
                modifier = Modifier.align(Alignment.BottomStart).padding(start = 4.dp)
            )
        }
    }
}