package com.example.habits.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habits.repository.IHabitRepository
import com.example.habits.ui.editHabit.EditHabitViewModel
import com.example.habits.ui.habits.HabitsViewModel

class ViewModelFactory(private val habitRepository: IHabitRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = when(modelClass){
            HabitsViewModel :: class.java ->{
                HabitsViewModel(habitRepository)

            }
            EditHabitViewModel :: class.java -> {
                EditHabitViewModel(habitRepository)
            }
            else -> throw IllegalStateException("Unknown ViewModel class.")
        }

        return viewModel as T
    }
}