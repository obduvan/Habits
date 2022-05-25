package com.example.habits.bottomSheets

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.domain.entities.HabitModel
import com.example.domain.entities.HabitType
import com.example.habits.databinding.FragmentBottomSheetBinding

import com.example.habits.habits.HabitsViewModel
import com.example.habits.App
import com.example.habits.utils.HabitsViewModelFactory

class HabitComparator {
    companion object {
        val emptyComparator = Comparator { _: HabitModel, _: HabitModel -> 0 }
        val nameComparator =
            Comparator { h1: HabitModel, h2: HabitModel -> h1.name.compareTo(h2.name) }
    }
}


class BottomSheetFragment : Fragment() {
    private var _binding: FragmentBottomSheetBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException(
            "Binding is only valid between onCreateView and onDestroyView."
        )

    private lateinit var viewModelGood: HabitsViewModel
    private lateinit var viewModelBad: HabitsViewModel

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(p0: Editable?) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val filter = p0.toString()
            viewModelGood.setFilter(filter)
            viewModelBad.setFilter(filter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app = (requireActivity().application as App)
        viewModelGood = ViewModelProvider(
            requireActivity(),
            HabitsViewModelFactory(app.habitsUseCase, app.doneHabitUseCase)
        )[HabitType.GOOD.name, HabitsViewModel::class.java]

        viewModelBad = ViewModelProvider(
            requireActivity(),
            HabitsViewModelFactory(app.habitsUseCase, app.doneHabitUseCase)
        )[HabitType.BAD.name, HabitsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchByName.addTextChangedListener(textWatcher)

        binding.switcherSortByName.setOnCheckedChangeListener { _, isChecked ->
            setComparator(isChecked)
        }
    }

    private fun setComparator(isChecked: Boolean) {
        var comparator = HabitComparator.emptyComparator
        if (isChecked) {
            comparator = HabitComparator.nameComparator
        }
        viewModelGood.setComparator(comparator)
        viewModelBad.setComparator(comparator)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}