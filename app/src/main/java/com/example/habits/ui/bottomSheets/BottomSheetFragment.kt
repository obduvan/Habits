package com.example.habits.ui.bottomSheets

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.habits.databinding.FragmentBottomSheetBinding
import com.example.habits.model.HabitModel
import com.example.habits.model.HabitType
import com.example.habits.ui.habits.HabitsViewModel

class BottomSheetFragment : Fragment() {

    private var _binding: FragmentBottomSheetBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException(
            "Binding is only valid between onCreateView and onDestroyView."
        )
    private lateinit var viewModelGood: HabitsViewModel
    private lateinit var viewModelBad: HabitsViewModel

    private val nameComparator =
        Comparator { h1: HabitModel, h2: HabitModel -> h1.name.compareTo(h2.name) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelGood =
            ViewModelProvider(requireActivity())[HabitType.GOOD.name, HabitsViewModel::class.java]
        viewModelBad =
            ViewModelProvider(requireActivity())[HabitType.BAD.name, HabitsViewModel::class.java]
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

        binding.searchByName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val filter = p0.toString()
                viewModelGood.setFilter(filter)
                viewModelBad.setFilter(filter)
            }
        })

        binding.switcherSortByName.setOnClickListener {
            viewModelGood.setSort(nameComparator)
            viewModelBad.setSort(nameComparator)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}