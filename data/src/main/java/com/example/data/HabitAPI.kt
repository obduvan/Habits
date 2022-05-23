package com.example.data


import com.example.data.DTO.HabitDTO
import com.example.data.DTO.HabitDoneDTO
import com.example.data.DTO.HabitUidDTO
import retrofit2.http.*

interface HabitAPI {

    @GET("habit")
    suspend fun getHabits(): List<HabitDTO>

    @PUT("habit")
    suspend fun saveHabit(@Body habitDTO: HabitDTO): HabitUidDTO

    @HTTP(method = "DELETE", path = "habit", hasBody = true)
    suspend fun deleteHabit(@Body habitUidDTO: HabitUidDTO)

    @POST("habit_done")
    suspend fun completeHabit(@Body habitDoneDTO: HabitDoneDTO)
}