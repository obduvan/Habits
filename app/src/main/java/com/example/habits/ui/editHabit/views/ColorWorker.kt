package com.example.habits.ui.editHabit.views

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView


class ColorWorker(
    recyclerView: RecyclerView,
    backgroundView: View,
    private val selectedColorView: MaterialCardView,
    private val rgbText: TextView,
    private val hsvTextView: TextView,
    intentColor: Int? = null,
) :
    OnColorListener {

    companion object {
        private const val countElements = 16
        private const val saturation = 0.9f
        private const val brightness = 1f
    }


    private val colorAdapter: ColorAdapter = ColorAdapter(this)
    private var colors: List<Int> = (0 until countElements).map {
        Color.HSVToColor(floatArrayOf(360f / 16f * (it + 1) - 360f / 16f / 2f, 0.9f, 1f))
    }

    private var selectedColor = intentColor ?: colors[0]

    init {
        backgroundView.background = getGradientBackground()
        setColorInfo(selectedColor)

        with(recyclerView) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = colorAdapter
        }

        colorAdapter.submitList(colors)
    }

    fun getSelectedColor() = selectedColor

    private fun getGradientBackground(): GradientDrawable {
        val colors = (0..360 step 60)
            .map { it.toFloat() }
            .map { floatArrayOf(it, saturation, brightness) }
            .map { Color.HSVToColor(it) }
            .toIntArray()

        return GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors)
    }

    override fun onColorClick(position: Int) {
        selectedColor = colors[position]
        setColorInfo(selectedColor)
    }

    private fun setColorInfo(color: Int) {
        selectedColorView.setCardBackgroundColor(color)
        rgbText.text = getRGBText(color)
        hsvTextView.text = getHSVText(color)
    }

    private fun getHSVText(color: Int): String {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)

        val hue = hsv[0].toInt()
        val sat = (hsv[1] * 100).toInt()
        val value = (hsv[2] * 100).toInt()

        return "HSV: ($hue, $sat, $value)"
    }

    private fun getRGBText(color: Int): String {
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)

        return "RGB: ($red, $green, $blue)"
    }
}