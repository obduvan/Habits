package com.example.habits.habits.viewPager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.domain.entities.HabitType
import com.example.habits.R
import com.example.habits.ResourceUtils.Companion.getResourceType
import com.example.habits.databinding.FragmentPagerHabitsBinding
import com.google.android.material.tabs.TabLayoutMediator


class HabitsPagerFragment : Fragment() {

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
            tab.text =
                context?.getString(getResourceType(HabitType.values()[position]))
                    ?: ""
        }.attach()
    }

    private fun onCreateHabitClick() {
        findNavController().navigate(R.id.action_navigation_habits_to_navigation_edit_habit)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
