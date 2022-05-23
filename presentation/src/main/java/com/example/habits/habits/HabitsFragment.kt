package com.example.habits.habits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habits.R
import com.example.habits.databinding.FragmentHabitsBinding
import com.example.domain.entities.HabitType
import com.example.habits.editHabit.ShowingMessage
import com.example.habits.App
import com.example.habits.utils.HabitsViewModelFactory


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

    private val showingMessage = object : ShowingMessage {
        override fun showMessage(message: String) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val KEY_TYPE_HABITS = "TYPE"

        fun newInstance(type: HabitType): HabitsFragment {
            val fragment = HabitsFragment()
            fragment.arguments = bundleOf(KEY_TYPE_HABITS to type)
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val type = arguments?.get(KEY_TYPE_HABITS) as? HabitType
        habitType = type

        type?.let {
            viewModel = ViewModelProvider(
                requireActivity(),
                HabitsViewModelFactory((requireActivity().application as App).habitsUseCase)
            )[type.name, HabitsViewModel::class.java]
        }
        viewModel.loadHabits()


        _binding = FragmentHabitsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterHabit = HabitAdapter(this)

        recyclerView = binding.habitsRecycler
        recyclerView.setHasFixedSize(true)
        viewModel.setShowingMessage(showingMessage)
        with(recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterHabit
        }

        val type = habitType
        if (type != null) {
            viewModel.getHabits(type).observe(viewLifecycleOwner) { habits ->
                adapterHabit.submitList(habits)
            }
        }
    }

    override fun onHabitClick(position: String) {
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