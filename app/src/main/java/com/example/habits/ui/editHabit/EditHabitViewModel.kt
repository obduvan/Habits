package com.example.habits.ui.editHabit

import android.util.Log
import androidx.lifecycle.*
import com.example.habits.R
import com.example.habits.model.HabitModel
import com.example.habits.net.ApiResponse
import com.example.habits.repository.IHabitRepository
import com.example.habits.utils.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ValidationResult(
    val isSuccessful: Boolean,
    val error: String? = null
)

class EditHabitViewModel(private val repository: IHabitRepository) : ViewModel() {

    private var _habit = MutableLiveData<HabitModel>()
        set(value) {
            field = value
            habit = value
        }
    var habit: LiveData<HabitModel> = _habit

    private var showingMessage: ShowingMessage? = null
    private var navigator: Navigator? = null

    fun loadHabit(id: String) {
        if (habit.value?.id != id) {
            _habit = repository.getHabit(id) as MutableLiveData<HabitModel>
        }
    }

    fun saveState(habitModel: HabitModel) {
        _habit.postValue(habitModel)
    }

    private fun saveHabit(habitModel: HabitModel, isNewHabit: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            val response =
                withContext(Dispatchers.IO) { repository.saveHabit(habitModel, isNewHabit) }
            if (response is ApiResponse.Success) {
                navigator?.onSave()
            }
            if (response is ApiResponse.Error) {
                showingMessage?.showMessage("Can't connect to server.")
            }
        }
    }

    fun onSaveClicked(habitModel: HabitModel, isNewHabit: Boolean) {
        val validationResult = getErrorTitle(habitModel)

        when (validationResult.isSuccessful) {
            false -> validationResult.error?.let { error ->
                showingMessage?.showMessage(error)
            }
            true -> {
                saveHabit(habitModel, isNewHabit)
            }
        }
    }

    private fun getErrorTitle(habit: HabitModel): ValidationResult {
        val context = App.getContext()

        return when (true) {
            isEmptyText(habit.name) -> ValidationResult(
                false, context?.getString(R.string.error_empty_habit)
            )
            isIncorrectRange(habit.countRepeats) -> ValidationResult(
                false, context?.getString(R.string.error_repeats_incorrect)
            )
            isIncorrectRange(habit.interval) -> ValidationResult(
                false, context?.getString(R.string.error_interval)
            )
            else -> ValidationResult(true)
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
