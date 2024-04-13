package com.example.myapplication.screens.foodDesriptionVisualizer

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.myapplication.models.FoodModel
import com.example.myapplication.screens.foodsvisualizerlist.FoodsVisualizerListViewModel
import com.example.myapplication.screens.homescreen.HomeScreenViewModel
import com.example.myapplication.screens.navigation.BottomNavComponent
import com.example.myapplication.screens.navigation.ImageBackgroundAllAppScreen
import com.example.myapplication.screens.navigation.TopAppBarBackWithIconInRight
import com.example.myapplication.ui.theme.GoldenOpaque
import com.example.myapplication.ui.theme.WhiteBone
import com.example.myapplication.ui.theme.lusitanaBoldFont
import com.example.myapplication.ui.theme.lusitanaFont

@Composable
fun FoodDescriptionVisualizerScreen(navController: NavController){
    val snackbarHostState = remember { SnackbarHostState() }
    val currentUser = HomeScreenViewModel.currentUserLogged

    val foodSelectedToDescribe = FoodsVisualizerListViewModel.foodSelectedForDescription
    val foodListOfIngredientsAdjust = foodSelectedToDescribe.adjustQuantityOfIngredients(currentUser!!)

    Scaffold(
        topBar = { TopAppBarBackWithIconInRight(navController, currentUser!!, "Descripción del alimento") },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { innerPaddin ->

            Box(modifier = Modifier
                .padding(innerPaddin)
                .fillMaxSize()){
                ImageBackgroundAllAppScreen()
                Spacer(modifier = Modifier.padding(10.dp))


                LazyColumn() {
                    item {
                        Spacer(modifier = Modifier.padding(10.dp))

                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.5f)){
                            MainImageForFood(foodSelectedToDescribe.img_url)
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.padding(5.dp))
                        TitleTextNameForVisualizerFoods(foodSelectedToDescribe.nombre)
                        Spacer(modifier = Modifier.padding(10.dp))
                        TitleTextGroupForVisualizerFoodsCategory("Ingredientes y Cantidades")
                    }

                    items(foodListOfIngredientsAdjust) { item ->
                        Spacer(modifier = Modifier.padding(5.dp))
                        ListForIngredientsAndValues(item)

                    }

                    item {
                        Divider(thickness = 1.dp, color = WhiteBone, modifier = Modifier.padding(horizontal = 30.dp))
                        Spacer(modifier = Modifier.padding(10.dp))

                        TitleTextGroupForVisualizerFoodsCategory("Macronutrientres Aportados")

                    }

                    item {
                        Spacer(modifier = Modifier.padding(10.dp))

                        ListForMacroNutrientsAndValues(
                            FoodModel.obtainTotalOfCalories(foodListOfIngredientsAdjust).toString(),
                            FoodModel.obtainTotalOfProtein(foodListOfIngredientsAdjust).toString(),
                            FoodModel.obtainTotalOfCarbohidrats(foodListOfIngredientsAdjust).toString(),
                            FoodModel.obtainTotalOfGrasas(foodListOfIngredientsAdjust).toString(),

                            )

                    }

                    item {
                        Divider(thickness = 1.dp, color = WhiteBone, modifier = Modifier.padding(horizontal = 30.dp))
                        Spacer(modifier = Modifier.padding(10.dp))

                        TitleTextGroupForVisualizerFoodsCategory("Receta")
                        Spacer(modifier = Modifier.padding(10.dp))

                        DescriptionTextForFood(foodSelectedToDescribe.receta)
                    }

                }
            }
        },
        bottomBar = { BottomNavComponent(navController) }
    )
}

@Composable
fun DescriptionTextForFood(receta: String) {
    // Dividir la receta en pasos usando el delimitador "n. "
    val pasos = receta.split("\\d+\\. ".toRegex())

    // Eliminar el primer elemento de la lista, ya que estará vacío debido al primer número seguido de punto en la cadena original
    val pasosFormateados = pasos.filter { it.isNotBlank() }

    // Crear un texto para cada paso
    Column {
        pasosFormateados.forEachIndexed { index, paso ->
            Text(
                text = "${index + 1}. $paso",
                fontFamily = lusitanaFont,
                fontSize = 18.sp,
                textAlign = TextAlign.Justify,
                overflow = TextOverflow.Ellipsis,
                color = WhiteBone,
                modifier = Modifier.padding(horizontal = 25.dp, vertical = 8.dp)
            )
        }
    }
}


@Composable
fun ListForMacroNutrientsAndValues(calorias: String, proteinas: String, carbohidratos: String, grasas: String, ) {
    Column(horizontalAlignment = Alignment.Start) {
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = GoldenOpaque)) {
                    append(" ◉ Calorias: ")
                }
                append("$calorias cal")
            },
            fontFamily = lusitanaFont,
            fontSize = 18.sp,
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            color = WhiteBone, // Cambia el color del texto después de los dos puntos
            modifier = Modifier.padding(horizontal = 30.dp)
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = GoldenOpaque)) {
                    append(" ◉ Proteinas: ")
                }
                append("$proteinas gramos")
            },
            fontFamily = lusitanaFont,
            fontSize = 18.sp,
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            color = WhiteBone, // Cambia el color del texto después de los dos puntos
            modifier = Modifier.padding(horizontal = 30.dp)
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = GoldenOpaque)) {
                    append(" ◉ Carbohidratos: ")
                }
                append("$carbohidratos gramos")
            },
            fontFamily = lusitanaFont,
            fontSize = 18.sp,
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            color = WhiteBone, // Cambia el color del texto después de los dos puntos
            modifier = Modifier.padding(horizontal = 30.dp)
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = GoldenOpaque)) {
                    append(" ◉ Grasas: ")
                }
                append("$grasas gramos")
            },
            fontFamily = lusitanaFont,
            fontSize = 18.sp,
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            color = WhiteBone, // Cambia el color del texto después de los dos puntos
            modifier = Modifier.padding(horizontal = 30.dp)
        )
        Spacer(modifier = Modifier.padding(5.dp))


    }
}

@Composable
fun ListForIngredientsAndValues(item: MutableMap<String, Any>) {
    Column(horizontalAlignment = Alignment.Start){
        if(item["nombre"].toString() != "null"){
            var cantidadRedondeada = item["cantidad"]

            if (item["cantidad"] is Number){
                cantidadRedondeada = kotlin.math.ceil(item["cantidad"] as Double).toInt()
            }

            TextForIngredientAndQuantity(item["nombre"].toString(), cantidadRedondeada.toString(), item["medida"].toString())

        }

    }
}

@Composable
fun TextForIngredientAndQuantity(nameIngredient: String, quantityIngredient: String, unitIngredient: String){

    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = GoldenOpaque)) {
                append(" ◉ $nameIngredient: ")
            }
            append("$quantityIngredient $unitIngredient")
        },
        fontFamily = lusitanaFont,
        fontSize = 18.sp,
        textAlign = TextAlign.Start,
        overflow = TextOverflow.Ellipsis,
        color = WhiteBone, // Cambia el color del texto después de los dos puntos
        modifier = Modifier.padding(horizontal = 30.dp)
    )



}

@Composable
fun TitleTextNameForVisualizerFoods(nameFood: String) {
    Text(
        text = nameFood,
        fontFamily = lusitanaBoldFont,
        fontSize = 24.sp,
        textAlign = TextAlign.Center,
        overflow = TextOverflow.Ellipsis,
        color = GoldenOpaque,
        modifier = Modifier.padding(horizontal = 25.dp)
    )
    Spacer(modifier = Modifier.padding(2.dp))
    Divider(thickness = 1.dp, color = WhiteBone, modifier = Modifier.padding(horizontal = 30.dp))
}

@Composable
fun TitleTextGroupForVisualizerFoodsCategory(category: String) {
    Text(
        text = "$category:",
        fontFamily = lusitanaBoldFont,
        fontSize = 22.sp,
        textAlign = TextAlign.Start,
        overflow = TextOverflow.Ellipsis,
        color = WhiteBone,
        modifier = Modifier.padding(horizontal = 25.dp)
    )
}


@Composable
fun MainImageForFood(urlImg: String){
    SubcomposeAsyncImage(
        model = urlImg,
        contentDescription = "Imagen Icon",
        contentScale = ContentScale.FillBounds,
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
            .fillMaxSize()
            .padding(horizontal = 75.dp)
            .border(
                BorderStroke(1.dp, WhiteBone),
                RoundedCornerShape(10.dp)
            )
            .clip(RoundedCornerShape(10.dp))
    )
}