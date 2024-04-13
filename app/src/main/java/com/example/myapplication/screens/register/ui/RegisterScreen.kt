package com.example.myapplication.screens.register.ui

import android.provider.ContactsContract.Intents.Insert
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Man
import androidx.compose.material.icons.outlined.Man2
import androidx.compose.material.icons.outlined.Woman
import androidx.compose.material.icons.outlined.Woman2
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.textFieldColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.screens.register.RegisterViewModel
import com.example.myapplication.ui.theme.BackGroundColor
import com.example.myapplication.ui.theme.GoldenOpaque
import com.example.myapplication.ui.theme.GrayDark
import com.example.myapplication.ui.theme.GrayForTopBars
import com.example.myapplication.ui.theme.WhiteBone
import com.example.myapplication.ui.theme.lusitanaFont
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import com.example.myapplication.screens.navigation.ProjectScreens
import com.example.myapplication.ui.theme.RedCarmesi
import com.example.myapplication.ui.theme.lusitanaBoldFont
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId

@Composable
fun RegisterScreen(navController: NavController, viewModel: RegisterViewModel){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(BackGroundColor)
    ){
        TopAppBarBack(navController)
        ColumnInputsData(navController, RegisterViewModel())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarBack(navController: NavController){

    TopAppBar(
        title = {
            Text(
                text ="Nueva Cuenta",
                fontFamily = lusitanaFont,
                fontSize = 26.sp,
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
            actionIconContentColor = GrayDark)
    )
}

@Composable
fun ColumnInputsData(navController: NavController, viewModel: RegisterViewModel){
//Variables traidas desde el ViewModel
    val nameUser: String by viewModel.name.observeAsState(initial = "")//Nombre mutable que se escribe
    val email: String by viewModel.email.observeAsState(initial = "") //Email Mutable que se escribe
    val birthDate: String by viewModel.birthDate.observeAsState(initial = "") //Email Mutable que se escribe
    val password: String by viewModel.password.observeAsState(initial = "") //Password Mutable que se escribe
    val confirmPassword: String by viewModel.confirmPassword.observeAsState(initial = "") //ConfirmPassword Mutable que se escribe
    val phoneNumberUser: String by viewModel.phoneNumber.observeAsState(initial = "") //Numero de telefono que se escribe
    val sexGender: String by viewModel.sexGender.observeAsState(initial = "") //Sexo que se elige
    val registerEnable: Boolean by viewModel.registerEnable.observeAsState(initial = false)

    //Variables para poder activar o desactivar el password
    val passwordVisible1: Boolean by viewModel.passwordVisible1.observeAsState(initial = false) //ConfirmPassword Mutable que se escribe
    val passwordVisible2: Boolean by viewModel.passwordVisible2.observeAsState(initial = false) //ConfirmPassword Mutable que se escribe

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.padding(45.dp))

        TextFieldCommonRegister(nameUser, "Nombre Y Apellido", "Ingresa Tu Nombre Completo"){
            viewModel.onTextFieldChange(it, email, birthDate,password,confirmPassword,phoneNumberUser,sexGender)
        }
        Spacer(modifier = Modifier.padding(10.dp))

        TextFieldCommonRegister(email, "Correo Electronico", "Ingresa Tu Correo Electronico"){
            viewModel.onTextFieldChange(nameUser, it, birthDate, password, confirmPassword, phoneNumberUser,sexGender)
        }
        Spacer(modifier = Modifier.padding(10.dp))

        //Fecha de Nacimiento
        BirthDateTextField(viewModel, "Fecha de Nacimiento", "Seleccion tu Fecha de Nacimiento"){
            viewModel.onTextFieldChange(nameUser, email, it, password, confirmPassword, phoneNumberUser, sexGender)
        }
        Spacer(modifier = Modifier.padding(10.dp))

        TextPasswordCommonField(password,
            passwordVisible = passwordVisible1,
            "Crear Contraseña", "Ingresa tu Contraseña",
            funShowPassword = { viewModel.changePasswordVisible1(passwordVisible1) },
        ){
            viewModel.onTextFieldChange(nameUser, email, birthDate, it, confirmPassword, phoneNumberUser, sexGender)
        }
        Spacer(modifier = Modifier.padding(2.dp))

        TextPasswordCommonField(confirmPassword,
            passwordVisible = passwordVisible2,
            "Confirmar Contraseña",
            "Confirma tu Contraseña",
            funShowPassword = { viewModel.changePasswordVisible2(passwordVisible2) },
        ){
            viewModel.onTextFieldChange(nameUser, email, birthDate, password, it, phoneNumberUser, sexGender)
        }
        Spacer(modifier = Modifier.padding(2.dp))

        TextFieldSexRegister(viewModel, "Sexo", "Selecciona Tu Sexo"){
            viewModel.onTextFieldChange(nameUser, email, birthDate, password, confirmPassword, phoneNumberUser, it)
        }
        Spacer(modifier = Modifier.padding(10.dp))


        TextFieldNumberRegister(phoneNumberUser, "Numero de Telefono", "Ingresa tu Numero de Telefono", viewModel){
            viewModel.onTextFieldChange(nameUser, email, birthDate, password, confirmPassword, it, sexGender)
        }

        Spacer(modifier = Modifier.padding(1.dp))

        //CAMBIAR POR registerEnable
        RegisterButton(enabled = registerEnable ) {

            RegisterViewModel.userTemp = viewModel.createTempMap()

            navController.navigate(ProjectScreens.WelcomeRegisterScreen.name)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldCommonRegister(atributeRequired: String,
                            labelText:String,
                            placeholderText: String,
                            onTextFieldChanged: (String) -> Unit){
    TextField(
        value = atributeRequired, //Atributo que se requiere para el cambio
        onValueChange = { onTextFieldChanged(it) },
        label = { Text(text = labelText, fontFamily = lusitanaFont) },
        placeholder = { Text(text = placeholderText, fontFamily = lusitanaFont) },
        colors = textFieldColors(
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
        modifier = Modifier
            .padding(horizontal = 25.dp)
            .fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldNumberRegister(atributeRequired: String,
                            labelText:String,
                            placeholderText: String,
                            viewModel: RegisterViewModel,
                            onTextFieldChanged: (String) -> Unit){

    var isValidNumber by remember { mutableStateOf(false) }


    TextField(
        value = atributeRequired, //Atributo que se requiere para el cambio
        onValueChange = { onTextFieldChanged(it)
                        isValidNumber = !(it.isBlank() || it.length > 6)
                        },
        label = { Text(text = labelText, fontFamily = lusitanaFont) },
        placeholder = { Text(text = placeholderText, fontFamily = lusitanaFont) },
        colors = textFieldColors(
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
        isError = isValidNumber,
        supportingText = {
            if(isValidNumber){
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Debe de ser un numero valido",
                    fontFamily = lusitanaFont,
                    textAlign = TextAlign.End,
                )
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.NumberPassword
        ),
        modifier = Modifier
            .padding(horizontal = 25.dp)
            .fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldSexRegister(viewModel: RegisterViewModel,
                         labelText:String,
                         placeholderText: String,
                         onTextFieldChanged: (String) -> Unit){

    var expanded by remember { mutableStateOf(false) }
    var sexSelect by remember { mutableStateOf("") }


    TextField(
        value = sexSelect, //Atributo que se requiere para el cambio
        onValueChange = { onTextFieldChanged(it) },
        label = { Text(text = labelText, fontFamily = lusitanaFont) },
        placeholder = { Text(text = placeholderText, fontFamily = lusitanaFont) },
        readOnly = true,
        colors = textFieldColors(
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
        trailingIcon = {
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Default.ArrowDownward,
                    contentDescription = "",
                    tint = WhiteBone
                )
            }
        },
        maxLines = 1,
        modifier = Modifier
            .padding(horizontal = 25.dp)
            .fillMaxWidth()
    )
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
    ) {
        DropdownMenuItem(
            text = { Text("Femenino") },
            onClick = {
                sexSelect = "Femenino"
                expanded = false
                viewModel.changeSexValue(sexSelect)
            },
            trailingIcon = {
                Icon(
                    Icons.Outlined.Woman2,
                    contentDescription = null,
                    tint = GoldenOpaque
                )
            })

        DropdownMenuItem(
            text = { Text("Masculino") },
            onClick = {
                sexSelect = "Masculino"
                expanded = false
                viewModel.changeSexValue(sexSelect)
            },
            trailingIcon = {
                Icon(
                    Icons.Outlined.Man2,
                    contentDescription = null,
                    tint = GoldenOpaque
                )
            })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextPasswordCommonField(atributeRequired: String,
                            passwordVisible: Boolean,
                            labelText:String,
                            placeholderText: String,
                            funShowPassword: () -> Unit,
                            onTextFieldChanged: (String) -> Unit){
    //Variables

    //Para Cambio Visual
    val visualTransformation = if (passwordVisible)
        VisualTransformation.None
    else PasswordVisualTransformation()

    var boolError by remember { mutableStateOf(false) }


    TextField(
        value = atributeRequired,
        onValueChange = {
            onTextFieldChanged(it)
            boolError = !(it.isBlank() || it.length > 6)
                        },
        label = { Text(text = labelText, fontFamily = lusitanaFont) },
        placeholder = { Text(text = placeholderText, fontFamily = lusitanaFont) },
        visualTransformation = visualTransformation,
        isError = boolError,
        colors = textFieldColors(
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
        supportingText = {
            if (boolError){
                Text(text = "La contraseña debe de ser mayor a 6", fontFamily = lusitanaFont)
            }
        },
        trailingIcon = {
            if(atributeRequired.isNotEmpty()){
                PasswordVisibleIcon(passwordVisible, funShowPassword)
            }
        },
        modifier = Modifier
            .padding(horizontal = 25.dp)
            .fillMaxWidth()
    )

}

@Composable
fun PasswordVisibleIcon(passwordVisible: Boolean, funShowPassword: () -> Unit) {
    val image =
        if (passwordVisible)
            Icons.Default.VisibilityOff
        else Icons.Default.Visibility
    IconButton(onClick = { funShowPassword() }) {
        Icon(
            imageVector = image,
            contentDescription = "",
            tint = WhiteBone
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthDateTextField(
    viewModel: RegisterViewModel,
    labelText: String,
    placeholderText: String,
    onTextFieldChanged: (String) -> Unit
) {
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = Instant.now().minusMillis(568099900000).toEpochMilli())
    var showDialog by remember { mutableStateOf(false) }
    var dateShow by remember { mutableStateOf("") }

    val date = datePickerState.selectedDateMillis
    date?.let {
        val localDate = Instant.ofEpochMilli(it).atZone(ZoneId.of("UTC")).toLocalDate()
        dateShow = "${localDate.dayOfMonth}/${localDate.monthValue}/${localDate.year}"

        viewModel.changeDateValue(localDate.toString())
    }



    TextField(
        value = dateShow,
        onValueChange = { onTextFieldChanged(it) },
        label = { Text(text = labelText, fontFamily = lusitanaFont) },
        placeholder = { Text(text = placeholderText, fontFamily = lusitanaFont) },
        colors = textFieldColors(
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
        trailingIcon = {
            IconButton(onClick = { showDialog = true }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "",
                    tint = WhiteBone
                )
            }
        },
        readOnly = true,
        modifier = Modifier
            .padding(horizontal = 25.dp)
            .fillMaxWidth()
    )

    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = true },
            confirmButton = {
                Button(onClick = { showDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GoldenOpaque)
                ) {
                    Text(
                        text = "Confirmar",
                        fontFamily = lusitanaBoldFont,
                        color = WhiteBone,
                        fontSize = 18.sp
                    )
                }
            }
        ) {
            DatePicker(state = datePickerState,
                dateValidator = { timestamp ->
                    // Disable all the days before today
                    timestamp < Instant.now().minusMillis(568099999999).toEpochMilli()
                })
        }
    }
}

@Composable
fun RegisterButton(enabled: Boolean, function: () -> Unit){

    Button(
        onClick = {
            function()
        },
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = GoldenOpaque,
            disabledContainerColor = Color(0xFF73777F)
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(horizontal = 80.dp)
            .fillMaxWidth()
            .height(45.dp)

        ) {
        Text(
            text = "Registrarse",
            fontFamily = lusitanaBoldFont,
            color = WhiteBone,
            fontSize = 18.sp
        )
    }
}
