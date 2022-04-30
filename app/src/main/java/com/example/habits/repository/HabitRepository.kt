package com.example.habits.repository

import android.graphics.Color
import androidx.lifecycle.LiveData
import com.example.habits.model.HabitModel
import com.example.habits.model.HabitPriority
import com.example.habits.model.HabitType
import com.example.habits.room.HabitDao
import com.example.habits.room.HabitEntity
import com.example.habits.utils.map


class HabitRepository(private val habitsDao: HabitDao) : IHabitRepository {

    override val habits = habitsDao.getAll().map { it.map { entity -> entity.toModel() } }

    override fun getHabits(type: HabitType): LiveData<List<HabitModel>> =
        habitsDao.getAll(type).map { it.map { entity -> entity.toModel() } }

    override fun getHabit(id: Int): LiveData<HabitModel> =
        habitsDao.getHabit(id).map { it?.toModel() }

    override suspend fun updateHabit(habit: HabitModel) {
        habitsDao.update(HabitEntity.fromModel(habit))
    }

    override suspend fun deleteHabit(habit: HabitModel) {
        habitsDao.delete(HabitEntity.fromModel(habit))
    }

    override suspend fun saveHabit(habit: HabitModel, isNewHabit: Boolean) {
        if (isNewHabit) {
            habitsDao.insert(HabitEntity.fromModel(habit))
        } else {
            habitsDao.update(HabitEntity.fromModel(habit))
        }
    }

    private suspend fun addRandomHabits() {
        (0..20).forEach {
            val type = if (it % 2 == 0) {
                HabitType.GOOD
            } else {
                HabitType.BAD
            }
            val habit = HabitModel(
                id = it,
                name = it.toString(),
                countRepeats = 1213,
                description = "My best habit!",
                color = Color.RED,
                interval = 31233122,
                type = type,
                priority = HabitPriority.HIGH
            )
            saveHabit(habit, isNewHabit = true)
        }
    }

    private suspend fun deleteAll() {
        habitsDao.deleteAll()
    }
}