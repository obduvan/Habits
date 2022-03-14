package com.example.habits.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habits.HabitRepositoryTest
import com.example.habits.databinding.ActivityHabitsBinding

class HabitsActivity : AppCompatActivity(), OnViewListener {
    private lateinit var binding: ActivityHabitsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHabitsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapterHabit = HabitAdapter(this)
        val recyclerView = binding.habits
        recyclerView.setHasFixedSize(true)

        with(recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterHabit
        }


        loadTestHabits(adapterHabit)
    }


    private fun loadTestHabits(adapterHabit: HabitAdapter){
        adapterHabit.submitList(HabitRepositoryTest.getHabits())
    }

    override fun onViewClick(position: Int) {
        val intent = Intent(this, EditHabitActivity::class.java)
        startActivity(intent)
        Log.e(javaClass.toString(), position.toString())
    }
}

