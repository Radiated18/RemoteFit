package com.example.myapplication.screens.goalssettingsscreen.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.screens.homescreen.HomeScreenViewModel
import com.example.myapplication.screens.navigation.BottomNavComponent
import com.example.myapplication.screens.navigation.ImageBackgroundAllAppScreen
import com.example.myapplication.screens.navigation.ProjectScreens
import com.example.myapplication.screens.navigation.TopAppBarBackWithIconInRight
import com.example.myapplication.screens.navigation.TopAppBarHomeScreen
import com.example.myapplication.ui.theme.GrayDark
import com.example.myapplication.ui.theme.WhiteBone
import com.example.myapplication.ui.theme.lusitanaBoldFont
import com.example.myapplication.ui.theme.lusitanaFont

@Composable
fun GoalsSettingsScreen(navController: NavController){
    val currentUser = HomeScreenViewModel.currentUserLogged
    val snackbarHostState = remember { SnackbarHostState() }


    Scaffold(
        topBar = { TopAppBarBackWithIconInRight(navController, currentUser!!, "Objetivos") },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { innerPaddin ->

            Box(modifier = Modifier
                .padding(innerPaddin)
                .fillMaxSize()){

                ImageBackgroundAllAppScreen()
                Spacer(modifier = Modifier.padding(10.dp))

                Column(modifier = Modifier.padding(horizontal = 20.dp)) {

                    Spacer(modifier = Modifier.padding(10.dp))
                    ListItemGoalsSettings("Seguimiento", "Revisa tu progreso", Icons.Default.HistoryEdu){
                        navController.navigate(ProjectScreens.GoalLogsScreen.name)

                    }

                    Spacer(modifier = Modifier.padding(10.dp))
                    ListItemGoalsSettings("Registro Actual", "Agregar nuevo registro", Icons.Default.Analytics){
                        navController.navigate(ProjectScreens.MonitoringRegisterScreen.name)
                    }

                    Spacer(modifier = Modifier.padding(10.dp))
                    ListItemGoalsSettings("Objetivo Final", "Ajusta tu meta", Icons.Default.Flag){
                        navController.navigate(ProjectScreens.FinalObjetiveScreen.name)

                    }

                    Spacer(modifier = Modifier.padding(10.dp))
                    ListItemGoalsSettings("Nivel", "Define tu nivel", Icons.Default.Speed){
                        navController.navigate(ProjectScreens.LevelAndFinalScreen.name)
                    }
                }


            }
        },
        bottomBar = { BottomNavComponent(navController) }


    )

}

@Composable
fun ListItemGoalsSettings(headlineText:String, supportingText:String, leadingIcon: ImageVector, functionNavigate:() -> Unit){
    ListItem(
        headlineContent = {
            Text(
                text = headlineText,
                fontFamily = lusitanaBoldFont,
                fontSize = 20.sp,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                color = WhiteBone,
            )
        },
        supportingContent = {
            Text(
                text = supportingText,
                fontFamily = lusitanaFont,
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                color = WhiteBone,
            )
        },
        trailingContent = {
            IconButton(onClick = { functionNavigate() }) {
                Icon(
                    Icons.Filled.ArrowForwardIos,
                    contentDescription = "Edit",
                    tint = WhiteBone,
                    modifier = Modifier.fillMaxSize(0.85f)
                )
            } },
        leadingContent = {

            Icon(
                imageVector = leadingIcon,
                contentDescription = "Edit",
                tint = WhiteBone,
                modifier = Modifier.size(30.dp)
            )
        },
        colors = ListItemDefaults.colors(
            containerColor = GrayDark,
            overlineColor = WhiteBone
        )
    )

}
