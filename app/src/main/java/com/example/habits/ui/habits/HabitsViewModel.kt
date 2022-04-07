package com.example.habits.ui.habits

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habits.HabitRepositoryTest
import com.example.habits.model.HabitModel
import com.example.habits.model.HabitType

class HabitsViewModel : ViewModel() {
    private var repository = HabitRepositoryTest
    private var habits: List<HabitModel> = ArrayList()

    private val mutableHabitList = MutableLiveData<List<HabitModel>>()
    val habitList: LiveData<List<HabitModel>> = mutableHabitList

    private var filterHabit = ""
    private var isSorted = false
    private val emptyComparator = Comparator { _: HabitModel, _: HabitModel -> 0 }
    private var comparator = emptyComparator


    fun setFilter(filter: String) {
        filterHabit = filter
        updateData()
    }

    private fun updateData() {
        mutableHabitList.value =
            habits.filter { it.name.contains(filterHabit) }.sortedWith(comparator)
    }

    fun loadHabits(type: HabitType) {
        habits = repository.getHabits(type)
        updateData()
    }

    fun setSort(mComparator: Comparator<HabitModel>) {
        isSorted = !isSorted

        comparator = if (isSorted) {
            mComparator
        } else {
            emptyComparator
        }
        updateData()
    }
}
