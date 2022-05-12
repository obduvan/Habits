package com.example.habits.net


import com.example.habits.net.DTO.HabitDTO
import com.example.habits.net.DTO.HabitDoneDTO
import com.example.habits.net.DTO.HabitUidDTO
import retrofit2.Response
import retrofit2.http.*

interface HabitAPI {

    @GET("habit")
    suspend fun getHabits(): List<HabitDTO>

    @PUT("habit")
    suspend fun saveHabit(@Body habitDTO: HabitDTO): HabitUidDTO

    @DELETE("habit")
    suspend fun deleteHabit(@Body habitDTO: HabitDTO)

    @POST("habit_done")
    suspend fun completeHabit(@Body habitDoneDTO: HabitDoneDTO)
}