package com.example.habits.ui

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.habits.model.HabitModel
import com.example.habits.model.HabitPriority
import com.example.habits.model.HabitType
import com.example.habits.R
import com.example.habits.databinding.WidgetHabitItemBinding


interface OnViewListener {
    fun onViewClick(position: Int)
}

class HabitAdapter(_onViewListener: OnViewListener) :
    ListAdapter<HabitModel, HabitAdapter.ViewHolder>(HabitDiffCallBack()) {

    private val onViewListener = _onViewListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val habitBinding =
            WidgetHabitItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(habitBinding, onViewListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class HabitDiffCallBack : DiffUtil.ItemCallback<HabitModel>() {
        override fun areItemsTheSame(oldItem: HabitModel, newItem: HabitModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: HabitModel, newItem: HabitModel): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder(private val binding: WidgetHabitItemBinding, _onViewListener: OnViewListener) :
        RecyclerView.ViewHolder(
            binding.root
        ) {
        init {
            binding.root.setOnClickListener { onClick() }
        }

        private val onViewListener: OnViewListener = _onViewListener

        fun bind(habitModel: HabitModel) {
            with(habitModel) {
                binding.color.setBackgroundColor(color)
                binding.habitDescription.text = description
                binding.habitName.text = name
                binding.habitPriority.text = priority.name
                binding.habitTextType.text = type.name

                val colorPriority = when (priority) {
                    HabitPriority.High -> Color.RED
                    HabitPriority.Medium -> Color.BLUE
                    HabitPriority.Low -> Color.GRAY
                }
                binding.habitPriority.setTextColor(colorPriority)
                if (type == HabitType.Good) {
                    binding.cardType.setCardBackgroundColor(R.color.green_edge_water.toInt())
                } else {
                    binding.cardType.setCardBackgroundColor(R.color.pink_cavern.toInt())
                }
            }
        }

        private fun onClick() {
            onViewListener.onViewClick(adapterPosition)
            Log.e("sdad", adapterPosition.toString())
        }
    }
}
