package com.example.habits.ui.editHabit

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.habits.R
import com.example.habits.databinding.FramentEditHabitBinding
import com.example.habits.model.HabitModel
import com.example.habits.model.HabitOperation
import com.example.habits.model.HabitPriority
import com.example.habits.model.HabitType

import com.example.habits.ui.editHabit.views.ColorWorker
import com.example.habits.ui.habits.KEY_HABIT
import com.example.habits.ui.habits.KEY_HABIT_OPERATION
import com.example.habits.ui.habits.KEY_POSITION

class EditHabitFragment : Fragment() {

    private var _binding: FramentEditHabitBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException(
            "Binding is only valid between onCreateView and onDestroyView."
        )

    private var colorWorker: ColorWorker? = null

    private var position: Int = -1
    private var intentColor: Int? = null
    private var firstSelectedType: HabitType? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FramentEditHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        position = arguments?.getInt(KEY_POSITION, position) ?: position
        val habitModel = arguments?.getParcelable<HabitModel>(KEY_HABIT)
        loadHabit(habitModel)

        binding.buttonSave.setOnClickListener { onSaveButtonClicked() }

        colorWorker = ColorWorker(
            binding.rvColor,
            binding.rvBackground,
            binding.selectedColor,
            binding.grbColor,
            binding.hsvColor,
            intentColor,
        )
    }

    private fun loadHabit(habitModel: HabitModel?) {

        if (habitModel != null) {
            with(habitModel) {
                intentColor = color
                binding.name.setText(name)
                binding.description.setText(description)
                binding.countRepeats.setText(countRepeats.toString())
                binding.interval.setText(interval.toString())
                binding.selectedColor.setCardBackgroundColor(color)
                firstSelectedType = type
                binding.prioritySpinner.setSelection(priority.ordinal)
                (binding.types.getChildAt(type.ordinal) as RadioButton).isChecked = true
            }
        } else {
            (binding.types.getChildAt(HabitType.Good.ordinal) as RadioButton).isChecked = true
        }
    }

    private fun onSaveButtonClicked() {
        val emptyEditText = getEmptyEditText()
        val incorrectNumber = getIncorrectNumber()

        when {
            emptyEditText != null -> showToast("$emptyEditText is empty")
            incorrectNumber != null -> showToast("$incorrectNumber is incorrect")
            else -> saveHabit()
        }
    }

    private fun getEmptyEditText(): CharSequence? {
        return when (true) {
            isEmptyText(binding.name.text) -> binding.name.hint
            isEmptyText(binding.description.text) -> binding.description.hint
            isEmptyText(binding.countRepeats.text) -> binding.countRepeats.hint
            isEmptyText(binding.interval.text) -> binding.interval.hint
            else -> null
        }
    }

    private fun getIncorrectNumber(): CharSequence? {
        return when (true) {
            !isValidNumber(binding.countRepeats.text) -> binding.countRepeats.hint
            !isValidNumber(binding.interval.text) -> binding.interval.hint
            else -> null
        }
    }

    private fun isValidNumber(text: Editable?): Boolean {
        return text != null && text.isNotEmpty() && text[0] != '0'
    }

    private fun isEmptyText(text: Editable?): Boolean {
        return text?.trim()?.isEmpty() ?: true
    }

    private fun showToast(name: String) {
        Toast.makeText(requireContext(), name, Toast.LENGTH_SHORT).show()
    }

    private fun saveHabit() {
        if (colorWorker == null) {
            showToast("Color error")
            return
        }

        val habitModel = HabitModel(
            id = position,
            name = binding.name.text.toString(),
            description = binding.description.text.toString(),
            color = colorWorker?.getSelectedColor() ?: Color.WHITE,
            countRepeats = binding.countRepeats.text.toString().toInt(),
            interval = binding.interval.text.toString().toInt(),
            priority = HabitPriority.valueOf(binding.prioritySpinner.selectedItem.toString()),
            type = getSelectedType(),
        )

        sendFragmentResult(habitModel)
    }

    private fun sendFragmentResult(habitModel: HabitModel?) {
        val selectedType = getSelectedType()
        val keyResult1 = selectedType
        val operation1: HabitOperation
        var operation2: HabitOperation? = null
        val keyResult2 = firstSelectedType


        when (firstSelectedType) {
            null -> operation1 = HabitOperation.Add
            selectedType -> operation1 = HabitOperation.Change
            else -> {
                operation1 = HabitOperation.Add
                operation2 = HabitOperation.Delete
            }
        }

        val bundle = bundleOf(
            KEY_HABIT to habitModel,
            KEY_HABIT_OPERATION to operation1,
            KEY_POSITION to position
        )

        if (operation2 != null && keyResult2 != null) {
            val bundle2 = bundleOf(
                KEY_HABIT to habitModel,
                KEY_HABIT_OPERATION to operation2,
                KEY_POSITION to position
            )
            parentFragmentManager.setFragmentResult(keyResult2.name, bundle2)
        }

        parentFragmentManager.setFragmentResult(keyResult1.name, bundle)
        findNavController().popBackStack()
    }

    private fun getSelectedType(): HabitType {
        return when (binding.types.checkedRadioButtonId) {
            R.id.good_type -> HabitType.Good
            else -> HabitType.Bad
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}