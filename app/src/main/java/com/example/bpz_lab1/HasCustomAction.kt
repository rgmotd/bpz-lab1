package com.example.bpz_lab1

import androidx.annotation.DrawableRes

interface HasCustomAction {

    fun getCustomAction(): CustomAction
}

data class CustomAction(
    @DrawableRes val iconRes: Int,
    val onCustomAction: () -> Unit
)