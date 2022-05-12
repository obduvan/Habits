package com.example.habits.ui.habits

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.habits.R
import com.example.habits.databinding.WidgetHabitItemBinding
import com.example.habits.model.HabitModel
import com.example.habits.model.HabitPriority
import com.example.habits.model.HabitType


interface OnHabitListener {
    fun onHabitClick(position: String)
}

class HabitAdapter(private val onHabitListener: OnHabitListener) :
    ListAdapter<HabitModel, HabitAdapter.ViewHolder>(
        AsyncDifferConfig.Builder(HabitDiffCallBack()).build()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val habitBinding =
            WidgetHabitItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(habitBinding, onHabitListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: WidgetHabitItemBinding, private val onHabitListener: OnHabitListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(habitModel: HabitModel) {

            with(habitModel) {
                binding.color.setBackgroundColor(color)
                binding.habitDescription.text = description
                binding.habitName.text = name
                binding.habitPriority.text = itemView.context.getText(priority.resId)
                binding.habitTextType.text = itemView.context.getString(type.resourceId)
                binding.interval.text = interval.toString()

                val colorPriority = when (priority) {
                    HabitPriority.HIGH -> itemView.context.getColor(R.color.bittersweet)
                    HabitPriority.MEDIUM -> itemView.context.getColor(R.color.polo_blue)
                    HabitPriority.LOW -> itemView.context.getColor(R.color.dusty_grey)
                }
                binding.habitPriority.setTextColor(colorPriority)


                if (interval == 1) {
                    binding.days.text = itemView.context.getString(R.string.day)
                }

                if (type == HabitType.GOOD) {
                    binding.cardType.setCardBackgroundColor(itemView.context.getColor(R.color.green_edge_water))
                } else {
                    binding.cardType.setCardBackgroundColor(itemView.context.getColor(R.color.pink_cavern))
                }

                itemView.setOnClickListener { onHabitListener.onHabitClick(id) }
            }
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

