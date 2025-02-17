package com.example.myapplication.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class SelectionOption(val option: String, var initialSelectedValue: Boolean, val value: Int) {
    var selected by mutableStateOf(initialSelectedValue)
}
