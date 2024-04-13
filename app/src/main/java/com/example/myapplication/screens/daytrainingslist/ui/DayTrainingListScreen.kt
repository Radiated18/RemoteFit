package com.example.myapplication.screens.daytrainingslist.ui

import android.annotation.SuppressLint
import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
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
import com.example.myapplication.screens.AlertDialogSample
import com.example.myapplication.screens.daytrainingslist.DayTrainingListViewModel
import com.example.myapplication.screens.homescreen.HomeScreenViewModel
import com.example.myapplication.screens.navigation.BottomNavComponent
import com.example.myapplication.screens.navigation.ImageBackgroundAllAppScreen
import com.example.myapplication.screens.navigation.ProjectScreens
import com.example.myapplication.screens.navigation.TopAppBarBackWithIconInRight
import com.example.myapplication.screens.navigation.TopAppBarHomeScreen
import com.example.myapplication.screens.selectdaytraining.SelectDayTrainingViewModel
import com.example.myapplication.screens.selectplacetraining.SelectPlaceTrainingViewModel
import com.example.myapplication.ui.theme.GoldenOpaque
import com.example.myapplication.ui.theme.GrayDark
import com.example.myapplication.ui.theme.GrayForTopBars
import com.example.myapplication.ui.theme.WhiteBone
import com.example.myapplication.ui.theme.lusitanaBoldFont
import com.example.myapplication.ui.theme.lusitanaFont
import okhttp3.internal.wait

@Composable
fun DayTrainingListScreen(navController: NavController, viewModel: DayTrainingListViewModel){
    val snackbarHostState = remember { SnackbarHostState() }
    val currentUser = HomeScreenViewModel.currentUserLogged
    viewModel.getExercisesList()

    val trainingExercisesList: List<TrainingExercises> by viewModel.exercisesTrainingsList.observeAsState(initial = mutableListOf())//Nombre mutable que se escribe

    val enableShowDialog: Boolean by viewModel.enableShowDialog.observeAsState(initial = false)//Nombre mutable que se escribe

    DialogShowCardExercise(enableShowDialog, viewModel,
        funForAll = { viewModel.changeValueDialog(enableShowDialog) },

    )

    Scaffold(
        topBar = { TopAppBarBackWithIconInRight(navController, currentUser!!, "Rutina ${SelectDayTrainingViewModel.trainingDaySelect}") },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { innerPaddin ->

            Box(modifier = Modifier
                .padding(innerPaddin)
                .fillMaxSize()){
                ImageBackgroundAllAppScreen()
                Spacer(modifier = Modifier.padding(10.dp))


                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    if (SelectPlaceTrainingViewModel.trainingTypeSelect == "Gym"){
                        TextTypeTrainningDaily(viewModel.getTrainingTextForDay(SelectDayTrainingViewModel.trainingDaySelect))
                    }

                    TextAnotationDaily()


                    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(vertical = 10.dp)) {

                        if (trainingExercisesList.isNotEmpty()){

                            item {
                                if(SelectPlaceTrainingViewModel.trainingTypeSelect == "Casa" && SelectDayTrainingViewModel.trainingDaySelect == "Sabado"){
                                    DescriptionWithSteps()
                                }
                            }

                            items(trainingExercisesList){ item ->
                                if(item.dia == SelectDayTrainingViewModel.trainingDaySelect || item.dia == "Todos"){
                                    if (item.entrenamiento == SelectPlaceTrainingViewModel.trainingTypeSelect){

                                            CardForShowTrainInformation(item){
                                                viewModel.selectableTrainingShower(item)
                                                viewModel.changeValueDialog(enableShowDialog)
                                            }
                                            Spacer(modifier = Modifier.padding(5.dp))

                                    }

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
fun DescriptionWithSteps() {
    val descripcion = "¡Prepárate para una sesión de carrera revitalizante! Dirígete a un parque o sendero cercano para disfrutar del aire fresco mientras corres\n\nAntes de comenzar, es importante dedicar unos minutos al calentamiento para preparar tu cuerpo y evitar lesiones. Sigue estos pasos para asegurarte de tener una sesión de entrenamiento segura y efectiva:"
    val pasos = listOf(
        "Calentamiento (5 minutos): Inicia con 2 minutos de estiramientos dinámicos para activar tus músculos principales, como las piernas, brazos y espalda. Luego, realiza una caminata rápida durante 3 minutos para elevar tu frecuencia cardíaca y preparar tus músculos para la carrera.",
        "Carrera (10 minutos): Corre a un ritmo cómodo y constante durante 10 minutos. No es necesario que corras rápido; concéntrate en mantener un ritmo que puedas mantener a lo largo del tiempo.",
        "Enfriamiento (5 minutos): Después de los 10 minutos de carrera, reduce gradualmente la velocidad a una caminata ligera durante 5 minutos. Esto ayudará a que tu frecuencia cardíaca disminuya gradualmente. Aprovecha este tiempo para estirar tus músculos principales, especialmente las piernas, los brazos y la espalda."
    )

    Column(modifier = Modifier.padding(horizontal = 25.dp)) {
        // Descripción
        Text(
            text = "Descripción:",
            fontFamily = lusitanaBoldFont,
            fontSize = 20.sp,
            textAlign = TextAlign.Start,
            color = GoldenOpaque,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = descripcion,
            fontFamily = lusitanaFont,
            fontSize = 18.sp,
            textAlign = TextAlign.Justify,
            overflow = TextOverflow.Ellipsis,
            color = WhiteBone,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Pasos
        pasos.forEach { paso ->
            val (tituloPaso, contenidoPaso) = paso.split(": ", limit = 2)
            Text(
                text = tituloPaso,
                fontFamily = lusitanaBoldFont,
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                color = GoldenOpaque,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = contenidoPaso,
                fontFamily = lusitanaFont,
                fontSize = 18.sp,
                textAlign = TextAlign.Justify,
                overflow = TextOverflow.Ellipsis,
                color = WhiteBone,
                modifier = Modifier.padding(bottom = 16.dp, start = 16.dp)
            )
        }
    }
}


@Composable
fun TextTypeTrainningDaily(text: String){
    Text(
        text = text,
        fontFamily = lusitanaFont,
        fontSize = 24.sp,
        textAlign = TextAlign.Center,
        overflow = TextOverflow.Ellipsis,
        color = GoldenOpaque,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 10.dp, bottom = 4.dp)
    )
}



@Composable
fun TextAnotationDaily(){
    Text(
        text = "Secuencia de ejercicios",
        fontFamily = lusitanaFont,
        fontSize = 22.sp,
        textAlign = TextAlign.Center,
        overflow = TextOverflow.Ellipsis,
        color = WhiteBone,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 10.dp, bottom = 4.dp)
    )
    Divider(color = GoldenOpaque, thickness = 1.dp, modifier = Modifier.padding(start = 40.dp, end = 40.dp, bottom = 5.dp))
}



@Composable
fun CardForShowTrainInformation(item: TrainingExercises, funPass: () -> Unit) {
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = GrayForTopBars,
        ),
        shape = RoundedCornerShape(1.dp),
        modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth()
            .padding(horizontal = 15.dp)

    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                SubcomposeAsyncImage(
                    model = item.urlimg,
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
                    alpha = 0.8f,
                    modifier = Modifier
                        .size(70.dp)
                        .padding(10.dp)
                        .border(
                            BorderStroke(1.dp, WhiteBone),
                            CircleShape
                        )
                        .clip(CircleShape)
                )

                Box(modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.82f)
                    .padding(end = 20.dp)
                ){
                    Text(
                        text = item.nombre,
                        fontFamily = lusitanaFont,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                        color = WhiteBone,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                }

                IconButton(onClick = { funPass() }) {
                    Icon(Icons.Default.PlayCircle, contentDescription = "", tint = WhiteBone,
                        modifier = Modifier.fillMaxSize(0.7f)
                    )
                }

            }
        }

    }
}

@SuppressLint("RememberReturnType")
@Composable
fun DialogShowCardExercise(stateDialog: Boolean, viewModel: DayTrainingListViewModel, funForAll:() -> Unit) {
    val exerciseTrainingShower: TrainingExercises? by viewModel.exerciseTrainingShower.observeAsState(initial = null)//Nombre mutable que se escribe

    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    if (stateDialog && exerciseTrainingShower != null) {
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
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                        color = GoldenOpaque,
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Text(
                        text = exerciseTrainingShower!!.descripcion,
                        fontFamily = lusitanaFont,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                        color = WhiteBone,
                    )
                }
            }
        }
    }
}

