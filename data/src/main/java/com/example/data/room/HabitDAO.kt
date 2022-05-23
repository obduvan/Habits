package com.example.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.habits.model.HabitType

@Dao
interface HabitDao {
    @Query("select * from Habits")
    fun getAll(): LiveData<List<HabitEntity>>

    @Query("select * from Habits where type == :type")
    fun getAll(type: HabitType): LiveData<List<HabitEntity>>

    @Query("select * from Habits where id == :id")
    fun getHabit(id: String): LiveData<HabitEntity?>

    @Query("select * from Habits where id == :id")
    suspend fun checkExistHabit(id: String): HabitEntity?

    @Delete
    suspend fun delete(habit: HabitEntity)

    @Update
    suspend fun update(habit: HabitEntity)

    @Insert
    suspend fun insert(habit: HabitEntity)

    @Query("delete from Habits")
    suspend fun deleteAll()
}