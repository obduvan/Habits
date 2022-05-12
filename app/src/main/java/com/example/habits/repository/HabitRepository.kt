package com.example.habits.repository

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.habits.model.HabitModel
import com.example.habits.model.HabitPriority
import com.example.habits.model.HabitType
import com.example.habits.model.HabitUid
import com.example.habits.net.ApiResponse
import com.example.habits.net.DTO.HabitDTO
import com.example.habits.net.NetworkClient
import com.example.habits.net.retryRequest
import com.example.habits.room.HabitDao
import com.example.habits.room.HabitEntity
import com.example.habits.utils.getIntTime
import com.example.habits.utils.map
import retrofit2.HttpException
import java.io.IOException
import java.lang.RuntimeException


class HabitRepository(private val habitsDao: HabitDao) : IHabitRepository {

    override val habits = habitsDao.getAll().map { it.map { entity -> entity.toModel() } }

    override suspend fun loadHabits(): ApiResponse<Unit> {
        return try {
            val response = retryRequest { NetworkClient.habitAPI.getHabits() }
            Log.i(HabitRepository::class.java.name, response.toString())

            response.forEach {
                updateLocalDatabase(it.toHabitModel())
            }

            ApiResponse.Success(data = Unit)
        } catch (e: RuntimeException) {
            handleErrors(e)
        }
    }

    override fun getHabit(id: String): LiveData<HabitModel> =
        habitsDao.getHabit(id).map { it?.toModel() }

    private suspend fun updateLocalDatabase(habit: HabitModel) {
        val existingHabit = habitsDao.checkExistHabit(habit.id)
        if (existingHabit != null) {
            if (existingHabit.date < habit.date) {
                Log.i(HabitRepository::class.java.name, "${existingHabit.name} new date.")
                habitsDao.update(HabitEntity.fromModel(habit))
            }
        } else {
            Log.i(HabitRepository::class.java.name, "${habit.name} new habit.")
            habitsDao.insert(HabitEntity.fromModel(habit))
        }
    }

    override suspend fun deleteHabit(habit: HabitModel) {
        habitsDao.delete(HabitEntity.fromModel(habit))
    }

    override suspend fun saveHabit(habit: HabitModel, isNewHabit: Boolean): ApiResponse<HabitUid> {
        return try {
            val response =
                retryRequest { NetworkClient.habitAPI.saveHabit(HabitDTO.fromHabitModel(habit)) }

            val apiResponse = ApiResponse.Success(HabitUid(response.uid))

            updateLocalDatabase(habit.copy(id = apiResponse.data.uid))
            return apiResponse

        } catch (e: RuntimeException) {
            handleErrors(e)
        }
    }

    private fun <T> handleErrors(e: RuntimeException): ApiResponse<T> {
        return when (e) {
            is HttpException -> ApiResponse.Error(e)
            is IOException -> ApiResponse.Error(e)
            else -> ApiResponse.Error(e)
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
                id = it.toString(),
                name = it.toString(),
                countRepeats = 1213,
                description = "My best habit!",
                color = Color.RED,
                interval = 31233122,
                type = type,
                date = getIntTime(),
                priority = HabitPriority.HIGH
            )
            saveHabit(habit, isNewHabit = true)
        }
    }

    suspend fun deleteAll() {
        habitsDao.deleteAll()
    }
}