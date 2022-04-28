package com.example.habits.ui.editHabit


import android.app.WallpaperManager
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.habits.R
import com.example.habits.databinding.FragmentEditHabitBinding
import com.example.habits.model.HabitModel
import com.example.habits.model.HabitPriority
import com.example.habits.model.HabitType
import com.example.habits.ui.editHabit.views.ColorWorker
import com.example.habits.ui.editHabit.views.OnColorSelectedListener
import com.example.habits.ui.habits.HabitsViewModel
import com.example.habits.ui.habits.KEY_POSITION
import com.example.habits.utils.App
import com.example.habits.utils.HabitsViewModelFactory

class EditHabitFragment : Fragment() {

    private var _binding: FragmentEditHabitBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException(
            "Binding is only valid between onCreateView and onDestroyView."
        )

    private var colorWorker: ColorWorker? = null
    private var position: Int = -1

    private val isNewHabit: Boolean
        get() = position == -1

    private val viewModel: EditHabitViewModel by viewModels{ HabitsViewModelFactory((requireActivity().application as App).repository)}
//    private lateinit var viewModel: EditHabitViewModel

    private var colorListener = object : OnColorSelectedListener {
        override fun onColorSelected(color: Int) {
//            viewModel.setColor(color)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        position = arguments?.getInt(KEY_POSITION, position) ?: position

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
            colorListener
        )

        if (!isNewHabit) {
            viewModel.loadHabit(position)
            viewModel.habit.observe(viewLifecycleOwner) {
                initViews(it)
            }
        }

        binding.buttonSave.setOnClickListener { onSaveButtonClicked() }
    }

    //    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        viewModel.saveStateHabit(habitModel = getHabitModel())
//    }

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

    private fun setNumberView(number: Int): String {
        return if (number == 0) {
            ""
        } else {
            number.toString()
        }
    }

    private fun onSaveButtonClicked() {
        val emptyEditText = getEmptyEditText()
        val incorrectNumber = getIncorrectNumber()

        when {
            emptyEditText != null -> showToast("$emptyEditText is empty")
            incorrectNumber != null -> showToast("$incorrectNumber is incorrect")
            colorWorker == null -> showToast("Color error")
            else -> saveHabit()
        }
    }

    private fun getEmptyEditText(): CharSequence? {
        return when (true) {
            isEmptyText(binding.name.text) -> binding.name.hint
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

    private fun getHabitModel(): HabitModel {

        return HabitModel(
            id = position,
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

    private fun saveHabit() {
        viewModel.saveHabit(habitModel = getHabitModel(), isNewHabit)
        findNavController().popBackStack()
    }

    private fun getSelectedType(): HabitType {
        return when (binding.types.checkedRadioButtonId) {
            R.id.good_type -> HabitType.GOOD
            else -> HabitType.BAD
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}