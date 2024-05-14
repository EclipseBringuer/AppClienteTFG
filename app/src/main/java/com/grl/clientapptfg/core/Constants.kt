package com.grl.clientapptfg.core

class Constants {
    companion object {
        const val TOKEN = "15112004"
        const val CARD = "CARD"
        const val CASH = "CASH"
        const val PICK = "PICK"
        const val DELIVERY = "DELIVERY"
        const val BASIC_STATE = "PENDING"

        class Category {
            companion object {
                const val KEBAB = "Kebab"
                const val DURUM = "Dürüm"
                const val MENU = "Menús"
                const val RACION = "Raciones"
                const val HAMBURGUESA = "Hamburguesa"
                const val LAHMACUN = "Lahmacun"
                const val BEBIDA = "Bebidas"

                fun getListOfCategories(): List<String> {
                    return listOf(KEBAB, DURUM, HAMBURGUESA, BEBIDA, LAHMACUN, RACION, MENU)
                }
            }
        }
    }
}