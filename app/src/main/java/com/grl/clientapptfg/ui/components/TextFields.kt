package com.grl.clientapptfg.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grl.clientapptfg.ui.theme.PurpleGrey80
import com.grl.clientapptfg.ui.theme.black
import com.grl.clientapptfg.ui.theme.mostaza
import com.grl.clientapptfg.ui.theme.white
import com.grl.clientapptfg.utils.Util

@Composable
fun TextFieldPersonalized(
    value: String,
    function: (String) -> Unit,
    modifier: Modifier,
    placeholder: String,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    enabled: Boolean = true,
) {
    OutlinedTextField(
        value = value,
        onValueChange = { function(it) },
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 28.sp,
            color = if (enabled) black else white,
            fontFamily = Util.loadFontFamilyFromAssets()
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        placeholder = {
            Text(
                text = placeholder,
                fontSize = 25.sp,
                fontFamily = Util.loadFontFamilyFromAssets(),
                color = PurpleGrey80
            )
        }, colors = TextFieldDefaults.colors(
            focusedContainerColor = white,
            unfocusedContainerColor = white,
            disabledContainerColor = black,
            focusedIndicatorColor = mostaza,
            disabledIndicatorColor = mostaza
        ),
        enabled = enabled
    )
}

@Composable
fun TextAreaPersonalized(
    value: String,
    function: (String) -> Unit,
    modifier: Modifier,
    placeholder: String,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    enabled: Boolean = true,
) {
    OutlinedTextField(
        value = value,
        onValueChange = { function(it) },
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        singleLine = false,
        textStyle = TextStyle(
            fontSize = 35.sp,
            color = if (enabled) black else white,
            fontFamily = Util.loadFontFamilyFromAssets()
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        placeholder = {
            Text(
                text = placeholder,
                fontSize = 25.sp,
                fontFamily = Util.loadFontFamilyFromAssets(),
                color = PurpleGrey80
            )
        }, colors = TextFieldDefaults.colors(
            focusedContainerColor = white,
            unfocusedContainerColor = white,
            disabledContainerColor = black,
            focusedIndicatorColor = mostaza,
            disabledIndicatorColor = mostaza
        ),
        enabled = enabled,
        maxLines = 10,
    )
}

@Composable
fun TextFieldForPasswordPersonalized(
    value: String,
    changeText: (String) -> Unit,
    modifier: Modifier,
    imeAction: ImeAction,
    isVisible: Boolean,
    changeVisibility: (Boolean) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = { changeText(it) },
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        singleLine = true,
        textStyle = TextStyle(fontSize = 20.sp, color = black),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        placeholder = {
            Text(
                text = "Constraseña",
                fontSize = 25.sp,
                fontFamily = Util.loadFontFamilyFromAssets(),
                color = PurpleGrey80
            )
        }, colors = TextFieldDefaults.colors(
            focusedContainerColor = white,
            unfocusedContainerColor = white,
            disabledContainerColor = white,
            focusedIndicatorColor = mostaza
        ), trailingIcon = {
            val imagen = if (isVisible) {
                Icons.Filled.VisibilityOff
            } else {
                Icons.Filled.Visibility
            }
            IconButton(onClick = { changeVisibility(isVisible) }) {
                Icon(
                    imageVector = imagen,
                    contentDescription = "Icono de visibilidad",
                    Modifier.size(35.dp),
                    tint = mostaza
                )
            }
        },
        visualTransformation = if (isVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        }
    )
}