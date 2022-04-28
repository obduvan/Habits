package com.example.habits.ui.editHabit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habits.model.HabitModel
import com.example.habits.repository.IHabitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditHabitViewModel(private val repository: IHabitRepository) : ViewModel() {

    private val _habit = MutableLiveData<HabitModel>()
    val habit: LiveData<HabitModel> = _habit

    fun loadHabit(id: Int) {
        repository.habits.value?.first { it.id == id }?.let { h ->
            _habit.postValue(h)

        }
    }

    fun saveHabit(habitModel: HabitModel, isNewHabit: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isNewHabit) {
                habitModel.id = 0
                repository.createHabit(habitModel)
            } else {
                repository.updateHabit(habitModel)
            }
        }
    }
}
