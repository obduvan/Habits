package com.example.habits.ui.editHabit

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habits.HabitRepositoryTest
import com.example.habits.model.HabitModel
import com.example.habits.model.HabitPriority
import com.example.habits.model.HabitType
import com.example.habits.utils.enumValueOfOrNull

class EditHabitViewModel(private val habitId: Int) : ViewModel() {
    private var repositoryTest = HabitRepositoryTest

    var habitModel: HabitModel? = null

    var habitLiveData = MutableLiveData<HabitModel>()


    init {
        habitModel = repositoryTest.getHabit(habitId)?.copy()

        Log.e("sda", habitModel?.name ?: "")
        Log.e("sda", habitId.toString())
    }


    var name = ""
        set(value) {
            habitModel?.name = value
            field = value
            Log.e("name",value)
        }
        get() {
            return  field
        }

    var description = ""
        set(value) {
            habitModel?.description = value
            field = value
        }
        get() {
            return  field
        }

    var color: Int? = null
        set(value) {
            value?.let { habitModel?.color = it }
            field = value
        }
        get() {
            return field
        }


    var countRepeats = ""
        set(value) {
            habitModel?.countRepeats = value.toIntOrNull() ?: 0
            field = value
        }
        get() {
            return field
        }

    var interval = ""
        set(value) {
            habitModel?.interval = value.toIntOrNull() ?: 0
            field = value
        }
        get() {
            return  field
        }

    var typePosition = 0
        set(value) {
            habitModel?.type = HabitType.values()[value]
            field = value
        }
        get() {
            return field
        }


    var priorityPosition = 0
        set(value) {
            habitModel?.priority = HabitPriority.values()[value]
            field = value
        }
        get() {
            return field
        }


    fun loadHabit(id: Int) {

    }

    fun saveHabit(id: Int, habitModel: HabitModel) {
        if (id == -1) {
            repositoryTest.addHabit(habitModel)
        } else {
            repositoryTest.updateHabit(id, habitModel)
        }
    }
}


class ViewModelFactory(private val HabitId: Int) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            EditHabitViewModel::class.java -> {
                EditHabitViewModel(HabitId)
            }
            else -> throw IllegalStateException("Unknown ViewModel class")
        }
        return viewModel as T
    }

}