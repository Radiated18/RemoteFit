package com.example.myapplication.screens.SplashScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.screens.navigation.ProjectScreens
import com.example.myapplication.ui.theme.BlueCapri
import com.example.myapplication.ui.theme.lusitanaFont
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController){
    LaunchedEffect(key1 = true){
        delay(1)
        if (FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()){
            navController.navigate(ProjectScreens.LoginScreen.name)
        }else{
            //navController.navigate(ProjectScreens.LoginScreen.name)
            navController.navigate(ProjectScreens.HomeScreen.name)

        }

    }
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        ImageBackgroundSplashScreen()
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TextLogo()
            Spacer(modifier = Modifier.padding(20.dp))
            CircleLoading()
        }
    }
}

@Composable
fun ImageBackgroundSplashScreen(){
    Image(painter = painterResource(
        id = R.drawable.menusplahsbg),
        contentDescription = "Background",
        alpha = 0.85f,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    )
}

@Composable
fun CircleLoading(){
    CircularProgressIndicator(
        modifier = Modifier.size(44.dp),
        color = BlueCapri
    )
}

@Composable
fun TextLogo(){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "RemoteFit",
            fontFamily = lusitanaFont,
            fontSize = 60.sp,
            textAlign = TextAlign.Center,
            color = Color.White.copy(alpha = 0.8f),
        )
        Spacer(modifier = Modifier.padding(2.dp))
        Text(
            text = "Hub",
            fontFamily = lusitanaFont,
            fontSize = 60.sp,
            textAlign = TextAlign.Center,
            color = Color.White.copy(alpha = 0.8f),
        )
    }
}