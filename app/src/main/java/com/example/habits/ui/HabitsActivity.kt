package com.example.habits.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habits.HabitRepositoryTest
import com.example.habits.OnHabitRepositoryListener
import com.example.habits.databinding.ActivityHabitsBinding
import com.example.habits.model.HabitModel


const val KEY_POSITION = "POSITION"
const val KEY_HABIT = "HABIT"


class HabitsActivity : AppCompatActivity(), OnHabitListener, OnHabitRepositoryListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterHabit: HabitAdapter
    private lateinit var binding: ActivityHabitsBinding

    private val habitRepository = HabitRepositoryTest(this)

    private val getNewHabit =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val newHabitModel = it.data?.getParcelableExtra<HabitModel>(KEY_HABIT)

                newHabitModel?.let { habit ->
                    habitRepository.addHabit(habit)
                    recyclerView.post { recyclerView.smoothScrollToPosition(0) }
                }
            }
        }

    private val getModifiedHabit =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val habitModel = it.data?.getParcelableExtra<HabitModel>(KEY_HABIT)
                val position = it.data?.getIntExtra(KEY_POSITION, -1) ?: -1

                if (habitModel != null && position >= 0) {
                    habitRepository.changeHabit(position, habitModel)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHabitsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabCreateHabit.setOnClickListener { onCreateHabitClick() }

        adapterHabit = HabitAdapter(this)
        recyclerView = binding.habits
        recyclerView.setHasFixedSize(true)

        with(recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterHabit
        }


        loadTestHabits()
    }

    private fun loadTestHabits() {
        adapterHabit.submitList(habitRepository.getHabits())
    }

    private fun onCreateHabitClick() {
        getNewHabit.launch(
            Intent(this, EditHabitActivity::class.java)
        )
    }

    override fun onHabitClick(position: Int) {
        getModifiedHabit.launch(
            Intent(this, EditHabitActivity::class.java).apply {
                putExtra(KEY_POSITION, position)
                putExtra(KEY_HABIT, habitRepository.getHabit(position))
            }
        )
    }

    override fun habitsUpdate(newHabits: List<HabitModel>) {
        adapterHabit.submitList(newHabits)
    }
}
