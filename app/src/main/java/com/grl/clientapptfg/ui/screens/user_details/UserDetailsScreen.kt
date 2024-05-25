package com.grl.clientapptfg.ui.screens.user_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.grl.clientapptfg.core.UserSession
import com.grl.clientapptfg.ui.components.ConfirmationDialog
import com.grl.clientapptfg.ui.components.ConfirmationDialogWithNegative
import com.grl.clientapptfg.ui.components.ProgressBarDialog
import com.grl.clientapptfg.ui.components.StyledText
import com.grl.clientapptfg.ui.components.TextAreaPersonalized
import com.grl.clientapptfg.ui.components.TextFieldPersonalized
import com.grl.clientapptfg.ui.screens.profile.ProfileViewModel
import com.grl.clientapptfg.ui.screens.tabs_menu.TabsMenuViewModel
import com.grl.clientapptfg.ui.screens.tracking.TrackingViewModel
import com.grl.clientapptfg.ui.theme.black
import com.grl.clientapptfg.ui.theme.blackSoft
import com.grl.clientapptfg.ui.theme.darkRed
import com.grl.clientapptfg.ui.theme.granate
import com.grl.clientapptfg.ui.theme.mostaza
import com.grl.clientapptfg.ui.theme.mostazaSoft
import com.grl.clientapptfg.ui.theme.white
import com.grl.clientapptfg.utils.Util

@Composable
fun UserDetailsScreen(
    userDetailsViewModel: UserDetailsViewModel,
    profileViewModel: ProfileViewModel,
    tabsMenuViewModel: TabsMenuViewModel,
    trackingViewModel: TrackingViewModel
) {
    val aladinFont = Util.loadFontFamilyFromAssets()
    val user = UserSession.getUser()!!
    val closeSession by userDetailsViewModel.closeSession.observeAsState(initial = false)
    val address by userDetailsViewModel.address.observeAsState(user.address)
    val phone by userDetailsViewModel.phone.observeAsState(initial = user.phone)
    val isUpdateEnable by userDetailsViewModel.isUpdateEnable.observeAsState(initial = true)
    val isEditing by userDetailsViewModel.isEditing.observeAsState(false)
    val updateWasGood by userDetailsViewModel.updateWasGood.observeAsState(initial = false)
    val isLoading by userDetailsViewModel.isLoading.observeAsState(false)
    val wantToUpdate by userDetailsViewModel.wantToUpdate.observeAsState(false)

    if (closeSession) {
        ConfirmationDialogWithNegative(
            onPositive = {
                userDetailsViewModel.changeCloseSession(false)
                UserSession.setUser(null)
                tabsMenuViewModel.cleanList()
                trackingViewModel.cleanOrderList()
                tabsMenuViewModel.updateTotalPrice()
                profileViewModel.setScreenState(1)
            },
            title = "¿Estas seguro de cerrar sesión?",
            text = "Pulsa aceptar para salir",
            onNegative = { userDetailsViewModel.changeCloseSession(false) })
    }

    if (wantToUpdate) {
        ConfirmationDialogWithNegative(
            onPositive = {
                userDetailsViewModel.updateUser(address, phone, user)
                userDetailsViewModel.changeWantToUpdate(false)
            },
            title = "¿Estas seguro de que quieres actualizar?",
            text = "Pulsa aceptar para actualizar"
        ) {
            userDetailsViewModel.changeWantToUpdate(false)
        }
    }

    if (isLoading) {
        ProgressBarDialog()
    }

    if (updateWasGood) {
        ConfirmationDialog(
            onClick = {
                userDetailsViewModel.changeIsEditing(false)
                userDetailsViewModel.changeUpdateWasGood(false)
            },
            title = "La cuenta ha sido actualizada con éxito",
            text = "Pulsa aceptar para continuar"
        )
    }

    ConstraintLayout(
        Modifier
            .fillMaxSize()
            .background(granate)
    ) {
        val (title, divider, updateBtn, closeBtn, userName,
            userAdd, userTelf, userEmail, userAddField, userTelfField) = createRefs()
        val topGuide = createGuidelineFromTop(0.03f)
        val bottomGuide = createGuidelineFromBottom(0.02f)
        val startGuide = createGuidelineFromStart(0.02f)
        val endGuide = createGuidelineFromEnd(0.02f)

        Text(
            text = "Detalles de mi cuenta",
            fontWeight = FontWeight.Bold,
            fontSize = 50.sp,
            fontFamily = aladinFont,
            color = white,
            modifier = Modifier
                .padding(bottom = 10.dp)
                .constrainAs(title) {
                    top.linkTo(topGuide)
                    start.linkTo(startGuide)
                    end.linkTo(endGuide)
                }
        )
        HorizontalDivider(
            Modifier
                .constrainAs(divider) {
                    top.linkTo(title.bottom)
                }
                .padding(horizontal = 20.dp)
                .padding(bottom = 30.dp),
            color = mostaza,
            thickness = 3.dp
        )
        StyledText(
            fieldText = "- Nombre: ",
            valueText = user.name,
            modifier = Modifier
                .padding(bottom = 25.dp)
                .constrainAs(userName) {
                    top.linkTo(divider.bottom)
                    start.linkTo(startGuide)
                })
        StyledText(
            fieldText = "- Correo: ",
            valueText = user.gmail,
            modifier = Modifier
                .padding(bottom = 30.dp)
                .constrainAs(userEmail) {
                    top.linkTo(userName.bottom)
                    start.linkTo(startGuide)
                })
        Text(
            text = "- Teléfono:",
            color = mostaza,
            fontFamily = aladinFont,
            fontSize = 35.sp,
            modifier = Modifier
                .padding(end = 5.dp)
                .constrainAs(userTelf) {
                    top.linkTo(userEmail.bottom)
                    start.linkTo(startGuide)
                })
        TextFieldPersonalized(
            value = phone,
            function = {
                userDetailsViewModel.setPhone(it)
                userDetailsViewModel.enableUpdate(address, phone)
            },
            modifier = Modifier
                .height(60.dp)
                .constrainAs(userTelfField) {
                    top.linkTo(userTelf.top)
                    bottom.linkTo(userTelf.bottom)
                    start.linkTo(userTelf.end)
                    end.linkTo(endGuide)
                    width = Dimension.fillToConstraints
                },
            placeholder = "Debe tener 9 dígitos",
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Next,
            enabled = isEditing
        )
        Text(
            text = "- Dirección:",
            color = mostaza,
            fontFamily = aladinFont,
            fontSize = 35.sp,
            modifier = Modifier
                .padding(top = 25.dp)
                .constrainAs(userAdd) {
                    top.linkTo(userTelfField.bottom)
                    start.linkTo(startGuide)
                })
        TextAreaPersonalized(
            value = address,
            function = {
                userDetailsViewModel.setAddress(it)
                userDetailsViewModel.enableUpdate(address, phone)
            },
            modifier = Modifier
                .padding(bottom = 15.dp)
                .constrainAs(userAddField) {
                    top.linkTo(userAdd.bottom)
                    end.linkTo(endGuide)
                    start.linkTo(startGuide)
                    bottom.linkTo(closeBtn.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
            placeholder = "Debe de tener al menos 5 caracteres",
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Default,
            enabled = isEditing
        )
        Button(
            onClick = {
                if (!isEditing)
                    userDetailsViewModel.changeIsEditing(true)
                else
                    userDetailsViewModel.changeWantToUpdate(true)

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 5.dp)
                .height(75.dp)
                .constrainAs(updateBtn) {
                    bottom.linkTo(bottomGuide)
                    start.linkTo(startGuide)
                    end.linkTo(closeBtn.start)
                    width = Dimension.fillToConstraints
                },
            colors = ButtonColors(
                contentColor = black,
                containerColor = mostaza,
                disabledContainerColor = mostazaSoft,
                disabledContentColor = blackSoft
            ),
            enabled = isUpdateEnable
        ) {
            Text(
                text = if (!isEditing) "Modificar" else "Confirmar",
                fontFamily = aladinFont,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Button(
            onClick = {
                if (!isEditing)
                    userDetailsViewModel.changeCloseSession(true)
                else {
                    userDetailsViewModel.setPhone(user.phone)
                    userDetailsViewModel.setAddress(user.address)
                    userDetailsViewModel.enableUpdate(address, phone)
                    userDetailsViewModel.changeIsEditing(false)
                }
            },
            Modifier
                .fillMaxWidth()
                .height(75.dp)
                .padding(start = 5.dp)
                .constrainAs(closeBtn) {
                    bottom.linkTo(bottomGuide)
                    end.linkTo(endGuide)
                    start.linkTo(updateBtn.end)
                    width = Dimension.fillToConstraints
                },
            colors = ButtonColors(
                contentColor = black,
                containerColor = darkRed,
                disabledContainerColor = mostazaSoft,
                disabledContentColor = blackSoft
            )
        ) {
            Text(
                text = if (!isEditing) "Cerrar Sesión" else "Cancelar",
                fontFamily = aladinFont,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}