package com.example.habits.habits

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entities.HabitModel
import com.example.domain.entities.HabitPriority
import com.example.domain.entities.HabitType
import com.example.habits.R
import com.example.habits.utils.ResourceUtils.Companion.getResourcePriority
import com.example.habits.utils.ResourceUtils.Companion.getResourceType
import com.example.habits.databinding.WidgetHabitItemBinding
import com.example.domain.getSecondsTime


interface OnHabitListener {
    fun onHabitClick(id: String)
}

interface OnDoneListener {
    fun onDoneClick(id: String, time: Int)
}

class HabitAdapter(
    private val onHabitListener: OnHabitListener,
    private val onDoneListener: OnDoneListener
) :
    ListAdapter<HabitModel, HabitAdapter.ViewHolder>(
        AsyncDifferConfig.Builder(HabitDiffCallBack()).build()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val habitBinding =
            WidgetHabitItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(habitBinding, onHabitListener, onDoneListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: WidgetHabitItemBinding, private val onHabitListener: OnHabitListener,
        private val onDoneListener: OnDoneListener,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(habitModel: HabitModel) {

            with(habitModel) {
                binding.color.setBackgroundColor(color)
                binding.habitDescription.text = description
                binding.habitName.text = name
                binding.habitPriority.text = itemView.context.getText(getResourcePriority(priority))
                binding.habitTextType.text = itemView.context.getString(getResourceType(type))
                binding.interval.text = interval.toString()
                binding.repeatsCount.text = countRepeats.toString()
                binding.repeats.text = doneDates.count().toString()

                val colorPriority = when (priority) {
                    HabitPriority.HIGH -> itemView.context.getColor(R.color.bittersweet)
                    HabitPriority.MEDIUM -> itemView.context.getColor(R.color.polo_blue)
                    HabitPriority.LOW -> itemView.context.getColor(R.color.dusty_grey)
                }
                binding.habitPriority.setTextColor(colorPriority)


                if (interval == 1) {
                    binding.hours.text = itemView.context.getString(R.string.hour)
                }

                if (type == HabitType.GOOD) {
                    binding.cardType.setCardBackgroundColor(itemView.context.getColor(R.color.green_edge_water))
                } else {
                    binding.cardType.setCardBackgroundColor(itemView.context.getColor(R.color.pink_cavern))
                }

                binding.doneButton.setOnClickListener {
                    onDoneListener.onDoneClick(
                        id,
                        time = getSecondsTime()
                    )
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

