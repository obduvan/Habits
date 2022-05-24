package com.example.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.entities.HabitModel
import com.example.domain.entities.HabitPriority
import com.example.domain.entities.HabitType

@Entity(tableName = "Habits")
data class HabitEntity(
    @PrimaryKey
    var id: String,
    var name: String,
    var description: String,
    var color: Int,
    var countRepeats: Int,
    var interval: Int,
    var date: Int,
    var type: HabitType,
    var priority: HabitPriority,
) {

    companion object {
        fun fromModel(model: HabitModel): HabitEntity = HabitEntity(
            id = model.id,
            name = model.name,
            description = model.description,
            color = model.color,
            date = model.date,
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
        priority = priority,
        date = date
    )
}



