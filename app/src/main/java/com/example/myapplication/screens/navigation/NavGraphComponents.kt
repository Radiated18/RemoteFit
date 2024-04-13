package com.example.myapplication.screens.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.models.UserModel
import com.example.myapplication.ui.theme.GoldenOpaque
import com.example.myapplication.ui.theme.GrayDark
import com.example.myapplication.ui.theme.GrayForTopBars
import com.example.myapplication.ui.theme.WhiteBone
import com.example.myapplication.ui.theme.lusitanaFont

@SuppressLint("RememberReturnType")
@Composable
fun BottomNavComponent(navController: NavController){
    val items = listOf(
        BottomBarScreen.InicioObjectBar,
        BottomBarScreen.TrainingObjectBar,
        BottomBarScreen.FoodObjectBar,
        BottomBarScreen.UsuarioObjectBar
    )
    val navBackStackEntry = navController.currentBackStackEntry?.destination

    NavigationBar(containerColor = GrayForTopBars) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = "",
                        tint = GoldenOpaque,
                        modifier = Modifier.padding(bottom = 6.dp).fillMaxSize(0.5f)
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontFamily = lusitanaFont,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Clip,
                        color = WhiteBone,
                    )
                },
                selected = navBackStackEntry?.hierarchy?.any{
                    it.route == item.route
                } == true,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = GrayDark
                ),
                onClick = { navController.navigate(item.route) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarHomeScreen(navController: NavController, currentUser: UserModel?){

    val ImgLinkUserIcon = currentUser?.img_link

    TopAppBar(
        title = {
            Column() {
                Text(
                    text = "¡Bienvenido!",
                    fontFamily = lusitanaFont,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                )

            }

        },
        navigationIcon = {
            IconButton(onClick = {
                navController.navigate(ProjectScreens.EditInformationScreen.name)

            }) {
                Box(modifier = Modifier.fillMaxSize()){
                    when(ImgLinkUserIcon){
                        "", null -> Icon(
                            imageVector = Icons.Default.ImageNotSupported,
                            contentDescription = "Arrow Icon",
                            tint = WhiteBone,
                            modifier = Modifier
                                .padding(start = 0.dp)
                                .align(Alignment.Center)
                                .fillMaxSize(0.85f)
                        )
                        else -> AsyncImage(
                            model = ImgLinkUserIcon,
                            contentDescription = "Imagen Icon",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(0.dp)
                                .fillMaxSize()
                        )
                    }
                }
            }
        },
        actions = {
            IconButton(onClick = {
                //AQUI VA EL SETTING PARA CONFIGURACION DEL PERFIL
            }) {
                Icon(
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = "Notifications Icon",
                    tint = GoldenOpaque,
                    modifier = Modifier
                        .padding(start = 0.dp)
                        .fillMaxSize(0.85f)
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = GrayForTopBars,
            navigationIconContentColor = WhiteBone,
            titleContentColor = WhiteBone,
            actionIconContentColor = GrayDark
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarBackWithIconInRight(navController: NavController, currentUser: UserModel, textHeader: String){
    val ImgLinkUserIcon = currentUser.img_link


    TopAppBar(
        title = {
            Text(
                text = textHeader,
                fontFamily = lusitanaFont,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Clip
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.navigateUp()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIos,
                    contentDescription = "Arrow Icon",
                    modifier = Modifier
                        .padding(start = 0.dp)
                )
            }

        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = GrayForTopBars,
            navigationIconContentColor = WhiteBone,
            titleContentColor = WhiteBone,
            actionIconContentColor = GrayDark
        ),
        actions = {
            IconButton(onClick = {
                navController.navigate(ProjectScreens.EditInformationScreen.name)

            }) {
                Box(modifier = Modifier.fillMaxSize()){
                    when(ImgLinkUserIcon){
                        "", null -> Icon(
                            imageVector = Icons.Default.ImageNotSupported,
                            contentDescription = "Arrow Icon",
                            tint = WhiteBone,
                            modifier = Modifier
                                .padding(start = 0.dp)
                                .align(Alignment.Center)
                                .fillMaxSize(0.85f)
                        )
                        else -> AsyncImage(
                            model = ImgLinkUserIcon,
                            contentDescription = "Imagen Icon",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(0.dp)
                                .fillMaxSize()
                        )
                    }
                }
            }
        }
    )
}


@Composable
fun ImageBackgroundAllAppScreen(){
    Image(painter = painterResource(
        id = R.drawable.formscreenbackground),
        contentDescription = "Meme",
        alpha = 0.9f,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    )
}