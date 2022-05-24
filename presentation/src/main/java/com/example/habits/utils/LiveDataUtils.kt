package com.example.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations

fun <T1, T2> LiveData<T1>.addLiveData(liveData2: LiveData<T2>): LiveData<Pair<T1?, T2?>> {
    val mediator = MediatorLiveData<Pair<T1?, T2?>>()

    mediator.addSource(this) { liveData1 -> mediator.value = liveData1 to liveData2.value }
    mediator.addSource(liveData2) { mediator.value = this.value to it }
    return mediator
}

fun <T1, T2> LiveData<T1>.map(func: (T1) -> T2) = Transformations.map(this, func)
