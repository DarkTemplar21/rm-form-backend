package com.richmeat.data.util

object TempFormHelper {
    fun intToString(on: Int, si : Boolean): String {
        return if (on == 1 && !si) {
            " "
        } else if (on == 1 && si) {
            "X"
        } else if (on == 0 && si) {
            " "
        } else if (on == 0 && !si) {
            "X"
        } else {
            " "
        }
    }
}