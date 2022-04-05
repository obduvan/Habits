package com.example.habits.ui.habits

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.habits.R
import com.example.habits.databinding.FragmentPagerHabitsBinding
import com.example.habits.model.HabitType
import com.google.android.material.tabs.TabLayoutMediator


class HabitsPagerFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentPagerHabitsBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException(
            "Binding is only valid between onCreateView and onDestroyView."
        )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPagerHabitsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PagerHabitsAdapter(this)
        val viewPager = binding.viewPager

        viewPager.offscreenPageLimit = 1
        viewPager.adapter = adapter
        binding.fabCreateHabit.setOnClickListener { onCreateHabitClick() }


        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            tab.text = context?.getString(HabitType.values()[position].resourceId) ?: ""
        }.attach()
    }

    private fun onCreateHabitClick() {
        findNavController().navigate(R.id.action_navigation_habits_to_navigation_edit_habit)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        TODO("Not yet implemented")

    }

    override fun onQueryTextChange(p0: String?): Boolean {
        TODO("Not yet implemented")
    }
}