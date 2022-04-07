package com.example.habits.ui.habits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habits.R
import com.example.habits.databinding.FragmentHabitsBinding
import com.example.habits.model.HabitType


const val KEY_POSITION = "POSITION"


class HabitsFragment : Fragment(), OnHabitListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterHabit: HabitAdapter

    //    private val viewModel: HabitsViewModel by viewModels()
    private lateinit var viewModel: HabitsViewModel


    private var habitType: HabitType? = null
    private var _binding: FragmentHabitsBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException(
            "Binding is only valid between onCreateView and onDestroyView."
        )

    companion object {
        private const val KEY_TYPE_HABITS = "TYPE"

        fun newInstance(type: HabitType): HabitsFragment {
            val fragment = HabitsFragment()
            fragment.arguments = bundleOf(KEY_TYPE_HABITS to type)
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val habitType = arguments?.get(KEY_TYPE_HABITS) as? HabitType

        habitType?.let {
            viewModel =
                ViewModelProvider(requireActivity())[habitType.name, HabitsViewModel::class.java]
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val habitType = arguments?.get(KEY_TYPE_HABITS) as? HabitType

        habitType?.let { viewModel.loadHabits(it) }

        _binding = FragmentHabitsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterHabit = HabitAdapter(this)

        recyclerView = binding.habitsRecycler
        recyclerView.setHasFixedSize(true)

        with(recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterHabit
        }

        viewModel.habitList.observe(viewLifecycleOwner) { habits ->
            adapterHabit.submitList(habits)
        }
    }

    override fun onHabitClick(position: Int) {
        findNavController().navigate(
            R.id.action_navigation_habits_to_navigation_edit_habit,
            bundleOf(KEY_POSITION to position)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}