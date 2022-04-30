package com.example.habits.ui.editHabit

import androidx.lifecycle.*
import com.example.habits.R
import com.example.habits.model.HabitModel
import com.example.habits.repository.IHabitRepository
import com.example.habits.utils.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditHabitViewModel(private val repository: IHabitRepository) : ViewModel() {

    private val _habit = MutableLiveData<HabitModel>()
    val habit: LiveData<HabitModel> = _habit

    private var showingMessage: ShowingMessage? = null
    private var navigator: Navigator? = null

    fun loadHabit(id: Int) {
        if (habit.value?.id != id) {
            repository.habits.value?.firstOrNull { it.id == id }?.let { habit ->
                _habit.postValue(habit)
            }
        }
    }

    fun saveState(habitModel: HabitModel) {
        _habit.postValue(habitModel)
    }

    fun onSaveClicked(habitModel: HabitModel, isNewHabit: Boolean) {
        val errorTitle = getErrorTitle(habitModel)

        when {
            errorTitle != null -> showingMessage?.showMessage(errorTitle)
            else -> {
                navigator?.onSave()
                saveHabit(habitModel, isNewHabit)
            }
        }
    }

    private fun saveHabit(habitModel: HabitModel, isNewHabit: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveHabit(habitModel, isNewHabit)
        }
    }

    private fun getErrorTitle(habit: HabitModel): String? {
        return when (true) {
            isEmptyText(habit.name) -> App.getContext()?.getString(R.string.error_empty_habit)
            isIncorrectRange(habit.countRepeats) -> App.getContext()?.getString(R.string.error_repeats_incorrect)
            isIncorrectRange(habit.interval) -> App.getContext()?.getString(R.string.error_interval)
            else -> null
        }
    }

    private fun isIncorrectRange(number: Int): Boolean {
        return number <= 0
    }

    private fun isEmptyText(text: String): Boolean {
        return text.trim().isEmpty()
    }

    fun setShowingMessage(obj: ShowingMessage) {
        showingMessage = obj
    }

    fun setNavigator(obj: Navigator) {
        navigator = obj
    }

    fun onDestroyView() {
        showingMessage = null
        navigator = null
    }
}
