package com.example.myapplication.screens.selectplacetraining

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
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

@Composable
fun SelectPlaceTrainingScreen(navController: NavController){
    val snackbarHostState = remember { SnackbarHostState() }
    val currentUser = HomeScreenViewModel.currentUserLogged

    Scaffold(
        topBar = { TopAppBarBackWithIconInRight(navController, currentUser!!, "Lugar de Entrenamiento") },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { innerPaddin ->

            Box(modifier = Modifier
                .padding(innerPaddin)
                .fillMaxSize()){
                ImageBackgroundAllAppScreen()
                Spacer(modifier = Modifier.padding(10.dp))
                Column() {
                    Spacer(modifier = Modifier.padding(10.dp))

                    CardDailyTrainingInGym {
                        SelectPlaceTrainingViewModel.trainingTypeSelect = "Gym"
                        navController.navigate(ProjectScreens.SelectDayTrainingScreen.name)
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    CardDailyTrainingInHome {
                        SelectPlaceTrainingViewModel.trainingTypeSelect = "Casa"
                        navController.navigate(ProjectScreens.SelectDayTrainingScreen.name)

                    }
                }

            }

        },
        bottomBar = { BottomNavComponent(navController) }

    )
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun CardDailyTrainingInHome(function: () -> Unit) {
    ElevatedCard(
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        modifier = Modifier
            .fillMaxHeight(0.9f)
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .clickable() {
                function()
            }
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            Image(painter = painterResource(
                id = R.drawable.homeworkout),
                contentDescription = "Meme",
                alpha = 0.7f,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            )
            Text(
                text = "ENTRENAMIENTO\nEN CASA",
                fontFamily = lusitanaBoldFont,
                fontSize = 26 .sp,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                color = WhiteBone,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center),
                style = TextStyle(
                   shadow = Shadow(color = Color.Black,
                   offset = Offset.Zero,
                   blurRadius = 25f)
                )
            )
        }
    }
}

@Composable
fun CardDailyTrainingInGym(function: () -> Unit) {
    ElevatedCard(
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        modifier = Modifier
            .fillMaxHeight(0.45f)
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .clickable() {
                function()
            }

    ) {
        Box(modifier = Modifier.fillMaxSize()){
            Image(painter = painterResource(
                id = R.drawable.gymworkout),
                contentDescription = "Meme",
                alpha = 0.7f,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            )
            Text(
                text = "ENTRENAMIENTO\nEN GIMNASIO",
                fontFamily = lusitanaBoldFont,
                fontSize = 26.sp,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                color = WhiteBone,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center),
                style = TextStyle(
                    shadow = Shadow(color = Color.Black,
                        offset = Offset.Zero,
                        blurRadius = 25f)
                )
            )
        }
    }
}