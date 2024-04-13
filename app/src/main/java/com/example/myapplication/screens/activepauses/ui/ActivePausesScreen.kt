package com.example.myapplication.screens.activepauses.ui

import android.os.Build
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.myapplication.models.TrainingExercises
import com.example.myapplication.screens.activepauses.ActivePausesViewModel
import com.example.myapplication.screens.daytrainingslist.ui.CardForShowTrainInformation
import com.example.myapplication.screens.daytrainingslist.ui.TextAnotationDaily
import com.example.myapplication.screens.homescreen.HomeScreenViewModel
import com.example.myapplication.screens.navigation.BottomNavComponent
import com.example.myapplication.screens.navigation.ImageBackgroundAllAppScreen
import com.example.myapplication.screens.navigation.TopAppBarBackWithIconInRight
import com.example.myapplication.screens.selectdaytraining.SelectDayTrainingViewModel
import com.example.myapplication.ui.theme.GoldenOpaque
import com.example.myapplication.ui.theme.GrayDark
import com.example.myapplication.ui.theme.WhiteBone
import com.example.myapplication.ui.theme.lusitanaBoldFont
import com.example.myapplication.ui.theme.lusitanaFont

@Composable
fun ActivePausesScreen(navController: NavController, viewModel: ActivePausesViewModel){
    val snackbarHostState = remember { SnackbarHostState() }
    val currentUser = HomeScreenViewModel.currentUserLogged
    viewModel.getActivePausesList()

    val activePausesList: List<TrainingExercises> by viewModel.activePausesList.observeAsState(initial = mutableListOf())//Nombre mutable que se escribe

    val enableShowDialog: Boolean by viewModel.enableShowDialog.observeAsState(initial = false)//Nombre mutable que se escribe

    DialogShowCardActivePauses(enableShowDialog, viewModel,
        funForAll = { viewModel.changeValueDialog(enableShowDialog) },

        )

    Scaffold(
        topBar = { TopAppBarBackWithIconInRight(navController, currentUser!!, "Pausas Activas") },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { innerPaddin ->

            Box(modifier = Modifier
                .padding(innerPaddin)
                .fillMaxSize()){
                ImageBackgroundAllAppScreen()
                Spacer(modifier = Modifier.padding(10.dp))


                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    TextAnotationDaily()


                    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(vertical = 10.dp)) {

                        if (activePausesList.isNotEmpty()){

                            items(activePausesList){ item ->
                                if(item.entrenamiento == "PausaActiva"){
                                    CardForShowTrainInformation(item){
                                        viewModel.selectableActivePauseShower(item)
                                        viewModel.changeValueDialog(enableShowDialog)
                                    }
                                    Spacer(modifier = Modifier.padding(5.dp))
                                }
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
fun DialogShowCardActivePauses(enableShowDialog: Boolean, viewModel: ActivePausesViewModel, funForAll: () -> Unit) {
    val exerciseTrainingShower: TrainingExercises? by viewModel.activePausesShower.observeAsState(initial = null)//Nombre mutable que se escribe

    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    if (enableShowDialog && exerciseTrainingShower != null) {
        Dialog(onDismissRequest = { funForAll() }){
            Card(
                colors = CardDefaults.cardColors(containerColor = GrayDark),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.85f)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ){
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    SubcomposeAsyncImage(
                        imageLoader = imageLoader,
                        model = exerciseTrainingShower!!.urlgif,
                        contentDescription = "Imagen Icon",
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center,
                        loading = {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(1.dp)
                                    .padding(10.dp),
                                color = GoldenOpaque
                            )
                        },
                        alpha = 0.9f,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.6f)
                            .padding(10.dp)
                            .border(
                                BorderStroke(1.dp, WhiteBone),
                                RoundedCornerShape(5.dp)
                            )
                            .clip(RoundedCornerShape(5.dp))
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Text(
                        text = exerciseTrainingShower!!.nombre,
                        fontFamily = lusitanaBoldFont,
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                        color = GoldenOpaque,
                    )
                    Spacer(modifier = Modifier.padding(15.dp))
                    Text(
                        text = exerciseTrainingShower!!.descripcion,
                        fontFamily = lusitanaFont,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                        color = WhiteBone,
                    )
                }
            }
        }
    }
}
