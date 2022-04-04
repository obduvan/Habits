package com.example.habits.ui.habits

import android.util.Log
import androidx.lifecycle.*
import com.example.habits.HabitRepositoryTest
import com.example.habits.model.HabitModel
import com.example.habits.model.HabitType
import kotlinx.coroutines.launch

class HabitsViewModel : ViewModel() {
    private var repository = HabitRepositoryTest


    private val mutableHabitList = MutableLiveData<List<HabitModel>>()


    fun getHabits(habitType: HabitType?): LiveData<List<HabitModel>> {
        var habits = repository.getHabits()
        habits  = habits.filter { it.type == habitType }
        mutableHabitList.value = habits

        return mutableHabitList
    }
}

