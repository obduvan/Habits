package com.example.habits

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import kotlin.random.Random

class GradientView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    lateinit var gradientView: GradientDrawable
    private val paint = Paint()
    private val colors = intArrayOf(
        Color.HSVToColor(floatArrayOf(0f, 1f, 1f)),
        Color.HSVToColor(floatArrayOf(60f, 1f, 1f)),
        Color.HSVToColor(floatArrayOf(120f, 1f, 1f)),
        Color.HSVToColor(floatArrayOf(180f, 1f, 1f)),
        Color.HSVToColor(floatArrayOf(240f, 1f, 1f)),
        Color.HSVToColor(floatArrayOf(300f, 1f, 1f)),
        Color.HSVToColor(floatArrayOf(360f, 1f, 1f))
    )

    init {
        gradientView = GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        background = gradientView
    }
}

class Hs(context: Context, attrs: AttributeSet) : HorizontalScrollView(context, attrs) {
    init {
        val linearLayout = LinearLayout(context, attrs)

        (1..16).forEach {
            val view = View(context, attrs)
            view.setBackgroundColor(resources.getColor(R.color.black))
            linearLayout.addView(view,  LayoutParams(50, 50))
            Log.e("aaaa","!" )
        }



        addView(
            linearLayout, LayoutParams(0,0)

        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

    }

}