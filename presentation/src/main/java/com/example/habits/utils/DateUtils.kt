package com.example.habits.utils

import java.util.*

fun getIntTime(): Int {
    return (Date().time / 1000).toInt()
}