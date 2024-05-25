package com.grl.clientapptfg.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.grl.clientapptfg.R
import com.grl.clientapptfg.ui.components.LogoApp
import com.grl.clientapptfg.ui.components.PersonalizedDivider
import com.grl.clientapptfg.ui.components.ProgressBarDialog
import com.grl.clientapptfg.ui.components.TextFieldForPasswordPersonalized
import com.grl.clientapptfg.ui.components.TextFieldPersonalized
import com.grl.clientapptfg.ui.screens.profile.ProfileViewModel
import com.grl.clientapptfg.ui.theme.black
import com.grl.clientapptfg.ui.theme.blackSoft
import com.grl.clientapptfg.ui.theme.mostaza
import com.grl.clientapptfg.ui.theme.mostazaSoft
import com.grl.clientapptfg.ui.theme.white
import com.grl.clientapptfg.utils.Util

@Composable
fun LoginScreen(loginViewModel: LoginViewModel, profileViewModel: ProfileViewModel) {
    val email by loginViewModel.email.observeAsState("")
    val password by loginViewModel.password.observeAsState("")
    val visibility by loginViewModel.isVisible.observeAsState(initial = false)
    val isLoginEnable by loginViewModel.isLoginEnable.observeAsState(initial = false)
    val isLoading by loginViewModel.isLoading.observeAsState(initial = false)
    val logedIsBad by loginViewModel.logedIsBad.observeAsState(initial = false)
    val dividerText by loginViewModel.dividerText.observeAsState(initial = "O")
    val aladinFont = Util.loadFontFamilyFromAssets()
    ConstraintLayout(
        Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.ic_good_logo_background))
    ) {
        val (appImage, title, newAcc, divider, fields) = createRefs()
        val topGuide = createGuidelineFromTop(0.03f)
        val startGuide = createGuidelineFromStart(0.05f)
        val endGuide = createGuidelineFromEnd(0.05f)
        if (isLoading) {
            ProgressBarDialog()
        }
        LogoApp(modifier = Modifier.constrainAs(appImage) {
            top.linkTo(topGuide)
            start.linkTo(startGuide)
        })
        Text(
            text = "Bienvenido",
            Modifier
                .width(200.dp)
                .constrainAs(title) {
                    top.linkTo(topGuide)
                    start.linkTo(startGuide)
                    end.linkTo(endGuide)
                    bottom.linkTo(appImage.bottom)
                },
            fontWeight = FontWeight.Bold,
            fontSize = 50.sp,
            fontFamily = aladinFont,
            color = white
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 35.dp)
                .constrainAs(newAcc) {
                    top.linkTo(title.bottom)
                    end.linkTo(endGuide)
                    start.linkTo(startGuide)
                }, horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Accede o crea ",
                fontSize = 25.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = aladinFont,
                color = white
            )
            Text(
                text = "una nueva cuenta",
                Modifier
                    .align(Alignment.CenterVertically)
                    .clickable { profileViewModel.setScreenState(3) },
                fontSize = 25.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = aladinFont,
                textDecoration = TextDecoration.Underline,
                color = mostaza
            )
        }
        PersonalizedDivider(
            modifier = Modifier
                .constrainAs(divider) {
                    top.linkTo(newAcc.bottom)
                    start.linkTo(startGuide)
                    end.linkTo(endGuide)
                }, text = dividerText,
            color = logedIsBad
        )
        Column(Modifier.constrainAs(fields) {
            top.linkTo(divider.bottom)
            start.linkTo(startGuide)
            end.linkTo(endGuide)
        }) {
            TextFieldPersonalized(
                value = email,
                function = {
                    loginViewModel.setEmail(it)
                    loginViewModel.enableLogin(email, password)
                },
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 50.dp, bottom = 40.dp)
                    .fillMaxWidth()
                    .height(70.dp),
                placeholder = "Correo electrónico",
                keyboardType = KeyboardType.Email,
                ImeAction.Next
            )
            TextFieldForPasswordPersonalized(
                value = password,
                changeText = {
                    loginViewModel.setPassword(it)
                    loginViewModel.enableLogin(email, password)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(70.dp),
                imeAction = ImeAction.Default,
                isVisible = visibility,
                changeVisibility = { loginViewModel.changeVisibility(visibility) }
            )
            Text(
                text = "La contraseña tiene ${password.length} caracteres de los 7 mínimos",
                Modifier
                    .padding(horizontal = 28.dp)
                    .padding(bottom = 60.dp), fontFamily = aladinFont, color = mostaza
            )
            Button(
                onClick = {
                    loginViewModel.getUser { succes ->
                        if (succes) {
                            profileViewModel.setScreenState(2)
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
                enabled = isLoginEnable
            ) {
                Text(
                    text = "Iniciar Sesión",
                    fontFamily = aladinFont,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = "¿Has olvidado tu contraseña?",
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable { }
                    .padding(top = 20.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = aladinFont,
                textDecoration = TextDecoration.Underline,
                color = white
            )
        }
    }
}