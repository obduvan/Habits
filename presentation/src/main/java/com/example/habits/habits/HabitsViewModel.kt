package com.example.habits.habits

import android.util.Log
import androidx.lifecycle.*
import com.example.domain.api.ApiResponse
import com.example.domain.addLiveData
import com.example.domain.api.DoneMessage
import com.example.domain.entities.HabitModel
import com.example.domain.entities.HabitType
import com.example.domain.map
import com.example.domain.useCases.DoneHabitUseCase
import com.example.domain.useCases.HabitsUseCase
import com.example.habits.bottomSheets.HabitComparator
import com.example.habits.editHabit.ShowingMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HabitsViewModel(
    private val habitsUseCase: HabitsUseCase,
    private val doneHabitUseCase: DoneHabitUseCase
) : ViewModel() {

    private val filter = MutableLiveData("")
    private val habitComparator = MutableLiveData<Comparator<HabitModel>>()

    private var showingMessage: ShowingMessage? = null

    fun setShowingMessage(obj: ShowingMessage) {
        showingMessage = obj
    }

    private val habits: LiveData<List<HabitModel>> = habitsUseCase.getHabits()
        .asLiveData(viewModelScope.coroutineContext)
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
            withContext(Dispatchers.Main) {
                if (apiResponse is ApiResponse.Error) {
                    showingMessage?.showMessage(SERVER_ERROR_TEXT)

                }
            }
        }
    }

    fun doneHabit(id: String, time: Int) {
        viewModelScope.launch {
            val apiResponse: ApiResponse<DoneMessage> = doneHabitUseCase.doneHabit(id, time)
            Log.e(HabitsViewModel::class.java.name, apiResponse.toString())

            withContext(Dispatchers.Main) {
                if (apiResponse is ApiResponse.Error) {
                    showingMessage?.showMessage(SERVER_ERROR_TEXT)
                }
                if (apiResponse is ApiResponse.Success) {
                    apiResponse.data.text?.let {
                        showingMessage?.showMessage(it)
                    }
                }
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

    companion object{
        private const val SERVER_ERROR_TEXT = "Server error"
    }
}
