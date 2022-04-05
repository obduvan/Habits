package com.example.habits.ui.editHabit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habits.HabitRepositoryTest
import com.example.habits.model.HabitModel

class EditHabitViewModel: ViewModel() {
    private val repositoryTest = HabitRepositoryTest

    private val _habitLiveData = MutableLiveData<HabitModel>()
    val habitLiveData: LiveData<HabitModel> = _habitLiveData


    fun loadHabit(id: Int){
        if (_habitLiveData.value == null){
            _habitLiveData.value = repositoryTest.getHabit(id)
        }
    }

    fun saveStateHabit(habitModel: HabitModel) {
        _habitLiveData.value = habitModel
    }

    fun saveHabit(id: Int, habitModel: HabitModel) {
        if (id == -1) {
            repositoryTest.addHabit(habitModel)
        } else {
            repositoryTest.updateHabit(id, habitModel)
        }
    }
}
