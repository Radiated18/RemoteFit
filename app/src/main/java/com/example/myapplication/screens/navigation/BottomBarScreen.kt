package com.example.myapplication.screens.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
){
    object InicioObjectBar: BottomBarScreen(
        route = ProjectScreens.HomeScreen.name,
        title = "Inicio",
        icon = Icons.Default.Home
    )
    object TrainingObjectBar: BottomBarScreen(
        route = ProjectScreens.SelectPlaceTrainingScreen.name,
        title = "Entrenos",
        icon = Icons.Default.FitnessCenter
    )
    object FoodObjectBar: BottomBarScreen(
        route = ProjectScreens.FoodsJourneyVisualizerScreen.name,
        title = "Comidas",
        icon = Icons.Default.Restaurant
    )
    object UsuarioObjectBar: BottomBarScreen(
        route = ProjectScreens.SettingsMenuScreen.name,
        title = "Usuario",
        icon = Icons.Default.Person
    )



}

