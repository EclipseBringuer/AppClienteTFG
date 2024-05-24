package com.grl.clientapptfg.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.grl.clientapptfg.ui.theme.mostaza
import com.grl.clientapptfg.ui.theme.white
import com.grl.clientapptfg.utils.Util

@Composable
fun StyledText(fieldText: String, valueText: String, modifier: Modifier) {
    val text = buildAnnotatedString {
        withStyle(style = SpanStyle(color = mostaza)) {
            append(fieldText)
        }
        withStyle(style = SpanStyle(color = white)) {
            append(valueText)
        }
    }
    Text(
        text = text,
        modifier = modifier.fillMaxWidth(),
        fontSize = 35.sp,
        maxLines = 2,
        style = TextStyle(lineHeight = 35.sp),
        overflow = TextOverflow.Ellipsis,
        fontFamily = Util.loadFontFamilyFromAssets()
    )
}