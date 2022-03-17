package com.example.habits.ui

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.habits.R
import com.example.habits.utils.App
import com.google.android.material.card.MaterialCardView


interface OnColorListener {
    fun onColorClick(position: Int)
}

class ColorAdapter(_onColorListener: OnColorListener) :
    ListAdapter<Int, ColorAdapter.ViewHolder>(ColorDiffCallBack()) {

    private val onColorListener: OnColorListener = _onColorListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val colorView =
            LayoutInflater.from(parent.context).inflate(R.layout.color_view, parent, false)

        return ViewHolder(colorView, onColorListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(view: View, _onColorListener: OnColorListener) :
        RecyclerView.ViewHolder(view) {

        private val onColorListener: OnColorListener = _onColorListener
        private val colorBackground: MaterialCardView = view.findViewById(R.id.color_square)

        init {
            view.setOnClickListener { onClick() }
        }

        fun bind(colorSquare: Int) {
            colorBackground.setCardBackgroundColor(colorSquare)
        }

        private fun onClick() {
            onColorListener.onColorClick(adapterPosition)
        }
    }
}

class ColorDiffCallBack : DiffUtil.ItemCallback<Int>() {
    override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
    }
}