package com.mindgeeks.sportsnews.screens.Fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.mindgeeks.sportsnews.adapters.tabLayoutAdapter_Upate
import com.mindgeeks.sportsnews.Components.CalendarListener
import com.mindgeeks.sportsnews.Components.HorizontalCalendar
import com.mindgeeks.sportsnews.models.RequestModels.DateWiseUpdateRequest
import com.mindgeeks.sportsnews.models.ResponseModel.Matche
import com.mindgeeks.sportsnews.models.ResponseModel.New
import com.mindgeeks.sportsnews.R
import com.mindgeeks.sportsnews.Utils.Constants
import com.mindgeeks.sportsnews.Utils.SessionManager
import com.mindgeeks.sportsnews.view_models.ViewModel_Main
import com.mindgeeks.sportsnews.databinding.FragmentUpdateBinding
import com.google.android.material.tabs.TabLayoutMediator
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
class Fragment_Update : Fragment(), CalendarListener {

    lateinit var sessionManager: SessionManager
    private lateinit var binding: FragmentUpdateBinding
    lateinit var viewModel: ViewModel_Main
    val TabTitle = listOf("Matches", "News")
    lateinit var DateWiseMatchUpdate: ArrayList<Matche>
    lateinit var DateWiseNewsUpdate: ArrayList<New>
    val TAG = "UpdateFragment"
    lateinit var adapter : tabLayoutAdapter_Upate

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentUpdateBinding.inflate(inflater, container, false)
        activity?.findViewById<TextView>(R.id.title)?.text = "Latest Updates"
        sessionManager = SessionManager(requireContext())
        DateWiseMatchUpdate = ArrayList()
        DateWiseNewsUpdate = ArrayList()
        val horizontalCalendar = HorizontalCalendar(
            requireContext(),
            layoutInflater,
            childFragmentManager,
            this // Pass the listener
        )
        binding.shimmerUpdatematch.visibility = View.VISIBLE
        binding.shimmerUpdatematch.startShimmer()
        binding.calendarContainer.addView(horizontalCalendar.getView())
        GetData()

        return binding.root
    }

    fun setTablayout(DateWiseMatchUpdate : ArrayList<Matche>,DateWiseNewsUpdate : ArrayList<New>) {
        binding.tabLayout.visibility =View.VISIBLE
        binding.shimmerUpdatematch.stopShimmer()
        binding.shimmerUpdatematch.visibility =View.GONE
        binding.viewPager2.visibility = View.VISIBLE
        binding.viewPager2.adapter = adapter
        adapter.setData(matchList = DateWiseMatchUpdate, news = DateWiseNewsUpdate)
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = TabTitle[position]
        }.attach()

        for (i in 0..1) {
            val text =
                LayoutInflater.from(requireContext()).inflate(R.layout.tab_title, null) as TextView
            binding.tabLayout.getTabAt(i)?.customView = text
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun GetData(selectedDate: LocalDateTime = LocalDateTime.now()) {
        binding.viewPager2.visibility = View.GONE
        viewModel = ViewModel_Main(requireContext())
        adapter =  tabLayoutAdapter_Upate(this)

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val currentdate = selectedDate.format(formatter)

        // Subtract one day
        val previousDateTime = selectedDate.minus(1, ChronoUnit.DAYS)

        // Format previous date as string
        val prevDate = previousDateTime.format(formatter)

        Log.e("UpdateFragment", "$prevDate  $currentdate")

        val request = DateWiseUpdateRequest(
 
 
            userId = sessionManager.GetValue(Constants.USER_ID).toString(),
            securityToken = sessionManager.GetValue(Constants.SECURITY_TOKEN).toString(),
            versionName = sessionManager.GetValue(Constants.VERSION_NAME).toString(),
            versionCode = sessionManager.GetValue(Constants.VERSION_CODE).toString(),
            dateFrom = prevDate.toString(),
            dateTo = currentdate.toString()
        )

        viewModel.DateWiseUpdate(request).observe(viewLifecycleOwner) {
            if (it.status == 200) {
                try {
                    DateWiseMatchUpdate.clear()
                    DateWiseNewsUpdate.clear()
                    DateWiseMatchUpdate.addAll(it.matches as ArrayList<Matche>)
                    Log.e(TAG, "DateWiseMatchUpdate size is ${DateWiseMatchUpdate.size}")
                    DateWiseNewsUpdate.addAll(it.news as ArrayList<New>)
                    Log.e(TAG, "DateWiseNewsUpdate size is ${DateWiseNewsUpdate.size}")
                    setTablayout(DateWiseMatchUpdate,DateWiseNewsUpdate)
                } catch (e: Exception) {
                    Log.e(TAG, "${e.message}")
                }
            }else{
                binding.viewPager2.visibility =View.VISIBLE
                binding.shimmerUpdatematch.visibility =View.GONE
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDateSelected(dateInMillis: Long) {
        val selectedDate = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(dateInMillis),
            ZoneId.systemDefault()
        )

        binding.shimmerUpdatematch.visibility = View.VISIBLE
        binding.shimmerUpdatematch.startShimmer()
        GetData(selectedDate)
    }
}


