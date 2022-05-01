package com.example.habits.ui.habits

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habits.model.HabitModel
import com.example.habits.model.HabitType
import com.example.habits.repository.IHabitRepository
import com.example.habits.ui.bottomSheets.HabitComparator
import com.example.habits.utils.addLiveData
import com.example.habits.utils.map

class HabitsViewModel(private val repository: IHabitRepository) : ViewModel() {

    private var filter = MutableLiveData("")
    private var habitComparator = MutableLiveData<Comparator<HabitModel>>()

    private val habitList: LiveData<List<HabitModel>> = repository.habits
        .addLiveData(filter)
        .addLiveData(habitComparator)
        .map { (pair, comparator) ->
            val habits = pair?.first ?: listOf()
            val filter = pair?.second ?: ""
            val habitComparator = comparator ?: HabitComparator.emptyComparator

            habits.filter { it.name.contains(filter) }.sortedWith(habitComparator)
        }

    fun setFilter(mFilter: String) {
        filter.postValue(mFilter)
    }

    fun getHabits(type: HabitType): LiveData<List<HabitModel>> {
        return habitList.map { it.filter { habitModel -> habitModel.type == type } }
    }

    fun setComparator(comparator: Comparator<HabitModel>) {
        this.habitComparator.postValue(comparator)
    }
}
