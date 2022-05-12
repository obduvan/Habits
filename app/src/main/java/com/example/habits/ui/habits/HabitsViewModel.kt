package com.example.habits.ui.habits

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habits.model.HabitModel
import com.example.habits.model.HabitType
import com.example.habits.net.ApiResponse
import com.example.habits.repository.IHabitRepository
import com.example.habits.ui.bottomSheets.HabitComparator
import com.example.habits.ui.editHabit.ShowingMessage
import com.example.habits.utils.addLiveData
import com.example.habits.utils.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HabitsViewModel(private val repository: IHabitRepository) : ViewModel() {

    private val filter = MutableLiveData("")
    private val habitComparator = MutableLiveData<Comparator<HabitModel>>()

    private var showingMessage: ShowingMessage? = null

    fun setShowingMessage(obj: ShowingMessage) {
        showingMessage = obj
    }

    private val habits: LiveData<List<HabitModel>> = repository.habits
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
            val apiResponse = repository.loadHabits()
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
