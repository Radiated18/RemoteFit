package com.example.myapplication.screens.welcomeregister

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.screens.login.LoginScreenViewModel
import com.example.myapplication.screens.login.ui.ImageBackgroundLoginScreen
import com.example.myapplication.screens.navigation.ProjectScreens
import com.example.myapplication.screens.register.RegisterViewModel
import com.example.myapplication.ui.theme.GoldenOpaque
import com.example.myapplication.ui.theme.WhiteBone
import com.example.myapplication.ui.theme.lusitanaBoldFont
import com.example.myapplication.ui.theme.lusitanaFont

@Composable
fun WelcomeRegisterScreen(navController: NavController){
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        WelcomeBackgroundLoginScreen()
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TitleOneWelcomeScreen()
            Spacer(modifier = Modifier.padding(15.dp))
            TextIntroduccionWl()
            Spacer(modifier = Modifier.padding(30.dp))
            ButtonToNextForm {
                navController.navigate(ProjectScreens.FormPageScreen.name)
            }
        }
    }
}

@Composable
fun TitleOneWelcomeScreen(){
    Text(
        text = "Desata tu potencial\nSaludable",
        fontFamily = lusitanaBoldFont,
        color = WhiteBone,
        fontSize = 28.sp,
        textAlign = TextAlign.Center,
        lineHeight = 40.sp

    )

}

@Composable
fun TextIntroduccionWl(){
    Text(
        text = "Ayúdanos a conocerte mejor. Completa nuestro formulario rápido y descubre cómo podemos personalizar tu experiencia para alcanzar juntos tus objetivos de bienestar. Tu viaje hacia una vida más saludable comienza con un simple paso. ¡Llenemos el formulario y avancemos hacia un tú más saludable!",
        fontFamily = lusitanaFont,
        color = WhiteBone,
        fontSize = 18.sp,
        textAlign = TextAlign.Center,
        lineHeight = 20.sp,

        modifier = Modifier.padding(horizontal = 20.dp)

    )
}

@Composable
fun ButtonToNextForm(function: () -> Unit){
    Button(onClick = { function()

                     },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = GoldenOpaque
        ),
        modifier = Modifier
            .padding(horizontal = 60.dp)
            .fillMaxWidth()
            .height(45.dp)
    ) {
        Text(
            text = "SIGUIENTE",
            fontFamily = lusitanaBoldFont,
            color = WhiteBone,
            fontSize = 16.sp
        )
    }
}

@Composable
fun WelcomeBackgroundLoginScreen(){
    Image(painter = painterResource(
        id = R.drawable.welcomeregisterbg),
        contentDescription = "Background",
        alpha = 0.95f,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    )
}
