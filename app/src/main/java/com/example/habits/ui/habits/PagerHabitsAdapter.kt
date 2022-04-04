package com.example.habits.ui.habits

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.habits.model.HabitType


class PagerHabitsAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    companion object {
        private const val NUM_PAGES = 2
    }

    override fun getItemCount() = HabitType.values().size

    override fun createFragment(position: Int): Fragment {
        return HabitsFragment.newInstance(HabitType.values()[position])
    }
}
