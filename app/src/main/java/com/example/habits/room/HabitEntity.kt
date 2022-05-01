package com.example.habits.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.habits.model.HabitModel
import com.example.habits.model.HabitPriority
import com.example.habits.model.HabitType
import java.util.*

@Entity(tableName = "Habits")
data class HabitEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var description: String,
    var color: Int,
    var countRepeats: Int,
    var interval: Int,
    var type: HabitType,
    var priority: HabitPriority,

) {

    companion object {
        fun fromModel(model: HabitModel): HabitEntity = HabitEntity(
            id = model.id,
            name = model.name,
            description = model.description,
            color = model.color,
            countRepeats = model.countRepeats,
            interval = model.interval,
            type = model.type,
            priority = model.priority
        )
    }

    fun toModel(): HabitModel = HabitModel(
        id = id,
        name = name,
        description = description,
        color = color,
        countRepeats = countRepeats,
        interval = interval,
        type = type,
        priority = priority
    )
}



