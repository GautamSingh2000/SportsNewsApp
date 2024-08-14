package com.mindgeeks.sportsnews.Components

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Rect
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.mindgeeks.sportsnews.databinding.HorizontalCalendarBinding
import com.mindgeeks.sportsnews.Adapter.CalendarAdapter
import com.mindgeeks.sportsnews.Model.OtherModel.CalendarData
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class HorizontalCalendar(
    val context: Context,
    val inflater: LayoutInflater,
    val fragmentManager: FragmentManager,
    val calendarListener: CalendarListener // Add this line
) :  CalendarAdapter.CalendarInterface {

    companion object {
        private const val TAG = "HorizontalCalendar"
    }

    private var binding: HorizontalCalendarBinding = HorizontalCalendarBinding.inflate(inflater)
    private val sdf = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    private val cal = Calendar.getInstance(Locale.ENGLISH)
    private var mStartD: Date? = null

    private val calendarAdapter =  CalendarAdapter(
        calendarInterface = this,
        arrayListOf()
    )
    private val calendarList = ArrayList<CalendarData>()

    init {
        Log.e(TAG, "HorizontalCalendar initialized")
        init()
        initCalendar()
    }

    private fun init() {
        binding.apply {
            monthYearPicker.text = sdf.format(cal.time)
            calendarView.setHasFixedSize(true)
            calendarView.adapter = calendarAdapter
            configureViewPager()
            monthYearPicker.setOnClickListener { displayDatePicker() }
        }
        Log.e(TAG, "init: Completed initialization")
    }

    fun configureViewPager()
    {
        binding.calendarView.apply {
            setClipToPadding(false)
            setClipChildren(false)
            overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            addItemDecoration(MarginItemDecoration(40))
            itemAnimator = ScaleItemAnimator()
        }
    }

    fun getView(): View {
        return binding.root
    }


    private fun initCalendar() {
        mStartD = Date()
        cal.time = Date()
        getDates()
    }
    private fun displayDatePicker() {
        val materialDateBuilder: MaterialDatePicker.Builder<Long> = MaterialDatePicker.Builder.datePicker()
        materialDateBuilder.setTitleText("Select Date")
        val materialDatePicker = materialDateBuilder.build()
        materialDatePicker.show(fragmentManager, "MATERIAL_DATE_PICKER")
        materialDatePicker.addOnPositiveButtonClickListener {
            try {
                mStartD = Date(it)
                binding.monthYearPicker.text = sdf.format(it)
                cal.time = Date(it)
                getDates()
                calendarListener.onDateSelected(it) // Notify the listener
            } catch (e: ParseException) {
                Log.e(TAG, "displayDatePicker: ${e.message}")
            }
        }
    }

    private fun getDates() {
        val dateList = ArrayList<CalendarData>()
        val dates = ArrayList<Date>()
        val monthCalendar = cal.clone() as Calendar
        val maxDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)

        while (dates.size < maxDaysInMonth) {
            dates.add(monthCalendar.time)
            dateList.add(CalendarData(monthCalendar.time))
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        calendarList.clear()
        calendarList.addAll(dateList)
        calendarAdapter.updateList(dateList)

        for (item in dateList.indices) {
            if (dateList[item].date == mStartD) {
                calendarAdapter.setPosition(item)
                binding.calendarView.scrollToPosition(item)
            }
        }
    }



    override fun onSelect(calendarData: CalendarData, position: Int, day: TextView) {
        calendarList.forEachIndexed { index, data ->
            data.isSelected = index == position
        }
        calendarAdapter.updateList(calendarList)
        calendarListener.onDateSelected(calendarData.date.time) // Notify the listener
    }
}


class MarginItemDecoration(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.right = spaceHeight
    }
}


class ScaleItemAnimator : DefaultItemAnimator() {
    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder?,
        newHolder: RecyclerView.ViewHolder?,
        fromX: Int,
        fromY: Int,
        toX: Int,
        toY: Int
    ): Boolean {
        val result = super.animateChange(oldHolder, newHolder, fromX, fromY, toX, toY)
        val animator = ValueAnimator.ofFloat(0.85f, 1.0f).apply {
            duration = 2000
            addUpdateListener {
                val value = it.animatedValue as Float
                newHolder?.itemView?.scaleY = value
            }
        }
        animator.start()

        return result
    }
}

interface CalendarListener {
    fun onDateSelected(dateInMillis: Long)
}