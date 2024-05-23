package com.grl.clientapptfg.ui.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.grl.clientapptfg.core.Constants
import com.grl.clientapptfg.core.UserSession
import com.grl.clientapptfg.data.models.ProductModel
import com.grl.clientapptfg.ui.screens.order.OrderViewModel
import com.grl.clientapptfg.ui.screens.tabs_menu.TabsMenuViewModel
import com.grl.clientapptfg.ui.theme.black
import com.grl.clientapptfg.ui.theme.blackSoft
import com.grl.clientapptfg.ui.theme.darkRed
import com.grl.clientapptfg.ui.theme.granate
import com.grl.clientapptfg.ui.theme.mostaza
import com.grl.clientapptfg.ui.theme.mostazaSoft
import com.grl.clientapptfg.ui.theme.white
import com.grl.clientapptfg.utils.Util

@Composable
fun ConfirmationDialog(onClick: () -> Unit, title: String, text: String) {
    val aladinFont = Util.loadFontFamilyFromAssets()
    Dialog(onDismissRequest = { }) {
        ConstraintLayout(
            Modifier
                .fillMaxWidth()
                .height(330.dp)
                .background(granate, RoundedCornerShape(20.dp))
        ) {
            val (titleText, description, bConfirm, divider) = createRefs()
            Text(
                text = title,
                color = mostaza,
                fontWeight = FontWeight.Bold,
                fontFamily = aladinFont,
                fontSize = 50.sp,
                style = TextStyle(lineHeight = 45.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(top = 10.dp)
                    .constrainAs(titleText) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    }
            )
            HorizontalDivider(
                Modifier
                    .constrainAs(divider) {
                        top.linkTo(titleText.bottom)
                        bottom.linkTo(description.top)
                    }
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                color = mostaza,
                thickness = 3.dp
            )
            Text(
                text = text,
                color = white,
                fontFamily = aladinFont,
                fontSize = 30.sp,
                style = TextStyle(lineHeight = 35.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier.constrainAs(description) {
                    top.linkTo(titleText.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    bottom.linkTo(bConfirm.top)
                }
            )
            Button(
                modifier = Modifier
                    .height(70.dp)
                    .fillMaxWidth()
                    .constrainAs(bConfirm) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    },
                onClick = onClick,
                shape = RoundedCornerShape(
                    bottomStart = 20.dp,
                    bottomEnd = 20.dp,
                    topStart = 0.dp,
                    topEnd = 0.dp
                ),
                colors = ButtonColors(
                    contentColor = black,
                    containerColor = mostaza,
                    disabledContainerColor = mostazaSoft,
                    disabledContentColor = blackSoft
                )
            ) {
                Text(
                    text = "Aceptar",
                    color = black,
                    fontFamily = aladinFont,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }
}

@Composable
fun ConfirmationDialogWithNegative(
    onPositive: () -> Unit,
    title: String,
    text: String,
    onNegative: () -> Unit
) {
    val aladinFont = Util.loadFontFamilyFromAssets()
    Dialog(onDismissRequest = { }) {
        ConstraintLayout(
            Modifier
                .fillMaxWidth()
                .height(350.dp)
                .background(granate, RoundedCornerShape(20.dp))
        ) {
            val (titleText, description, bConfirm, bDenegate, divider) = createRefs()
            Text(
                text = title,
                color = mostaza,
                fontFamily = aladinFont,
                fontWeight = FontWeight.Bold,
                fontSize = 50.sp,
                style = TextStyle(lineHeight = 45.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 10.dp)
                    .constrainAs(titleText) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    }
            )
            HorizontalDivider(
                Modifier
                    .constrainAs(divider) {
                        top.linkTo(titleText.bottom)
                        bottom.linkTo(description.top)
                    }
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 40.dp),
                color = mostaza,
                thickness = 3.dp
            )
            Text(
                text = text,
                color = white,
                fontFamily = aladinFont,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                style = TextStyle(lineHeight = 40.sp),
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .constrainAs(description) {
                        top.linkTo(titleText.bottom)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                        bottom.linkTo(bConfirm.top)
                    }
            )
            Button(
                modifier = Modifier
                    .height(70.dp)
                    .fillMaxWidth()
                    .constrainAs(bConfirm) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(bDenegate.start)
                        width = Dimension.fillToConstraints
                    },
                onClick = onPositive,
                shape = RoundedCornerShape(
                    bottomStart = 20.dp,
                    bottomEnd = 0.dp,
                    topEnd = 0.dp,
                    topStart = 0.dp
                ),
                colors = ButtonColors(
                    contentColor = black,
                    containerColor = mostaza,
                    disabledContainerColor = mostazaSoft,
                    disabledContentColor = blackSoft
                )
            ) {
                Text(
                    text = "Aceptar",
                    color = black,
                    fontFamily = aladinFont,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Button(
                modifier = Modifier
                    .height(70.dp)
                    .fillMaxWidth()
                    .constrainAs(bDenegate) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                        start.linkTo(bConfirm.end)
                        width = Dimension.fillToConstraints
                    },
                shape = RoundedCornerShape(
                    bottomStart = 0.dp,
                    bottomEnd = 20.dp,
                    topStart = 0.dp,
                    topEnd = 0.dp
                ),
                onClick = onNegative,
                colors = ButtonColors(
                    contentColor = black,
                    containerColor = darkRed,
                    disabledContainerColor = mostazaSoft,
                    disabledContentColor = blackSoft
                )
            ) {
                Text(
                    text = "Cancelar",
                    color = black,
                    fontFamily = aladinFont,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun OrderProductDialog(
    onDismiss: () -> Unit,
    product: ProductModel,
    context: Context,
    notLogged: () -> Unit,
    tabsMenuViewModel: TabsMenuViewModel
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(dismissOnClickOutside = true, dismissOnBackPress = true)
    ) {
        ConstraintLayout(
            Modifier
                .fillMaxWidth()
                .height(480.dp)
                .background(granate, shape = RoundedCornerShape(20.dp))
        ) {
            val (image, title, body, button) = createRefs()
            val topGuide = createGuidelineFromTop(0.02f)
            Image(
                painter = painterResource(id = product.photo),
                contentDescription = "Product image",
                modifier = Modifier
                    .size(140.dp)
                    .constrainAs(image) {
                        top.linkTo(topGuide)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    }
            )

            Text(
                text = product.name,
                fontFamily = Util.loadFontFamilyFromAssets(),
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                color = mostaza,
                style = TextStyle(lineHeight = 50.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(image.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
            )

            Text(
                text = product.description,
                fontFamily = Util.loadFontFamilyFromAssets(),
                fontSize = 35.sp,
                color = white,
                style = TextStyle(lineHeight = 40.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .constrainAs(body) {
                        top.linkTo(title.bottom)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                        bottom.linkTo(button.top)
                    }
            )

            Button(
                onClick = {
                    if (UserSession.getUser() != null) {
                        tabsMenuViewModel.addProduct(product)
                        tabsMenuViewModel.updateTotalPrice()
                        Toast.makeText(
                            context,
                            "Producto ${product.name} añadido",
                            Toast.LENGTH_SHORT
                        ).show()
                        onDismiss()
                    } else {
                        notLogged()
                        onDismiss()
                    }
                },
                Modifier
                    .height(70.dp)
                    .fillMaxWidth()
                    .constrainAs(button) {
                        bottom.linkTo(parent.bottom)
                    },
                shape = RoundedCornerShape(
                    bottomEnd = 20.dp,
                    bottomStart = 20.dp,
                    topStart = 0.dp,
                    topEnd = 0.dp
                ),
                colors = ButtonColors(
                    contentColor = black,
                    containerColor = mostaza,
                    disabledContainerColor = mostazaSoft,
                    disabledContentColor = blackSoft
                )
            ) {
                Text(
                    text = "Añadir",
                    fontFamily = Util.loadFontFamilyFromAssets(),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun OrderOptionsDialog(
    paymentValue: String = "",
    deliveryValue: String = "",
    orderViewModel: OrderViewModel,
    total: Double,
    onConfirm: () -> Unit,
) {
    val aladinFont = Util.loadFontFamilyFromAssets()
    Dialog(onDismissRequest = { }) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(580.dp)
                .background(granate, RoundedCornerShape(20.dp))
        ) {
            val (spacerSecond, spacerFirst, title, divider, deliveryText, paymentText, button, radioCard, radioCash, radioDeliver, radioPick, textCard, textCash, textDeliver, textPick) = createRefs()
            Text(
                text = "Opciones del Pedido",
                fontFamily = aladinFont,
                fontWeight = FontWeight.Bold,
                color = mostaza,
                fontSize = 50.sp,
                textAlign = TextAlign.Center,
                style = TextStyle(lineHeight = 50.sp),
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
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
            Text(
                text = "Entrega:",
                fontFamily = aladinFont,
                fontWeight = FontWeight.Bold,
                color = mostaza,
                fontSize = 35.sp,
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .constrainAs(deliveryText) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(divider.bottom)
                    }
            )
            RadioButton(
                selected = deliveryValue == Constants.DELIVERY,
                onClick = { orderViewModel.changeDeliveryValue(Constants.DELIVERY) },
                colors = RadioButtonColors(
                    selectedColor = mostaza,
                    unselectedColor = white,
                    disabledSelectedColor = blackSoft,
                    disabledUnselectedColor = blackSoft
                ),
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .size(20.dp)
                    .constrainAs(radioDeliver) {
                        top.linkTo(textDeliver.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(textDeliver.bottom)
                    }
            )
            Text(text = "A Domicilio (${Util.formatDouble(total)}€ + 1.50€)",
                fontFamily = aladinFont,
                fontSize = 25.sp,
                color = white,
                modifier = Modifier.constrainAs(textDeliver) {
                    start.linkTo(radioDeliver.end)
                    top.linkTo(deliveryText.bottom)
                })
            Spacer(modifier = Modifier
                .height(20.dp)
                .constrainAs(spacerFirst) {
                    top.linkTo(textDeliver.bottom)
                    bottom.linkTo(textPick.top)
                })
            RadioButton(
                selected = deliveryValue == Constants.PICK,
                onClick = { orderViewModel.changeDeliveryValue(Constants.PICK) },
                colors = RadioButtonColors(
                    selectedColor = mostaza,
                    unselectedColor = white,
                    disabledSelectedColor = blackSoft,
                    disabledUnselectedColor = blackSoft
                ),
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .size(20.dp)
                    .constrainAs(radioPick) {
                        start.linkTo(parent.start)
                        top.linkTo(textPick.top)
                        bottom.linkTo(textPick.bottom)
                    }
            )
            Text(text = "Establecimiento",
                fontFamily = aladinFont,
                color = white,
                fontSize = 25.sp,
                modifier = Modifier.constrainAs(textPick) {
                    top.linkTo(spacerFirst.bottom)
                    start.linkTo(radioPick.end)
                })
            Text(text = "Método de Pago:", fontFamily = aladinFont,
                fontWeight = FontWeight.Bold,
                color = mostaza,
                fontSize = 35.sp,
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .constrainAs(paymentText) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(textPick.bottom)
                    })
            Text(text = "Tarjeta",
                fontFamily = aladinFont,
                color = white,
                fontSize = 25.sp,
                modifier = Modifier.constrainAs(textCard) {
                    top.linkTo(paymentText.bottom)
                    start.linkTo(radioPick.end)
                })
            RadioButton(
                selected = paymentValue == Constants.CARD,
                onClick = { orderViewModel.changePaymentValue(Constants.CARD) },
                colors = RadioButtonColors(
                    selectedColor = mostaza,
                    unselectedColor = white,
                    disabledSelectedColor = blackSoft,
                    disabledUnselectedColor = blackSoft
                ),
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .size(20.dp)
                    .constrainAs(radioCard) {
                        top.linkTo(textCard.top)
                        bottom.linkTo(textCard.bottom)
                        start.linkTo(parent.start)
                    }
            )
            Spacer(modifier = Modifier
                .height(20.dp)
                .constrainAs(spacerSecond) {
                    top.linkTo(textCard.bottom)
                    bottom.linkTo(textCash.top)
                })
            Text(text = "Efectivo", fontFamily = aladinFont,
                color = white,
                fontSize = 25.sp,
                modifier = Modifier.constrainAs(textCash) {
                    top.linkTo(spacerSecond.bottom)
                    start.linkTo(radioCash.end)
                })
            RadioButton(
                selected = paymentValue == Constants.CASH,
                onClick = { orderViewModel.changePaymentValue(Constants.CASH) },
                colors = RadioButtonColors(
                    selectedColor = mostaza,
                    unselectedColor = white,
                    disabledSelectedColor = blackSoft,
                    disabledUnselectedColor = blackSoft
                ),
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .size(20.dp)
                    .constrainAs(radioCash) {
                        start.linkTo(parent.start)
                        top.linkTo(textCash.top)
                        bottom.linkTo(textCash.bottom)
                    }
            )
            Button(
                onClick = { onConfirm() },
                Modifier
                    .height(70.dp)
                    .fillMaxWidth()
                    .constrainAs(button) {
                        bottom.linkTo(parent.bottom)
                    },
                shape = RoundedCornerShape(
                    bottomEnd = 20.dp,
                    bottomStart = 20.dp,
                    topStart = 0.dp,
                    topEnd = 0.dp
                ),
                colors = ButtonColors(
                    contentColor = black,
                    containerColor = mostaza,
                    disabledContainerColor = mostazaSoft,
                    disabledContentColor = blackSoft
                )
            ) {
                Text(
                    text = "Confirmar",
                    fontFamily = Util.loadFontFamilyFromAssets(),
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}