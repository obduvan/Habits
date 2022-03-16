package com.example.habits.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.MenuItem
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.habits.R
import com.example.habits.databinding.ActivityEditHabitBinding
import com.example.habits.model.HabitModel
import com.example.habits.model.HabitPriority
import com.example.habits.model.HabitType


class EditHabitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditHabitBinding
//    private var name: TextInputEditText? = null
//    private var description: String? = null
//    private var count: Int? = null
//    private var daysInterval: Int? = null
//    private var habitPriority: HabitPriority? = null
//    private var type: HabitType? = null

    private var position: Int = -1
    private var isNewHabit = position != -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditHabitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = getString(R.string.title_edit_habit)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.buttonSave.setOnClickListener {
            onSaveButtonClicked()
        }

        loadHabit()
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

    private fun onSaveButtonClicked() {
        val emptyEditText = getEmptyEditText()
        val incorrectNumber = getIncorrectNumber()

        when {
            emptyEditText != null -> showToast("$emptyEditText is empty")
            incorrectNumber != null -> showToast("$incorrectNumber is incorrect")
            else -> saveHabit()
        }
    }

    private fun showToast(name: String) {
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show()
    }

    private fun saveHabit() {
        val bt = binding.types.checkedRadioButtonId
        val r = findViewById<RadioButton>(bt).toString()
        Log.e("das", r)
        val habitModel = HabitModel(
            id = position,
            name = binding.name.text.toString(),
            description = binding.description.text.toString(),
            color = Color.RED,
            countRepeats = binding.countRepeats.text.toString().toInt(),
            interval = binding.interval.text.toString().toInt(),
            priority = HabitPriority.valueOf(binding.prioritySpinner.selectedItem.toString()),
            type = getSelectedType(),
        )

        val intent = Intent().apply {
            putExtra(KEY_HABIT, habitModel)
        }
        if (!isNewHabit) {
            intent.putExtra(KEY_POSITION, position)
        }

        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun getSelectedType(): HabitType {
        return when (binding.types.checkedRadioButtonId) {
            R.id.good_type -> HabitType.Good
            else -> HabitType.Bad
        }
    }

    private fun loadHabit() {
        position = intent.getIntExtra(KEY_POSITION, position)
        val habitModel = intent.getParcelableExtra<HabitModel>(KEY_HABIT)

        if (habitModel != null) {
            with(habitModel) {
                binding.name.setText(name)
                binding.description.setText(description)
                binding.countRepeats.setText(countRepeats.toString())
                binding.interval.setText(interval.toString())
                binding.prioritySpinner.setSelection(priority.ordinal)
                (binding.types.getChildAt(type.ordinal) as RadioButton).isChecked = true
            }
        } else {
            (binding.types.getChildAt(HabitType.Good.ordinal) as RadioButton).isChecked = true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}