package com.example.habits.ui.editHabit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habits.model.HabitModel
import com.example.habits.repository.IHabitRepository

class EditHabitViewModel(private val repository: IHabitRepository) : ViewModel() {

    private var _habitLiveData = MutableLiveData<HabitModel>()
    var habitLiveData: LiveData<HabitModel> = _habitLiveData

    fun loadHabit(id: Int): LiveData<HabitModel> {
        if (_habitLiveData.value == null) {
            _habitLiveData = repository.getHabit(id) as MutableLiveData<HabitModel>
        }
        return _habitLiveData
    }

    fun saveStateHabit(habitModel: HabitModel) {
        _habitLiveData.postValue(habitModel)
    }

    fun saveHabit(habitModel: HabitModel) {
        repository.saveHabit(habitModel)
    }
}
