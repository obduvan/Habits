package com.example.habits.ui.habits

import androidx.lifecycle.*
import com.example.habits.model.HabitModel
import com.example.habits.model.HabitType
import com.example.habits.repository.IHabitRepository
import com.example.habits.utils.addLiveData
import com.example.habits.utils.map

class HabitsViewModel(private val repository: IHabitRepository) : ViewModel() {
    private var filter = MutableLiveData("")

    private val emptyComparator = Comparator { _: HabitModel, _: HabitModel -> 0 }
    private var comparator = MutableLiveData<Comparator<HabitModel>>(emptyComparator)
    private var isSorted = false

    private val habitList: LiveData<List<HabitModel>> = repository.getHabits()
        .addLiveData(filter)
        .addLiveData(comparator)
        .map { (pair, comparator) ->
            val habits = pair?.first ?: listOf()
            val filter = pair?.second ?: ""
            val habitComparator = comparator ?: emptyComparator
            habits.filter { it.name.contains(filter) }.sortedWith(habitComparator)
        }

    fun setFilter(mFilter: String) {
        filter.postValue(mFilter)
    }

    fun getHabits(type: HabitType?): LiveData<List<HabitModel>> {
        return if (type == null) {
            habitList
        } else {
            habitList.map { it.filter { habitModel -> habitModel.type == type } }
        }
    }

    fun setComparator(mComparator: Comparator<HabitModel>) {
        isSorted = !isSorted

        comparator.postValue(
            if (isSorted) {
                mComparator
            } else {
                emptyComparator
            }
        )
    }
}






