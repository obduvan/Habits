package com.example.habits.ui.editHabit

import androidx.lifecycle.ViewModel
import com.example.habits.HabitRepositoryTest
import com.example.habits.model.HabitModel
import com.example.habits.model.HabitPriority
import com.example.habits.model.HabitType

class EditHabitViewModel : ViewModel() {

    private var repositoryTest = HabitRepositoryTest

    var name: String = "3213"
    var name1: String = "3213111111"
    var description = ""
    var color = 0
    var countRepeats = 0
    var interval = 0

    var type: HabitType? = null
    var priority: HabitPriority? = null
    var habitModel: HabitModel? = null


    fun loadHabit(id: Int): HabitModel? {
        val habitModel= repositoryTest.getHabit(id)
        name = habitModel?.name ?: "111"

//        habitModel?.let {
//            name = it.name
//            description = it.description
//            color = it.color
//            countRepeats = it.countRepeats
//            interval =
//        }

//        if (habitModel != null){
//
//                color = color
//            }
//        }

        return habitModel


    }

    fun saveHabit(id: Int, habitModel: HabitModel) {
        if (id == -1) {
            repositoryTest.addHabit(habitModel)
        } else {
            repositoryTest.updateHabit(id, habitModel)
        }
    }
}