package com.example.habits.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.useCases.HabitsUseCase

import com.example.habits.editHabit.EditHabitViewModel
import com.example.habits.habits.HabitsViewModel

class HabitsViewModelFactory(private val habitsUseCase: HabitsUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            HabitsViewModel::class.java -> {
                HabitsViewModel(habitsUseCase)
            }

            EditHabitViewModel::class.java -> {
                EditHabitViewModel(habitsUseCase)
            }
            else -> throw IllegalStateException("Unknown ViewModel class.")
        }

        return viewModel as T
    }
}
