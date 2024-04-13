package com.example.myapplication.screens.foodsvisualizerlist.ui

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
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.PlayCircle
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.myapplication.R
import com.example.myapplication.models.FoodModel
import com.example.myapplication.models.TrainingExercises
import com.example.myapplication.screens.foodsjourneyvisualizer.FoodsJourneyVisualizerViewModel
import com.example.myapplication.screens.foodsjourneyvisualizer.FoodsJourneyVisualizerViewModel.Companion.foodJourneySelected
import com.example.myapplication.screens.foodsjourneyvisualizer.ui.CardForJourneyFoods
import com.example.myapplication.screens.foodsvisualizerlist.FoodsVisualizerListViewModel
import com.example.myapplication.screens.homescreen.HomeScreenViewModel
import com.example.myapplication.screens.navigation.BottomNavComponent
import com.example.myapplication.screens.navigation.ImageBackgroundAllAppScreen
import com.example.myapplication.screens.navigation.ProjectScreens
import com.example.myapplication.screens.navigation.TopAppBarBackWithIconInRight
import com.example.myapplication.ui.theme.GoldenOpaque
import com.example.myapplication.ui.theme.GrayForTopBars
import com.example.myapplication.ui.theme.WhiteBone
import com.example.myapplication.ui.theme.lusitanaFont
import java.util.Locale

@Composable
fun FoodsVisualizerListScreen(navController: NavController, viewModel: FoodsVisualizerListViewModel){
    viewModel.getFoodList()

    val snackbarHostState = remember { SnackbarHostState() }
    val currentUser = HomeScreenViewModel.currentUserLogged

    val foodList: List<FoodModel> by viewModel.foodList.observeAsState(initial = mutableListOf())//Nombre mutable que se escribe



    Scaffold(
        topBar = { TopAppBarBackWithIconInRight(navController, currentUser!!, foodJourneySelected) },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { innerPaddin ->

            Box(modifier = Modifier
                .padding(innerPaddin)
                .fillMaxSize()){
                ImageBackgroundAllAppScreen()
                Spacer(modifier = Modifier.padding(10.dp))


                LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                    item {
                        Spacer(modifier = Modifier.padding(10.dp))
                        TitleTextForVisualizerFoods()
                        Spacer(modifier = Modifier.padding(10.dp))

                    }

                    items(foodList){ item ->
                        if (item.jornada.equals(foodJourneySelected, ignoreCase = true)){
                            CardForFoodsVisualizer(item) {
                                FoodsVisualizerListViewModel.foodSelectedForDescription = item
                                navController.navigate(ProjectScreens.FoodDescriptionVisualizerScreen.name)
                            }
                            Spacer(modifier = Modifier.padding(10.dp))
                        }
                        
                    }



                }
            }
        },
        bottomBar = { BottomNavComponent(navController) }
    )
}

@Composable
fun TitleTextForVisualizerFoods() {
    Text(
        text = "Seleccion Tu Alimento Favorito",
        fontFamily = lusitanaFont,
        fontSize = 22.sp,
        textAlign = TextAlign.Center,
        overflow = TextOverflow.Ellipsis,
        color = WhiteBone,
        modifier = Modifier.padding(horizontal = 15.dp)
    )
    Divider(thickness = 1.dp, color = GoldenOpaque, modifier = Modifier.padding(horizontal = 10.dp))
}

@Composable
fun CardForFoodsVisualizer(item: FoodModel, function: () -> Unit) {
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = GrayForTopBars,
        ),
        shape = RoundedCornerShape(1.dp),
        modifier = Modifier
            .fillMaxHeight(0.7f)
            .fillMaxWidth()
            .padding(horizontal = 15.dp)

    ){
        Box(modifier = Modifier.fillMaxSize()) {
            Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                SubcomposeAsyncImage(
                    model = item.img_url,
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
                        .size(90.dp)
                        .padding(10.dp)
                        .border(
                            BorderStroke(1.dp, WhiteBone),
                            CircleShape
                        )
                        .clip(CircleShape)
                )
                Box(modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.8f)
                    .padding(end = 20.dp)
                ){
                    Text(
                        text = item.nombre,
                        fontFamily = lusitanaFont,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                        color = WhiteBone,
                        modifier = Modifier.padding(horizontal = 15.dp)
                    )

                }

                IconButton(onClick = { function() }) {
                    Icon(
                        Icons.Default.ArrowForwardIos, contentDescription = "", tint = WhiteBone,
                        modifier = Modifier.fillMaxSize(1f)
                    )
                }
            }
        }
    }
}
