package com.example.habits.ui.habits

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habits.R
import com.example.habits.databinding.FragmentHabitsBinding
import com.example.habits.model.HabitType


const val KEY_POSITION = "POSITION"
//const val KEY_HABIT = "HABIT"
//const val KEY_HABIT_OPERATION = "HABIT_OPERATION"


class HabitsFragment : Fragment(), OnHabitListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterHabit: HabitAdapter
//    private var habitRepository = HabitRepositoryTest(this)

    private val viewModel: HabitsViewModel by viewModels ()


    private var _binding: FragmentHabitsBinding? = null
    private val baseFragmentManager get() = parentFragment?.parentFragmentManager
    private var habitType: HabitType? =  null
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

//        val typeHabits = arguments?.get(KEY_TYPE_HABITS) as? HabitType ?: return
//
//        baseFragmentManager?.setFragmentResultListener(typeHabits.name, this) { _, bundle ->
//            val habitModel = bundle.getParcelable<HabitModel>(KEY_HABIT)
//            val position = bundle.getInt(KEY_POSITION, -1)
//            val habitOperation = bundle.get(KEY_HABIT_OPERATION) as? HabitOperation
//            executeHabitOperation(habitOperation, position, habitModel)
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        habitType = arguments?.get(KEY_TYPE_HABITS) as? HabitType

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

        viewModel.getHabits(habitType).observe(viewLifecycleOwner){ habits ->
            adapterHabit.submitList(habits)
            Log.e("dsa", habitType?.name ?: "")
        }

//        loadTestHabits()
    }

//    private fun loadTestHabits() {
//        adapterHabit.submitList(habitRepository.getHabits())
//    }
//
//    private fun executeHabitOperation(
//        habitOperation: HabitOperation?, position: Int, habit: HabitModel?
//    ) {
//        when (habitOperation) {
//            HabitOperation.Delete -> habitRepository.deleteHabit(position)
//            HabitOperation.Add -> habitRepository.addHabit(habit)
//            HabitOperation.Change -> habitRepository.changeHabit(position, habit)
//        }
//    }

//    override fun habitsUpdate(newHabits: List<HabitModel>, isScrollUp: Boolean) {
//        adapterHabit.submitList(newHabits)
//        if (isScrollUp) {
//            recyclerView.smoothScrollToPosition(0)
//        }
//    }

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