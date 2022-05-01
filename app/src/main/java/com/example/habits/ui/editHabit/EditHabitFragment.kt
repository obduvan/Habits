package com.example.habits.ui.editHabit


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.habits.R
import com.example.habits.databinding.FragmentEditHabitBinding
import com.example.habits.model.HabitModel
import com.example.habits.model.HabitPriority
import com.example.habits.model.HabitType
import com.example.habits.ui.editHabit.views.ColorWorker
import com.example.habits.ui.habits.KEY_POSITION
import com.example.habits.utils.App
import com.example.habits.utils.HabitsViewModelFactory


interface ShowingMessage {
    fun showMessage(message: String)
}

interface Navigator {
    fun onSave()
}

class EditHabitFragment : Fragment(), ShowingMessage, Navigator {

    private var _binding: FragmentEditHabitBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException(
            "Binding is only valid between onCreateView and onDestroyView."
        )

    private var colorWorker: ColorWorker? = null
    private var idHabit: Int = 0

    private val isNewHabit: Boolean
        get() = idHabit == 0

    private val viewModel: EditHabitViewModel by viewModels { HabitsViewModelFactory((requireActivity().application as App).repository) }

//    private var viewModel: EditHabitViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idHabit = arguments?.getInt(KEY_POSITION, idHabit) ?: idHabit
//
//        viewModel = ViewModelProvider(
//            requireActivity(),
//            HabitsViewModelFactory((requireActivity().application as App).repository)
//        )[EditHabitViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        colorWorker = ColorWorker(
            binding.rvColor,
            binding.rvBackground,
            binding.selectedColor,
            binding.grbColor,
            binding.hsvColor,
        )
        viewModel.setShowingMessage(this)
        viewModel.setNavigator(this)

        if (!isNewHabit) {
            viewModel.loadHabit(idHabit)
        }

        viewModel.habit.observe(viewLifecycleOwner) { initViews(it) }
        binding.buttonSave.setOnClickListener { onSaveButtonClicked() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.saveState(habitModel = getHabitModel())
    }

    private fun initViews(habit: HabitModel) {
        with(habit) {
            binding.let { b ->
                b.name.setText(name)
                b.description.setText(description)
                b.countRepeats.setText(setNumberView(countRepeats))
                b.interval.setText(setNumberView(interval))
                b.selectedColor.setCardBackgroundColor(color)

                colorWorker?.setSelectedColor(color)
                b.prioritySpinner.setSelection(priority.ordinal)
                (b.types.getChildAt(type.ordinal) as RadioButton).isChecked = true
                (b.types.getChildAt((type.ordinal + 1) % HabitType.values().size) as RadioButton).isChecked =
                    false
            }
        }
    }

    private fun onSaveButtonClicked() {
        viewModel.onSaveClicked(habitModel = getHabitModel(), isNewHabit)
    }

    private fun getHabitModel(): HabitModel {
        return HabitModel(
            id = idHabit,
            name = binding.name.text.toString(),
            description = binding.description.text.toString(),
            color = colorWorker?.getSelectedColor() ?: Color.WHITE,
            countRepeats = binding.countRepeats.text.toString().toIntOrNull() ?: 0,
            interval = binding.interval.text.toString().toIntOrNull() ?: 0,
            priority = HabitPriority.valueOf(
                binding.prioritySpinner.selectedItem.toString().uppercase()
            ),
            type = getSelectedType(),
        )
    }

    private fun setNumberView(number: Int): String {
        return if (number == 0) {
            ""
        } else {
            number.toString()
        }
    }

    private fun getSelectedType(): HabitType {
        return when (binding.types.checkedRadioButtonId) {
            R.id.good_type -> HabitType.GOOD
            else -> HabitType.BAD
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onSave() {
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.onDestroyView()
    }
}
