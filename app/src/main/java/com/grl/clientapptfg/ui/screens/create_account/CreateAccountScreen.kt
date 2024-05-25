package com.grl.clientapptfg.ui.screens.create_account

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.grl.clientapptfg.ui.components.ConfirmationDialog
import com.grl.clientapptfg.ui.components.LogoApp
import com.grl.clientapptfg.ui.components.ProgressBarDialog
import com.grl.clientapptfg.ui.components.TextFieldForPasswordPersonalized
import com.grl.clientapptfg.ui.components.TextFieldPersonalized
import com.grl.clientapptfg.ui.screens.profile.ProfileViewModel
import com.grl.clientapptfg.ui.theme.black
import com.grl.clientapptfg.ui.theme.blackSoft
import com.grl.clientapptfg.ui.theme.granate
import com.grl.clientapptfg.ui.theme.mostaza
import com.grl.clientapptfg.ui.theme.mostazaSoft
import com.grl.clientapptfg.ui.theme.white
import com.grl.clientapptfg.utils.Util

@Composable
fun CreateAccountScreen(
    createAccountViewModel: CreateAccountViewModel,
    profileViewModel: ProfileViewModel
) {
    val aladinFont = Util.loadFontFamilyFromAssets()
    val name = createAccountViewModel.name.observeAsState(initial = "")
    val email = createAccountViewModel.email.observeAsState(initial = "")
    val password = createAccountViewModel.password.observeAsState(initial = "")
    val phone = createAccountViewModel.phone.observeAsState(initial = "")
    val address = createAccountViewModel.addres.observeAsState(initial = "")
    val visibility = createAccountViewModel.isVisible.observeAsState(initial = false)
    val enable = createAccountViewModel.isEnable.observeAsState(initial = false)
    val isLoading = createAccountViewModel.isLoading.observeAsState(initial = false)
    val createAccountSuccess =
        createAccountViewModel.createAccountSuccess.observeAsState(initial = false)
    val createAccountFailed =
        createAccountViewModel.createAccountFailed.observeAsState(initial = false)
    ConstraintLayout(
        Modifier
            .fillMaxSize()
            .background(granate)
    ) {
        val (title, appImage, icon, body) = createRefs()
        val topGuide = createGuidelineFromTop(0.03f)
        val startGuide = createGuidelineFromStart(0.05f)
        val endGuide = createGuidelineFromEnd(0.05f)

        if (isLoading.value) {
            ProgressBarDialog()
        }

        if (createAccountSuccess.value) {
            ConfirmationDialog(
                title = "Cuenta creada con éxito",
                text = "Pulsa el botón aceptar para ir a la pantalla de iniciar sesión",
                onClick = {
                    createAccountViewModel.changeAccountSuccess(false)
                    profileViewModel.setScreenState(1)
                })
        }

        if (createAccountFailed.value) {
            ConfirmationDialog(
                title = "Oh... Parece que este correo ya esta usado",
                text = "Prueba a usar otro correo diferente. Pulsa aceptar para volver",
                onClick = {
                    createAccountViewModel.changeAccountFailed(false)
                })
        }

        LogoApp(modifier = Modifier.constrainAs(appImage) {
            top.linkTo(topGuide)
            start.linkTo(startGuide)
        })
        Text(
            text = "Crear cuenta",
            Modifier
                .padding(bottom = 40.dp)
                .constrainAs(title) {
                    top.linkTo(topGuide)
                    start.linkTo(startGuide)
                    end.linkTo(endGuide)
                    bottom.linkTo(body.top)
                },
            fontWeight = FontWeight.Bold,
            fontSize = 50.sp,
            fontFamily = aladinFont,
            color = white
        )
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = "Icono de cierre",
            tint = mostaza,
            modifier = Modifier
                .size(35.dp)
                .constrainAs(icon) {
                    top.linkTo(topGuide)
                    end.linkTo(endGuide)
                    bottom.linkTo(appImage.bottom)
                }
                .clickable {
                    createAccountViewModel.cleanFields()
                    profileViewModel.setScreenState(1)
                })
        Column(
            Modifier
                .fillMaxSize()
                .constrainAs(body) {
                    top.linkTo(title.bottom)
                    start.linkTo(startGuide)
                    end.linkTo(endGuide)
                }) {
            TextFieldPersonalized(
                value = name.value,
                function = {
                    createAccountViewModel.setName(it)
                    createAccountViewModel.enableButton(
                        email = email.value,
                        password = password.value,
                        phone = phone.value,
                        address = address.value,
                        name = name.value
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(90.dp)
                    .padding(bottom = 20.dp),
                placeholder = "Nombre",
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
            TextFieldPersonalized(
                value = phone.value,
                function = {
                    createAccountViewModel.setPhone(it)
                    createAccountViewModel.enableButton(
                        email = email.value,
                        password = password.value,
                        phone = phone.value,
                        address = address.value,
                        name = name.value
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(90.dp)
                    .padding(bottom = 20.dp),
                placeholder = "Número de teléfono",
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            )
            TextFieldPersonalized(
                value = address.value,
                function = {
                    createAccountViewModel.setAddress(it)
                    createAccountViewModel.enableButton(
                        email = email.value,
                        password = password.value,
                        phone = phone.value,
                        address = address.value,
                        name = name.value
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(90.dp)
                    .padding(bottom = 20.dp),
                placeholder = "Dirección",
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
            TextFieldPersonalized(
                value = email.value,
                function = {
                    createAccountViewModel.setEmail(it)
                    createAccountViewModel.enableButton(
                        email = email.value,
                        password = password.value,
                        phone = phone.value,
                        address = address.value,
                        name = name.value
                    )
                },
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .height(90.dp)
                    .padding(bottom = 20.dp),
                placeholder = "Correo electrónico",
                keyboardType = KeyboardType.Email,
                ImeAction.Next
            )
            TextFieldForPasswordPersonalized(
                value = password.value,
                changeText = {
                    createAccountViewModel.setPassword(it)
                    createAccountViewModel.enableButton(
                        email = email.value,
                        password = password.value,
                        phone = phone.value,
                        address = address.value,
                        name = name.value
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(70.dp),
                imeAction = ImeAction.Default,
                isVisible = visibility.value,
                changeVisibility = { createAccountViewModel.changeVisibility(visibility.value) }
            )
            Text(
                text = "La contraseña tiene ${password.value.length} caracteres de los 7 mínimos",
                Modifier
                    .padding(horizontal = 28.dp)
                    .padding(bottom = 30.dp),
                fontFamily = aladinFont,
                color = mostaza
            )
            Button(
                onClick = {
                    createAccountViewModel.createNewUser { succes ->
                        if (succes) {
                            createAccountViewModel.changeAccountSuccess(true)
                        }else{
                            createAccountViewModel.changeAccountFailed(true)
                        }
                    }
                },
                Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(horizontal = 20.dp),
                colors = ButtonColors(
                    contentColor = black,
                    containerColor = mostaza,
                    disabledContainerColor = mostazaSoft,
                    disabledContentColor = blackSoft
                ),
                enabled = enable.value
            ) {
                Text(
                    text = "Crear Cuenta",
                    fontFamily = aladinFont,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
    BackHandler {
        createAccountViewModel.cleanFields()
        profileViewModel.setScreenState(1)
    }
}