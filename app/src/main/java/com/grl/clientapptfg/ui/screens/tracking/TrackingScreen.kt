package com.grl.clientapptfg.ui.screens.tracking

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.grl.clientapptfg.R
import com.grl.clientapptfg.core.Constants
import com.grl.clientapptfg.core.UserSession
import com.grl.clientapptfg.data.models.OrderModel
import com.grl.clientapptfg.ui.components.ConfirmationDialog
import com.grl.clientapptfg.ui.components.LogoApp
import com.grl.clientapptfg.ui.components.ProgressBarDialog
import com.grl.clientapptfg.ui.theme.black
import com.grl.clientapptfg.ui.theme.granate
import com.grl.clientapptfg.ui.theme.mostaza
import com.grl.clientapptfg.ui.theme.white
import com.grl.clientapptfg.utils.Util
import kotlinx.coroutines.delay

@Composable
fun TrackingScreen(trackingViewModel: TrackingViewModel) {
    val aladinFont = Util.loadFontFamilyFromAssets()
    val isLoading by trackingViewModel.isLoading.observeAsState(initial = false)
    val isOrderEvent by trackingViewModel.orderEvent.observeAsState(initial = false)
    val orderOfEvent by trackingViewModel.orderOfEvent.observeAsState()
    val orders by trackingViewModel.orders.observeAsState(initial = listOf())
    val orderSelected by trackingViewModel.orderSelected.observeAsState(
        initial = OrderModel(
            price = 0.0,
            paymentMethod = "",
            delivery = "",
            items = listOf(),
            state = "",
            user = null
        )
    )

    if (isLoading) {
        ProgressBarDialog()
    }

    if (UserSession.getUser() != null) {
        LaunchedEffect(Unit) {
            while (true) {
                trackingViewModel.getOrdersByUser(UserSession.getUser()!!.id)
                delay(8000L)
            }
        }
    }

    if (isOrderEvent) {
        ConfirmationDialog(
            onClick = { trackingViewModel.closeOrderEvent() },
            title =
            if (orderOfEvent!!.state == Constants.COMPLETED)
                "El pedido ${orderOfEvent!!.id}Nº esta completado"
            else
                "El pedido ${orderOfEvent!!.id}Nº se ha cancelado",
            text = "Pulsa aceptar para continuar"
        )
    }

    ConstraintLayout(
        Modifier
            .fillMaxSize()
            .background(granate)
    ) {
        val (title, logo, body, pending, doing, delivery, bar1, bar2, textPending, textDoing, textDelivery) = createRefs()
        val topGuide = createGuidelineFromTop(0.03f)
        val startGuide = createGuidelineFromStart(0.05f)
        LogoApp(modifier = Modifier.constrainAs(logo) {
            top.linkTo(topGuide)
            start.linkTo(startGuide)
        })
        Text(text = "Seguimiento",
            fontFamily = aladinFont,
            color = white,
            fontWeight = FontWeight.Bold,
            fontSize = 50.sp,
            modifier = Modifier
                .padding(bottom = 50.dp)
                .constrainAs(title) {
                    top.linkTo(topGuide)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
        Box(modifier = Modifier
            .padding(start = 20.dp)
            .size(35.dp)
            .clip(CircleShape)
            .background(
                color =
                if (orderSelected.state != Constants.CANCELED && orderSelected.state != "") {
                    mostaza
                } else {
                    white
                }
            )
            .constrainAs(pending) {
                top.linkTo(title.bottom)
                start.linkTo(parent.start)
            })
        HorizontalDivider(
            thickness = 5.dp,
            color = if (orderSelected.state == Constants.PREPARATION || orderSelected.state == Constants.DELIVERY) {
                mostaza
            } else {
                white
            },
            modifier = Modifier.constrainAs(bar1) {
                top.linkTo(pending.top)
                bottom.linkTo(pending.bottom)
                start.linkTo(pending.end)
                end.linkTo(doing.start)
                width = Dimension.fillToConstraints
            })
        Box(modifier = Modifier
            .size(35.dp)
            .clip(CircleShape)
            .background(
                if (orderSelected.state == Constants.PREPARATION || orderSelected.state == Constants.DELIVERY) {
                    mostaza
                } else {
                    white
                }
            )
            .constrainAs(doing) {
                top.linkTo(title.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
        HorizontalDivider(
            thickness = 5.dp,
            color = if (orderSelected.state == Constants.DELIVERY) {
                mostaza
            } else {
                white
            },
            modifier = Modifier.constrainAs(bar2) {
                start.linkTo(doing.end)
                end.linkTo(delivery.start)
                top.linkTo(delivery.top)
                bottom.linkTo(delivery.bottom)
                width = Dimension.fillToConstraints
            })
        Box(modifier = Modifier
            .padding(end = 20.dp)
            .size(35.dp)
            .clip(CircleShape)
            .background(
                if (orderSelected.state == Constants.DELIVERY) {
                    mostaza
                } else {
                    white
                }
            )
            .constrainAs(delivery) {
                end.linkTo(parent.end)
                top.linkTo(title.bottom)
            })
        Text(text = "Pendiente",
            fontFamily = aladinFont,
            color = if (orderSelected.state == Constants.PENDING) {
                mostaza
            } else {
                white
            },
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            fontWeight = if (orderSelected.state == Constants.PENDING) {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            },
            modifier = Modifier
                .padding(start = 30.dp, top = 10.dp)
                .constrainAs(textPending) {
                    top.linkTo(pending.bottom)
                    start.linkTo(pending.start)
                    end.linkTo(pending.end)
                })
        Text(text = "Preparación",
            fontFamily = aladinFont,
            color = if (orderSelected.state == Constants.PREPARATION) {
                mostaza
            } else {
                white
            },
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            fontWeight = if (orderSelected.state == Constants.PREPARATION) {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            },
            modifier = Modifier
                .padding(top = 10.dp, bottom = 50.dp)
                .constrainAs(textDoing) {
                    top.linkTo(doing.bottom)
                    start.linkTo(doing.start)
                    end.linkTo(doing.end)
                })
        Text(text = "Reparto",
            fontFamily = aladinFont,
            color = if (orderSelected.state == Constants.DELIVERY) {
                mostaza
            } else {
                white
            },
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            fontWeight = if (orderSelected.state == Constants.DELIVERY) {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            },
            modifier = Modifier
                .padding(end = 25.dp, top = 10.dp)
                .constrainAs(textDelivery) {
                    top.linkTo(delivery.bottom)
                    start.linkTo(delivery.start)
                    end.linkTo(delivery.end)
                })
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .constrainAs(body) {
                    top.linkTo(textDoing.bottom)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                }) {
            if (orders.isEmpty()) {
                item {
                    Text(
                        text = "No hay pedidos en curso",
                        fontSize = 35.sp,
                        fontFamily = aladinFont,
                        color = mostaza,
                    )
                }
            } else {
                item {
                    Text(
                        text = "Listado de Pedidos",
                        fontSize = 35.sp,
                        fontFamily = aladinFont,
                        color = mostaza
                    )
                }
                items(orders) { order ->
                    Card(
                        border = BorderStroke(3.dp, mostaza),
                        onClick = {
                            trackingViewModel.changeOrderSelected(order)
                        },
                        elevation = CardDefaults.cardElevation(defaultElevation = 15.dp),
                        colors = CardDefaults.cardColors(containerColor = black),
                        shape = RoundedCornerShape(
                            bottomStart = 8.dp,
                            bottomEnd = 40.dp,
                            topStart = 40.dp,
                            topEnd = 8.dp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .padding(horizontal = 12.dp, vertical = 10.dp)
                    ) {
                        ConstraintLayout(Modifier.fillMaxSize()) {
                            val (number, price, ico) = createRefs()
                            Text(
                                text = "${order.id} Nº",
                                fontFamily = aladinFont,
                                color = mostaza,
                                fontSize = 40.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(horizontal = 20.dp, vertical = 10.dp)
                                    .constrainAs(number) {
                                        top.linkTo(parent.top)
                                        start.linkTo(parent.start)
                                        bottom.linkTo(parent.bottom)
                                    }
                            )
                            if (order == orderSelected) {
                                Icon(
                                    painter = painterResource(id = R.drawable.morito_blanco),
                                    contentDescription = "morito icono",
                                    tint = white,
                                    modifier = Modifier
                                        .size(80.dp)
                                        .constrainAs(ico) {
                                            top.linkTo(parent.top)
                                            bottom.linkTo(parent.bottom)
                                            start.linkTo(parent.start)
                                            end.linkTo(parent.end)
                                        })
                            }
                            Text(
                                text = "${Util.formatDouble(order.price)}€",
                                fontFamily = aladinFont,
                                color = mostaza,
                                fontSize = 40.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(horizontal = 20.dp, vertical = 10.dp)
                                    .constrainAs(price) {
                                        top.linkTo(parent.top)
                                        end.linkTo(parent.end)
                                        bottom.linkTo(parent.bottom)
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}