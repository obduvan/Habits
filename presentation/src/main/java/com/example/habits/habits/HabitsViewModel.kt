package com.example.habits.habits

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.ApiResponse
import com.example.domain.addLiveData
import com.example.domain.entities.HabitModel
import com.example.domain.entities.HabitType
import com.example.domain.map
import com.example.domain.useCases.HabitsUseCase
import com.example.habits.bottomSheets.HabitComparator
import com.example.habits.editHabit.ShowingMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HabitsViewModel(private val habitsUseCase: HabitsUseCase) : ViewModel() {

    private val filter = MutableLiveData("")
    private val habitComparator = MutableLiveData<Comparator<HabitModel>>()

    private var showingMessage: ShowingMessage? = null

    fun setShowingMessage(obj: ShowingMessage) {
        showingMessage = obj
    }

    private val habits: LiveData<List<HabitModel>> = habitsUseCase.getHabits()
        .addLiveData(filter)
        .addLiveData(habitComparator)
        .map { (pairLiveData, comparator) ->
            val habits = pairLiveData?.first ?: listOf()
            val filter = pairLiveData?.second ?: ""
            val selectedComparator = comparator ?: HabitComparator.emptyComparator

            habits.filter { it.name.contains(filter) }.sortedWith(selectedComparator)
        }


    fun loadHabits() {
        viewModelScope.launch(Dispatchers.IO) {
            val apiResponse = habitsUseCase.loadHabits()
            if (apiResponse is ApiResponse.Error) {
                showingMessage?.showMessage("Can't connect to server.")
            }
        }
    }

    fun setFilter(mFilter: String) {
        filter.postValue(mFilter)
    }

    fun getHabits(type: HabitType): LiveData<List<HabitModel>> {
        return habits.map { it.filter { habitModel -> habitModel.type == type } }
    }

    fun setComparator(comparator: Comparator<HabitModel>) {
        habitComparator.postValue(comparator)
    }

    fun onDestroyView() {
        showingMessage = null
    }
}
