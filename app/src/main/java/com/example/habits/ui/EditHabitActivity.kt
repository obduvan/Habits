package com.example.habits.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.habits.model.HabitPriority
import com.example.habits.model.HabitType
import com.example.habits.databinding.ActivityEditHabitBinding
import com.google.android.material.textfield.TextInputEditText

class EditHabitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditHabitBinding
    private var name: TextInputEditText? = null
    private var description: String? = null
    private var count: Int? = null
    private var daysInterval: Int? = null
    private var habitPriority: HabitPriority? = null
    private var type: HabitType? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditHabitBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val bt = binding.bt
//        name = binding.habitName
//
//        bt.setOnClickListener {
//            Toast.makeText(this, name?.text.toString(), Toast.LENGTH_SHORT).show()
//        }


    }



}