package com.example.habits.utils

inline fun <reified T : Enum<T>> enumValueOfOrNull(name: String?): T? {
    return enumValues<T>().find { it.name == name }
}
