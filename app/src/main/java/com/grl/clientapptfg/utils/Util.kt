package com.grl.clientapptfg.utils

import android.icu.text.DecimalFormat
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.grl.clientapptfg.R
import java.security.MessageDigest

class Util {
    companion object {
        @Composable
        fun loadFontFamilyFromAssets(): FontFamily {
            return FontFamily(Font(R.font.aladin_regular))
        }

        fun hashPassword(password: String): String {
            val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
            return bytes.joinToString("") { "%02x".format(it) }
        }

        fun formatDouble(value: Double):String {
            val decimalFormat = DecimalFormat("0.#")
            return decimalFormat.format(value)
        }
    }
}