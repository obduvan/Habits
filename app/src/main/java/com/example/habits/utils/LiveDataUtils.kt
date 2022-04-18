package com.example.habits.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations

fun <T1, T2> LiveData<T1>.addLiveData(second: LiveData<T2>): LiveData<Pair<T1?, T2?>> {
    val mediator = MediatorLiveData<Pair<T1?, T2?>>()
    mediator.addSource(this) { mediator.value = it to second.value }
    mediator.addSource(second) { mediator.value = this.value to it }
    return mediator
}

fun <T1, T2> LiveData<T1>.map(f: (T1) -> T2) = Transformations.map(this, f)