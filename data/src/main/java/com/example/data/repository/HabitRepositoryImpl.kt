package com.example.data.repository

import android.util.Log
import com.example.data.network.DTO.HabitDTO
import com.example.data.network.DTO.HabitUidDTO
import com.example.data.network.NetworkClient
import com.example.data.network.retryRequest
import com.example.data.database.HabitDao
import com.example.data.database.HabitEntity
import com.example.domain.ApiResponse
import com.example.domain.entities.HabitModel
import com.example.domain.entities.HabitUid
import com.example.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import retrofit2.HttpException
import java.io.IOException
import java.lang.RuntimeException


class HabitRepositoryImpl(private val habitsDao: HabitDao) :
    HabitRepository {

    override fun getHabits(): Flow<List<HabitModel>> {
        return habitsDao.getAll().map { it.map { entity -> entity.toModel() } }
    }

    override suspend fun loadHabits(): ApiResponse<Unit> {
        return try {
            val response = retryRequest { NetworkClient.habitAPI.getHabits() }
            Log.i(HabitRepositoryImpl::class.java.name, response.toString())

            response.forEach {
                updateLocalDatabase(it.toHabitModel())
            }

            ApiResponse.Success(data = Unit)
        } catch (e: RuntimeException) {
            handleErrors(e)
        }
    }

    override fun getHabit(id: String): Flow<HabitModel> =
        habitsDao.getHabit(id).map { it.toModel() }

    private suspend fun updateLocalDatabase(habit: HabitModel) {
        val existingHabit = habitsDao.checkExistHabit(habit.id)
        if (existingHabit != null) {
            if (existingHabit.date < habit.date) {
                Log.i(HabitRepositoryImpl::class.java.name, "${existingHabit.name} new date.")
                habitsDao.update(HabitEntity.fromModel(habit))
            }
        } else {
            Log.i(HabitRepositoryImpl::class.java.name, "${habit.name} new habit.")
            habitsDao.insert(HabitEntity.fromModel(habit))
        }
    }

    override suspend fun deleteHabit(habit: HabitModel): ApiResponse<Unit> {
        return try {
            retryRequest { NetworkClient.habitAPI.deleteHabit(HabitUidDTO(habit.id)) }
            habitsDao.delete(HabitEntity.fromModel(habit))
            ApiResponse.Success(data = Unit)
        } catch (e: RuntimeException) {
            handleErrors(e)
        }
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

//    private suspend fun addRandomHabits() {
//        (0..20).forEach {
//            val type = if (it % 2 == 0) {
//                HabitType.GOOD
//            } else {
//                HabitType.BAD
//            }
//            val habit = HabitModel(
//                id = it.toString(),
//                name = it.toString(),
//                countRepeats = 1213,
//                description = "My best habit!",
//                color = Color.RED,
//                interval = 31233122,
//                type = type,
//                date = getIntTime(),
//                priority = HabitPriority.HIGH
//            )
//            saveHabit(habit, isNewHabit = true)
//        }
//    }

    suspend fun deleteAll() {
        habitsDao.deleteAll()
    }
}