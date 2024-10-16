package com.mindgeeks.sportsnews.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mindgeeks.sportsnews.R
import com.mindgeeks.sportsnews.databinding.SingleDateLayoutBinding
import com.mindgeeks.sportsnews.models.OtherModel.data_Calendar
import java.text.SimpleDateFormat
import java.util.*

class adapter4Calander(
    private val calendarInterface: CalendarInterface,
    private val list: ArrayList<data_Calendar>
) : RecyclerView.Adapter<adapter4Calander.CalendarViewHolder>() {

    var pos: Int = -1

    inner class CalendarViewHolder(
        private val binding: SingleDateLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(calendarData: data_Calendar, position: Int) {
            val calendarDay = binding.calenderDay
            val calendarDate = binding.calenderDate
            val cardView = binding.datecard

            if (pos == position) {
                calendarData.isSelected = true
            }
            if (calendarData.isSelected) {
                pos = -1
                calendarDay.setTextColor(itemView.context.getColor(R.color.white))
                calendarDate.setTextColor(itemView.context.getColor(R.color.white))
                cardView.setCardBackgroundColor(itemView.context.getColor(R.color.peach))
            } else {
                calendarDay.setTextColor(itemView.context.getColor(R.color.red_black))
                calendarDate.setTextColor(itemView.context.getColor(R.color.red_black))
                cardView.setCardBackgroundColor(itemView.context.getColor(R.color.white))
            }

            calendarDate.text = calendarData.CalenderDate
            calendarDay.text = calendarData.CalanderDay

            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1 // +1 because January is 0

// Create a full date string
            val dateString = "$year-$month-${calendarData.CalenderDate}"

// Parse the date from the calendarData
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = dateFormat.parse(dateString)

// Get the current date
            val currentDate = Calendar.getInstance().time

// Check if the date is in the future
            val isFutureDate = date?.after(currentDate) == true
            Log.e("CalanderAdapter","Date is ${calendarData.CalenderDate} and is Future is $isFutureDate")
            if (!isFutureDate) {
                cardView.setOnClickListener {
                    calendarInterface.onSelect(calendarData, adapterPosition, calendarDate)
                }
            } else {
                cardView.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.shimmer_ligtest_gray))
                calendarDate.setTextColor(ContextCompat.getColor(itemView.context, R.color.light_gray))
                calendarDay.setTextColor(ContextCompat.getColor(itemView.context, R.color.light_gray))
                cardView.setOnClickListener {
                  Toast.makeText(itemView.context,"You Have Selected A Future Date!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    interface CalendarInterface {
        fun onSelect(calendarData: data_Calendar, position: Int, day: TextView)
    }

    fun setPosition(pos: Int) {
        this.pos = pos
    }

    fun updateList(list: List<data_Calendar>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapter4Calander.CalendarViewHolder {
        val binding = SingleDateLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: adapter4Calander.CalendarViewHolder, position: Int) {
        holder.bind(list[position], position)
    }
}