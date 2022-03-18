package com.example.habits.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.habits.R
import com.example.habits.databinding.WidgetHabitItemBinding
import com.example.habits.model.HabitModel
import com.example.habits.model.HabitPriority
import com.example.habits.model.HabitType


interface OnHabitListener {
    fun onHabitClick(position: Int)
}

class HabitAdapter(private val onViewListener: OnHabitListener) :
    ListAdapter<HabitModel, HabitAdapter.ViewHolder>(HabitDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val habitBinding =
            WidgetHabitItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(habitBinding, onViewListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: WidgetHabitItemBinding, private val onHabitListener: OnHabitListener
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { onClick() }
        }

        fun bind(habitModel: HabitModel) {
            with(habitModel) {
                binding.color.setBackgroundColor(color)
                binding.habitDescription.text = description
                binding.habitName.text = name
                binding.habitPriority.text = priority.name
                binding.habitTextType.text = type.name
                binding.interval.text = interval.toString()

                val colorPriority = when (priority) {
                    HabitPriority.High -> Color.RED
                    HabitPriority.Medium -> Color.BLUE
                    HabitPriority.Low -> Color.GRAY
                }

                binding.habitPriority.setTextColor(colorPriority)

                if (interval == 1) {
                    binding.days.text = itemView.context.getString(R.string.day)
                }

                if (type == HabitType.Good) {
                    binding.cardType.setCardBackgroundColor(itemView.context.getColor(R.color.green_edge_water))
                } else {
                    binding.cardType.setCardBackgroundColor(itemView.context.getColor(R.color.pink_cavern))
                }
            }
        }

        private fun onClick() {
            onHabitListener.onHabitClick(adapterPosition)
        }
    }
}


class HabitDiffCallBack : DiffUtil.ItemCallback<HabitModel>() {
    override fun areItemsTheSame(oldItem: HabitModel, newItem: HabitModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: HabitModel, newItem: HabitModel): Boolean {
        return oldItem == newItem
    }
}
