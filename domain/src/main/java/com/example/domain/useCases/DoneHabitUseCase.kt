package com.example.domain.useCases

import android.util.Log
import com.example.domain.api.ApiResponse
import com.example.domain.api.DoneMessage
import com.example.domain.entities.HabitModel
import com.example.domain.entities.HabitType
import com.example.domain.getSecondsTime
import com.example.domain.repository.HabitRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DoneHabitUseCase @Inject constructor(private val habitRepository: HabitRepository) {

    suspend fun doneHabit(id: String, doneTimeSeconds: Int): ApiResponse<DoneMessage> {
        val habit = habitRepository.getHabit(id).first()

        var message: String? = null
        var habitMustBeUpdated = true
        val lastDoneTaskTime: Int? = habit.doneDates.lastOrNull()

        if (lastDoneTaskTime != null) {
            val countMayCanBeDone = habit.countRepeats - habit.doneDates.count() - 1

            Log.e(HabitsUseCase::class.java.name, countMayCanBeDone.toString())
            if (countMayCanBeDone == 0) {
                message = COMPLETE_TEXT
            } else if (countMayCanBeDone < 0) {
                message = RETRY_DO_TEXT
                habitMustBeUpdated = false
            } else {
                val countCanBeDone: Int =
                    ((doneTimeSeconds - lastDoneTaskTime) / (habit.interval * 3600)) - 1

                if (countCanBeDone >= 1) {
                    message = MAY_DO_TEXT.format(countCanBeDone)

                } else if (countCanBeDone == 0) {
                    message = DONE_TEXT
                } else {
                    if (habit.type == HabitType.GOOD) {
                        message = GOOD_STOP_TEXT
                    }
                    if (habit.type == HabitType.BAD) {
                        message = BAD_STOP_TEXT
                    }
                }
            }
        } else {
            message = DONE_TEXT
            if (habit.countRepeats == 1) {
                message = COMPLETE_TEXT
            }
        }

        if (habitMustBeUpdated) {
            updateDoneHabit(doneTimeSeconds, habit)
        }
        return ApiResponse.Success(DoneMessage(text = message))
    }

    private suspend fun updateDoneHabit(doneTimeSeconds: Int, habit: HabitModel) {
        val newDates: List<Int> = listOf(*habit.doneDates.toTypedArray(), doneTimeSeconds)
        val newHabit = habit.copy(doneDates = newDates, date = getSecondsTime())
        habitRepository.doneHabit(newHabit, doneTimeSeconds)
    }

    companion object {
        private const val COMPLETE_TEXT = "Вы завершили привычку!"
        private const val DONE_TEXT = "Сделано!"
        private const val RETRY_DO_TEXT = "Данная привычка завершена."
        private const val GOOD_STOP_TEXT =  "You are breathtaking!"
        private const val BAD_STOP_TEXT ="Хватит это делать"
        private const val MAY_DO_TEXT = "Можете выполнить еще %d раз"
    }
}