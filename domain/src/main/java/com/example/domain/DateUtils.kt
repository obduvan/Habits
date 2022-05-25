package com.example.domain

import java.util.*

fun getSecondsTime(): Int {
    return (Date().time / 1000).toInt()
}