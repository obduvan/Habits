package com.example.habits.habits.viewPager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.domain.entities.HabitType

import com.example.habits.habits.HabitsFragment


class PagerHabitsAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    companion object {
        private const val NUM_PAGES = 2
    }

    override fun getItemCount() = HabitType.values().size

    override fun createFragment(position: Int): Fragment {
        return HabitsFragment.newInstance(HabitType.values()[position])
    }
}
