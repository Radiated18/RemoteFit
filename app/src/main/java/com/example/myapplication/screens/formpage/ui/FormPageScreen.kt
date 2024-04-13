package com.example.myapplication.screens.formpage.ui

import android.icu.util.MeasureUnit.Complexity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.outlined.Accessibility
import androidx.compose.material.icons.outlined.AccessibilityNew
import androidx.compose.material.icons.outlined.EmojiPeople
import androidx.compose.material.icons.outlined.Man2
import androidx.compose.material.icons.outlined.Woman2
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.screens.formpage.FormPageViewModel
import com.example.myapplication.models.SelectionOption
import com.example.myapplication.screens.navigation.ProjectScreens
import com.example.myapplication.ui.theme.GoldenOpaque
import com.example.myapplication.ui.theme.GrayDark
import com.example.myapplication.ui.theme.GrayForTopBars
import com.example.myapplication.ui.theme.RedCarmesi
import com.example.myapplication.ui.theme.WhiteBone
import com.example.myapplication.ui.theme.lusitanaBoldFont
import com.example.myapplication.ui.theme.lusitanaFont

@Composable
fun FormPageScreen(navController: NavController, viewModel: FormPageViewModel){
    BackgroundFormPageScreen()

    Box(modifier = Modifier.fillMaxSize()){
        Column() {
            TopAppBarBackFormScreen(navController)

            Spacer(modifier = Modifier.padding(15.dp))

            ItemsForColumnFormScreen(navController, viewModel)
        }


    }
}

@Composable
fun ItemsForColumnFormScreen(navController: NavController, viewModel: FormPageViewModel){
    val habitos_alValue: List<SelectionOption> = viewModel.habitos_al
    val frutas_porValue : List<SelectionOption> = viewModel.frutas_por
    val ejercicio_ratValue: List<SelectionOption> = viewModel.ejercicio_rat
    val ejercicio_xtimeValue : List<SelectionOption> = viewModel.ejercicio_xtime
    val objetive_selectionValue : List<SelectionOption> = viewModel.objetive_selection
    val journeyAvailable_selectionValue : List<SelectionOption> = viewModel.journeyAvailable_seleccion



    val enableRegisterValue by viewModel.enableRegister.observeAsState(initial = false)

    LazyColumn(userScrollEnabled = true
    ){
        item {
            PersonalDataQuest(viewModel)

        }
        //PARTE DE LOS HABITOS ALIMENTICIOS
        item {
            Spacer(modifier = Modifier.padding(20.dp))
            QuestWithTitleForm("2. Habitos Alimenticios", "a. ¿Con qué frecuencia consumes alimentos ricos en proteínas (carnes magras, legumbres, lácteos)?" )
        }

        items(habitos_alValue){ item ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .padding(horizontal = 25.dp)
                    .clickable(
                        true,
                        onClick = { viewModel.selectionOptionHabitsSelected(item) }),
                color = if (item.selected) { GoldenOpaque } else { GrayForTopBars },
                border = BorderStroke(1.dp, GoldenOpaque),
                shape = RoundedCornerShape(4.dp)
            ){
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                    Text(
                        text = item.option,
                        fontFamily = lusitanaFont,
                        color = WhiteBone,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                    )
                }
            }
            Spacer(modifier = Modifier.padding(5.dp))
        }

        item {
            Spacer(modifier = Modifier.padding(10.dp))
            QuestWithOnlyResumeForm("b. ¿Cuántas porciones de frutas consumes al día?")
        }

        items(frutas_porValue){ item ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .padding(horizontal = 25.dp)
                    .clickable(
                        true,
                        onClick = { viewModel.selectionOptionFrutasPorSelected(item) }),
                color = if (item.selected) { GoldenOpaque } else { GrayForTopBars },
                border = BorderStroke(1.dp, GoldenOpaque),
                shape = RoundedCornerShape(4.dp)
            ){
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Text(
                        text = item.option,
                        fontFamily = lusitanaFont,
                        color = WhiteBone,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                    )
                }

            }
            Spacer(modifier = Modifier.padding(5.dp))
        }

        //PARTE DE EJERCICIO Y ACTIVIDAD FISICA
        item {
            Spacer(modifier = Modifier.padding(20.dp))
            QuestWithTitleForm("3. Ejercicio Y Actividad Fisica", "a. ¿Con qué frecuencia realizas actividad física o ejercicio en una semana?" )
        }

        items(ejercicio_ratValue){ item ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .padding(horizontal = 25.dp)
                    .clickable(
                        true,
                        onClick = { viewModel.selectionOptionEjercicioRatSelected(item) }),
                color = if (item.selected) { GoldenOpaque } else { GrayForTopBars },
                border = BorderStroke(1.dp, GoldenOpaque),
                shape = RoundedCornerShape(4.dp)
            ){
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Text(
                        text = item.option,
                        fontFamily = lusitanaFont,
                        color = WhiteBone,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                    )
                }
            }
            Spacer(modifier = Modifier.padding(5.dp))
        }

        item {
            Spacer(modifier = Modifier.padding(10.dp))
            QuestWithOnlyResumeForm("b. ¿Cuánto tiempo aproximadamente dedicas a cada sesión de ejercicio?")
        }

        items(ejercicio_xtimeValue){ item ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .padding(horizontal = 25.dp)
                    .clickable(
                        true,
                        onClick = { viewModel.selectionOptionEjercicioTimePorSelected(item) }),
                color = if (item.selected) { GoldenOpaque } else { GrayForTopBars },
                border = BorderStroke(1.dp, GoldenOpaque),
                shape = RoundedCornerShape(4.dp)
            ){
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Text(
                        text = item.option,
                        fontFamily = lusitanaFont,
                        color = WhiteBone,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                    )
                }
            }
            Spacer(modifier = Modifier.padding(5.dp))
        }

        item {
            Spacer(modifier = Modifier.padding(10.dp))
            QuestWithOnlyResumeForm("¿Cual es tu objetivo a largo plazo?")
        }

        items(objetive_selectionValue){ item ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .padding(horizontal = 25.dp)
                    .clickable(
                        true,
                        onClick = { viewModel.selectionOptionObjetiveSelected(item) }),
                color = if (item.selected) { GoldenOpaque } else { GrayForTopBars },
                border = BorderStroke(1.dp, GoldenOpaque),
                shape = RoundedCornerShape(4.dp)
            ){
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Text(
                        text = item.option,
                        fontFamily = lusitanaFont,
                        color = WhiteBone,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                    )
                }
            }
            Spacer(modifier = Modifier.padding(5.dp))
        }

        item {
            Spacer(modifier = Modifier.padding(10.dp))
            QuestWithOnlyResumeForm("¿Como ingeniero/a de sistemas, cual es la jornada en la que cuenta con mayor disponibilidad?")
        }

        items(journeyAvailable_selectionValue){ item ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .padding(horizontal = 25.dp)
                    .clickable(
                        true,
                        onClick = { viewModel.selectionJourneyAvailableSelected(item) }),
                color = if (item.selected) { GoldenOpaque } else { GrayForTopBars },
                border = BorderStroke(1.dp, GoldenOpaque),
                shape = RoundedCornerShape(4.dp)
            ){
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Text(
                        text = item.option,
                        fontFamily = lusitanaFont,
                        color = WhiteBone,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                    )
                }
            }
            Spacer(modifier = Modifier.padding(5.dp))
        }

        item {
            Spacer(modifier = Modifier.padding(10.dp))
            ButtonForRegister(enableRegisterValue){
                viewModel.createAuth(){
                    navController.navigate(ProjectScreens.HomeScreen.name)

                }
            }
            Spacer(modifier = Modifier.padding(10.dp))

        }


    }
}

@Composable
fun ButtonForRegister(loginEnable: Boolean, function: () -> Unit){
    Button(
        onClick = {
            function()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = GoldenOpaque,
            disabledContainerColor = Color(0xFF73777F)
        ),
        enabled = loginEnable,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(horizontal = 80.dp)
            .fillMaxWidth()
            .height(45.dp)
    ) {
        Text(
            text = "Aceptar y Continuar",
            fontFamily = lusitanaBoldFont,
            color = WhiteBone,
            fontSize = 18.sp
        )
    }
}

@Composable
fun PersonalDataQuest(viewModel: FormPageViewModel){
    val physical_build: String by viewModel.physical_build.observeAsState(initial = "")//Nombre mutable que se escribe
    val weightValue: String by viewModel.weight.observeAsState(initial = "")//Nombre mutable que se escribe
    val heightValue: String by viewModel.height.observeAsState(initial = "")//Nombre mutable que se escribe

    Column {
        Text(
            text = "1. Datos Personales",
            fontFamily = lusitanaFont,
            color = WhiteBone,
            fontSize = 25.sp,
            textAlign = TextAlign.Start,
            lineHeight = 27.sp

        )
        Spacer(modifier = Modifier.padding(10.dp))

        TextFieldBodyTypeRegister(viewModel, "Complexion Fisica", "Seleccion Tu Tipo de Cuerpo"){
            viewModel.onTextFieldChangePersonalData(it, weightValue, heightValue)
        }
        Spacer(modifier = Modifier.padding(10.dp))
        TextFieldNumberFormScreen(weightValue, "Peso", "Ingresa tu Peso (Kg)", "Kg"){
            viewModel.onTextFieldChangePersonalData(physical_build, it, heightValue)

        }
        Spacer(modifier = Modifier.padding(10.dp))
        TextFieldNumberFormScreen(heightValue, "Altura", "Ingresa tu Altura (cm)", "cm"){
            viewModel.onTextFieldChangePersonalData(physical_build, weightValue, it)

        }
    }



}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldBodyTypeRegister(viewModel: FormPageViewModel,
                         labelText:String,
                         placeholderText: String,
                         onTextFieldChanged: (String) -> Unit){

    var expanded by remember { mutableStateOf(false) }
    var ComplexitySelect by remember { mutableStateOf("") }


    TextField(
        value = ComplexitySelect, //Atributo que se requiere para el cambio
        onValueChange = { onTextFieldChanged(it) },
        label = { Text(text = labelText, fontFamily = lusitanaFont) },
        placeholder = { Text(text = placeholderText, fontFamily = lusitanaFont) },
        readOnly = true,
        colors = ExposedDropdownMenuDefaults.textFieldColors(
            focusedContainerColor = GrayDark.copy(alpha = 0.9f),
            unfocusedContainerColor = GrayDark.copy(alpha = 0.9f),
            focusedTextColor = WhiteBone,
            unfocusedTextColor = WhiteBone,
            focusedIndicatorColor = GoldenOpaque,
            unfocusedIndicatorColor = GoldenOpaque,
            cursorColor = GoldenOpaque,
            focusedLabelColor = GoldenOpaque,
            unfocusedLabelColor = WhiteBone,
            focusedPlaceholderColor = WhiteBone,
            unfocusedPlaceholderColor = WhiteBone,
            //Colores para los errores
            errorContainerColor = GrayDark.copy(alpha = 0.9f),
            errorTextColor = WhiteBone,
            errorCursorColor = RedCarmesi,
            errorLabelColor = RedCarmesi
        ),
        trailingIcon = {
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Default.ArrowDownward,
                    contentDescription = "",
                    tint = WhiteBone
                )
            }
        },
        modifier = Modifier
            .padding(horizontal = 25.dp)
            .fillMaxWidth()
    )
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        offset = DpOffset(500.dp, (-140).dp)
    ) {
        DropdownMenuItem(
            text = { Text("Ectomorfo") },
            onClick = {
                ComplexitySelect = "Ectomorfo"
                expanded = false
                viewModel.changePhysicalValue(ComplexitySelect)
            },
            trailingIcon = {
                Image(painter = painterResource(
                    id = R.drawable.ectomorfo),
                    contentDescription = "Icon",
                    modifier = Modifier.fillMaxSize(0.2f)

                )
            })

        DropdownMenuItem(
            text = { Text("Endomorfo") },
            onClick = {
                ComplexitySelect = "Endomorfo"
                expanded = false
                viewModel.changePhysicalValue(ComplexitySelect)
            },
            trailingIcon = {
                Image(painter = painterResource(
                    id = R.drawable.endomorfo),
                    contentDescription = "Icon",
                    modifier = Modifier.fillMaxSize(0.2f)
                )
            })
        DropdownMenuItem(
            text = { Text("Mesomorfo") },
            onClick = {
                ComplexitySelect = "Mesomorfo"
                expanded = false
                viewModel.changePhysicalValue(ComplexitySelect)
            },
            trailingIcon = {
                Image(painter = painterResource(
                    id = R.drawable.mesomorfo),
                    contentDescription = "Icon",
                    modifier = Modifier.fillMaxSize(0.2f)

                )
            })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldNumberFormScreen(atributeRequired: String,
                            labelText:String,
                            placeholderText: String,
                              suffix: String,
                            onTextFieldChanged: (String) -> Unit){
    TextField(
        value = atributeRequired, //Atributo que se requiere para el cambio
        onValueChange = { onTextFieldChanged(it) },
        label = { Text(text = labelText, fontFamily = lusitanaFont) },
        placeholder = { Text(text = placeholderText, fontFamily = lusitanaFont) },
        colors = ExposedDropdownMenuDefaults.textFieldColors(
            focusedContainerColor = GrayDark.copy(alpha = 0.9f),
            unfocusedContainerColor = GrayDark.copy(alpha = 0.9f),
            focusedTextColor = WhiteBone,
            unfocusedTextColor = WhiteBone,
            focusedIndicatorColor = GoldenOpaque,
            unfocusedIndicatorColor = GoldenOpaque,
            cursorColor = GoldenOpaque,
            focusedLabelColor = GoldenOpaque,
            unfocusedLabelColor = WhiteBone,
            focusedPlaceholderColor = WhiteBone,
            unfocusedPlaceholderColor = WhiteBone
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.NumberPassword
        ),
        suffix = {
            Text(
                text = suffix,
                fontFamily = lusitanaFont,
                color = WhiteBone,
                textAlign = TextAlign.Start,
            )

        },
        modifier = Modifier
            .padding(horizontal = 25.dp)
            .fillMaxWidth()
    )
}

@Composable
fun QuestWithTitleForm(title1: String, resumeQuest: String) {
    Text(
        text = title1,
        fontFamily = lusitanaFont,
        color = WhiteBone,
        fontSize = 25.sp,
        textAlign = TextAlign.Start,
        lineHeight = 27.sp,
        modifier = Modifier.padding(start = 10.dp, end = 25.dp)
    )
    Spacer(modifier = Modifier.padding(8.dp))
    Text(
        text = resumeQuest,
        fontFamily = lusitanaFont,
        color = WhiteBone,
        fontSize = 20.sp,
        textAlign = TextAlign.Start,
        lineHeight = 21.sp,
        modifier = Modifier.padding(start = 18.dp, end = 25.dp)

    )
    Spacer(modifier = Modifier.padding(8.dp))

}

@Composable
fun QuestWithOnlyResumeForm(resumeQuest: String) {
    Text(
        text = resumeQuest,
        fontFamily = lusitanaFont,
        color = WhiteBone,
        fontSize = 20.sp,
        textAlign = TextAlign.Start,
        lineHeight = 21.sp,
        modifier = Modifier.padding(start = 18.dp, end = 25.dp)

    )
    Spacer(modifier = Modifier.padding(8.dp))
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarBackFormScreen(navController: NavController){
    TopAppBar(
        title = {
            Text(
                text ="Formulario Inicial",
                fontFamily = lusitanaFont,
                fontSize = 22.sp,
                overflow = TextOverflow.Clip
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.navigateUp()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
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
        )
    )
}

@Composable
fun BackgroundFormPageScreen(){
    Image(painter = painterResource(
        id = R.drawable.formscreenbackground),
        contentDescription = "Background",
        alpha = 0.95f,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    )
}