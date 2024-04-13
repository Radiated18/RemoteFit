package com.example.myapplication.screens.badgeforuser.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.myapplication.R
import com.example.myapplication.models.ActualRecordRegistration
import com.example.myapplication.screens.badgeforuser.BadgeForUserViewModel
import com.example.myapplication.screens.editinformation.ui.AddImageToStorageResponse
import com.example.myapplication.screens.editinformation.ui.AlertDialogForUpdateImage
import com.example.myapplication.screens.editinformation.ui.AlertDialogForUpdateInformation
import com.example.myapplication.screens.editinformation.ui.ButtonToUpdateUserInfo
import com.example.myapplication.screens.editinformation.ui.CircleImageProfile
import com.example.myapplication.screens.editinformation.ui.TextFieldToChangeJourney
import com.example.myapplication.screens.editinformation.ui.TextFieldToChangeName
import com.example.myapplication.screens.editinformation.ui.TextFieldToChangeNumber
import com.example.myapplication.screens.editinformation.ui.TextFieldToViewInformation
import com.example.myapplication.screens.editinformation.ui.UpdateInformationFromDatabaseAndCircle
import com.example.myapplication.screens.homescreen.HomeScreenViewModel
import com.example.myapplication.screens.navigation.BottomNavComponent
import com.example.myapplication.screens.navigation.ImageBackgroundAllAppScreen
import com.example.myapplication.screens.navigation.ProjectScreens
import com.example.myapplication.screens.navigation.TopAppBarBackWithIconInRight
import com.example.myapplication.ui.theme.GoldenOpaque
import com.example.myapplication.ui.theme.GrayDark
import com.example.myapplication.ui.theme.GrayForTopBars
import com.example.myapplication.ui.theme.WhiteBone
import com.example.myapplication.ui.theme.lusitanaBoldFont
import com.example.myapplication.ui.theme.lusitanaFont
import com.example.myapplication.utils.Constants
import kotlinx.coroutines.launch

@Composable
fun BadgeForUserScreen(navController: NavController, viewModel: BadgeForUserViewModel){
    val historyRecords: List<ActualRecordRegistration> by viewModel.historyRecordRegistered.observeAsState(initial = mutableListOf())//Nombre mutable que se escribe
    val finalObjetiveForUser by viewModel.objetiveUserGet.observeAsState(initial = null)

    viewModel.getHistoryRecords()
    viewModel.getFinalObjetive()

    val currentUser = HomeScreenViewModel.currentUserLogged
    val snackbarHostState = remember { SnackbarHostState() }

    val badgeOneRecord = historyRecords.isNotEmpty()
    val badgeTwoRecord = historyRecords.size >= 2
    val badgeThreeRecord = historyRecords.size >= 3
    val badgeFourRecord = historyRecords.size >= 4
    val badgeFiveRecord = historyRecords.size >= 5
    val badgeTenRecord = historyRecords.size >= 10
    val badgeSetFinalObjetive = finalObjetiveForUser != null



    Scaffold(
        topBar = { TopAppBarBackWithIconInRight(navController, currentUser!!,"Insignias") },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { innerPaddin ->

            Box(
                modifier = Modifier
                    .padding(innerPaddin)
                    .fillMaxSize()
            ) {
                ImageBackgroundAllAppScreen()
                Spacer(modifier = Modifier.padding(10.dp))

                LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                    item {
                        Spacer(modifier = Modifier.padding(10.dp))
                    }
                    item {
                        CardForShowBadgeEarnedForUser(badgeOneRecord, "El Inicio", "Realiza un registro", R.drawable.badgeonerecord)
                        Spacer(modifier = Modifier.padding(10.dp))
                    }
                    item {
                        CardForShowBadgeEarnedForUser(badgeTwoRecord, "Vamos Bien", "Realiza dos registros", R.drawable.badgetworecord)
                        Spacer(modifier = Modifier.padding(10.dp))
                    }
                    item {
                        CardForShowBadgeEarnedForUser(badgeThreeRecord, "Buen Ritmo", "Realiza tres registros", R.drawable.badgethreerecord)
                        Spacer(modifier = Modifier.padding(10.dp))
                    }
                    item {
                        CardForShowBadgeEarnedForUser(badgeFourRecord, "Constancia", "Realiza cuatro registros", R.drawable.badgefiverecords)
                        Spacer(modifier = Modifier.padding(10.dp))
                    }
                    item {
                        CardForShowBadgeEarnedForUser(badgeFiveRecord, "Disciplina", "Realiza cinco registros", R.drawable.badgefiverecords)
                        Spacer(modifier = Modifier.padding(10.dp))
                    }
                    item {
                        CardForShowBadgeEarnedForUser(badgeTenRecord, "¿Fue Tan Facil?", "Realiza diez registros", R.drawable.badgetenrecords)
                        Spacer(modifier = Modifier.padding(10.dp))
                    }
                    item {
                        CardForShowBadgeEarnedForUser(badgeSetFinalObjetive, "Enfocado", "Establece tu meta", R.drawable.badgeobjetive)
                        Spacer(modifier = Modifier.padding(10.dp))
                    }



                }
            }
        },
        bottomBar = { BottomNavComponent(navController) },

        )
}

@Composable
fun CardForShowBadgeEarnedForUser(earnedBadge: Boolean, nameBadge: String, descriptionBadge: String, imgIdBadge: Int){

    val imgIdBadgeToShow = if(earnedBadge) imgIdBadge else R.drawable.badgenoearned

    ListItem(
        headlineContent = {
            Text(
                text = nameBadge,
                fontFamily = lusitanaBoldFont,
                fontSize = 20.sp,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                color = GoldenOpaque,
            )
        },
        supportingContent = {
            Text(
                text = descriptionBadge,
                fontFamily = lusitanaFont,
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                color = WhiteBone,
            )
        },
        leadingContent = {

            Image(painter = painterResource(
                id = imgIdBadgeToShow),
                contentDescription = "Badge",
                alpha = 0.85f,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize(0.15f)
                    .background(Color.Transparent)
            )
        },
        colors = ListItemDefaults.colors(
            containerColor = GrayForTopBars,
            overlineColor = WhiteBone
        ),
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
    )
}

