package com.example.habits.ui.views

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habits.ui.ColorAdapter
import com.example.habits.ui.OnColorListener

class ColorWorker(
    recyclerView: RecyclerView,
    backgroundView: View,
    private val selectedView: View,
    private val rgbText: TextView,
    private val hsvTextView: TextView,
    intentColor: Int? = null

) :
    OnColorListener {

    private val colorAdapter: ColorAdapter = ColorAdapter(this)
    private var colors: List<Int> = (0..15).map {
        Color.HSVToColor(floatArrayOf(360f / 16f * (it + 1) - 360f / 16f / 2f, 1f, 1f))

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
            .map { floatArrayOf(it, 1f, 1f) }
            .map { Color.HSVToColor(it) }
            .toIntArray()

        return GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors)
    }

    override fun onColorClick(position: Int) {
        selectedColor = colors[position]

        setColorInfo(selectedColor)
    }

    private fun setColorInfo(color: Int) {
        selectedView.background = ColorDrawable(color)
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