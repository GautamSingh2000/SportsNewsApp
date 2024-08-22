package com.mindgeeks.sportsnews.screens.Fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindgeeks.sportsnews.Components.customDialog
import com.mindgeeks.sportsnews.adapters.adapter_PlayerSearch
import com.mindgeeks.sportsnews.models.RequestModels.PlayerSearchRequest
import com.mindgeeks.sportsnews.models.ResponseModel.PlayerData
import com.mindgeeks.sportsnews.R
import com.mindgeeks.sportsnews.Utils.Constants
import com.mindgeeks.sportsnews.Utils.SessionManager
import com.mindgeeks.sportsnews.view_models.ViewModel_Main
import com.mindgeeks.sportsnews.databinding.FragmentPlayerBinding

class Fragment_Player : Fragment() {
    private lateinit var sessionManager: SessionManager
    private  var viewmodel: ViewModel_Main? = null
    private lateinit var binding: FragmentPlayerBinding
    private var playerlist: ArrayList<PlayerData> = ArrayList()
    var pages = 1
    var isLoading = false
    private var all = false
    private var batting = false
    private var bawling = false
    var lastSearch = ""


    private var adapter: adapter_PlayerSearch? = adapter_PlayerSearch()
    private val SPLASH_TIME_OUT = 2000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPlayerBinding.inflate(layoutInflater)
        activity?.findViewById<TextView>(R.id.title)?.text = "Players"
        viewmodel = ViewModel_Main(requireContext())
        adapter = adapter_PlayerSearch()
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        sessionManager = SessionManager(requireContext())
        binding.playernamseShimmer.startShimmer()
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        getData()

        binding.searchView.setOnClickListener {
            if (binding.filterLl.visibility == View.VISIBLE) {
                hideFilters()
            } else {
                showFilter()
            }
        }

        binding.searchView.setOnCloseListener {
            hideFilters()
            hideKeyboard()
            false
        }

        binding.searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (binding.filterLl.visibility == View.VISIBLE) {
                    hideFilters()
                    hideKeyboard()
                }
            } else {
                showFilter()
                showSoftKeyboard(binding.searchView)
            }
        }

        binding.searchView.findViewById<View>(androidx.appcompat.R.id.search_close_btn)
            ?.setOnClickListener {
                binding.searchView.setQuery("", false)
                binding.searchView.clearFocus()
                if (binding.filterLl.visibility == View.VISIBLE) {
                    hideFilters()
                    hideKeyboard()
                }
            }

        binding.searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_button)
            ?.setOnClickListener {
                showFilter()
            }

        setupFilters()
        return binding.root
    }

    private fun getData() {
        
       //  adapter = null
       viewmodel = ViewModel_Main(requireContext())
        val request = PlayerSearchRequest(
            userId = sessionManager.GetValue(Constants.USER_ID).toString(),
            securityToken = sessionManager.GetValue(Constants.SECURITY_TOKEN).toString(),
            versionCode = sessionManager.GetValue(Constants.VERSION_CODE).toString(),
            versionName = sessionManager.GetValue(Constants.VERSION_NAME).toString(),
            nationality = "IN",
        )

        viewmodel?.PlayerSearch(request)?.observe(viewLifecycleOwner) {
            if (it.status == 200) {
                if (!it.allPlayers.isNullOrEmpty()) {
                    playerlist.clear()
                    playerlist = it.allPlayers as ArrayList<PlayerData>
                    binding.rv.layoutManager = LinearLayoutManager(requireContext())
                    binding.rv.adapter = adapter
                    adapter?.setData(requireContext(), playerlist)
                    adapter?.notifyDataSetChanged()
                    binding.playernamseShimmer.stopShimmer()
                    binding.playernamseShimmer.visibility = View.GONE
                    binding.rv.visibility = View.VISIBLE
                    viewmodel = null
                } else {
                    viewmodel = null
                    binding.rvLl.visibility = View.GONE
                    binding.nodatafound.root.visibility = View.VISIBLE
                }
            } else {
                viewmodel = null
                val padding = PaddingValues(
                    start = 50.dp,
                    top = 55.dp,
                    end = 50.dp,
                    bottom = 10.dp
                )

               val  NoDataFoundDialog = customDialog(
                    context = requireContext(),
                    Title1 = "Something Went Wrong !",
                    Title2 = "Restart This App Or Try Again Later",
                    cancelable = false,
                    Error = " Error :${it.status} + ${it.message }",
                    animationID = R.raw.something_went_wrong,
                    padding = padding,
                    repeat = false
                )
            }
        }
    }

    private fun SearchPlayer() {
       //  adapter = null
        viewmodel = ViewModel_Main(requireContext())
        Log.e("PLayerFragment", "in SearhPLayer method")
        binding.playernamseShimmer.visibility = View.VISIBLE
        binding.playernamseShimmer.startShimmer()
        binding.rv.visibility = View.GONE
        binding.nodatafound.root.visibility = View.GONE

        val request = PlayerSearchRequest(
            page = pages.toString(),
            userId = sessionManager.GetValue(Constants.USER_ID).toString(),
            securityToken = sessionManager.GetValue(Constants.SECURITY_TOKEN).toString(),
            versionCode = sessionManager.GetValue(Constants.VERSION_CODE).toString(),
            versionName = sessionManager.GetValue(Constants.VERSION_NAME).toString(),
            playerName = lastSearch,
            playingRole = when {
                batting -> {
                    "bat"
                }

                bawling -> {
                    "bowl"
                }

                all -> {
                    "all"
                }

                else -> {
                    ""
                }
            },

            )

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(
            Runnable {
                viewmodel?.PlayerSearch(request)?.observe(viewLifecycleOwner) {
                    if (it.status == 200) {
                        playerlist.clear()
                        playerlist.addAll(it.allPlayers as ArrayList<PlayerData>)
                        if (playerlist.size > 0) {
                            adapter?.setData(requireContext(), playerlist)
                            adapter?.notifyDataSetChanged()
                            binding.playernamseShimmer.stopShimmer()
                            binding.playernamseShimmer.visibility = View.GONE
                            binding.rv.visibility = View.VISIBLE
                            binding.nodatafound.root.visibility = View.GONE
                            binding.preogressIndicator.visibility = View.GONE
                            viewmodel = null
                        } else {
                            binding.nodatafound.root.visibility = View.VISIBLE
                            binding.playernamseShimmer.stopShimmer()
                            binding.playernamseShimmer.visibility = View.GONE
                            binding.preogressIndicator.visibility = View.GONE
                            binding.rv.visibility = View.GONE
                            viewmodel = null
                        }
                    } else {
                        viewmodel = null
                        val padding = PaddingValues(
                            start = 50.dp,
                            top = 55.dp,
                            end = 50.dp,
                            bottom = 10.dp
                        )

                        val  NoDataFoundDialog = customDialog(
                            context = requireContext(),
                            Title1 = "Something Went Wrong !",
                            Title2 = "Restart This App Or Try Again Later",
                            cancelable = false,
                            Error = " Error :${it.status} + ${it.message }",
                            animationID = R.raw.something_went_wrong,
                            padding = padding,
                            repeat = false
                        )
                        binding.nodatafound.root.visibility = View.VISIBLE
                        binding.playernamseShimmer.stopShimmer()
                        binding.preogressIndicator.visibility = View.GONE
                        binding.playernamseShimmer.visibility = View.GONE
                        binding.rvLl.visibility = View.GONE
                        viewmodel = null
                    }
                }
            },
            SPLASH_TIME_OUT.toLong()
        )
    }


    private fun setupFilters() {
        binding.battingFilter.setOnClickListener {

            batting = !batting
            updateFilterUI(binding.battingFilter, binding.battingFilterText, batting)
            bawling = false
            updateFilterUI(binding.bawlingFilter, binding.bawlingFilterText, bawling)
            all = false
            updateFilterUI(binding.maleFilter, binding.maleFilterText, all)
            if(!lastSearch.equals(""))
            {

                SearchPlayer()
            }
        }

        binding.bawlingFilter.setOnClickListener {

            bawling = !bawling
            updateFilterUI(binding.bawlingFilter, binding.bawlingFilterText, bawling)
            batting = false
            updateFilterUI(binding.battingFilter, binding.battingFilterText, batting)
            all = false
            updateFilterUI(binding.maleFilter, binding.maleFilterText, all)

            if(!lastSearch.equals(""))
            {
                SearchPlayer()
            }

        }

        binding.maleFilter.setOnClickListener {
            all = !all
            updateFilterUI(binding.maleFilter, binding.maleFilterText, all)
            bawling = false
            updateFilterUI(binding.bawlingFilter, binding.bawlingFilterText, bawling)
            batting = false
            updateFilterUI(binding.battingFilter, binding.battingFilterText, batting)
            if(!lastSearch.equals(""))
            {
                SearchPlayer()
            }
        }
    }

    private fun updateFilterUI(filterButton: CardView, filterText: TextView, isActive: Boolean) {
        if (isActive) {
            filterButton.cardElevation = 0f
            filterButton.setCardBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black_red
                )
            )
            filterText.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        } else {
            filterButton.cardElevation = 5f
            filterButton.setCardBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
            filterText.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        }
        if (!batting && !bawling && !all && !lastSearch.equals("")) {
            hideFilters()
        }
    }

    private fun showFilter() {
        val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.slide_down_anim)
        binding.filterLl.apply {
            visibility = View.VISIBLE
            binding.searchView.requestFocus()
            showSoftKeyboard(binding.searchView)
            startAnimation(animation)
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    binding.filterLl.clearAnimation()
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })
        }
        val animation2: Animation = AnimationUtils.loadAnimation(context, R.anim.hide_slide_down_anim)
        binding.rvLl.let {

            it.startAnimation(animation2)
            it.animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    it.clearAnimation()
                    it.visibility = View.GONE
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })
        }
    }

    private fun hideFilters() {
        val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.slide_up_anim)
        binding.filterLl.apply {
            hideKeyboard()
            startAnimation(animation)
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    visibility = View.GONE
                    binding.filterLl.clearAnimation()

                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })
        }
        showResult()
    }

    private fun setupSearchView() {
        binding.searchView.isIconified = false

        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding.rv.visibility = View.GONE
                    binding.playernamseShimmer.visibility = View.VISIBLE
                    binding.playernamseShimmer.startShimmer()
                    Log.e(tag, "Quesry is $query")
                    lastSearch = query
                    lastSearch = lastSearch.trim()
                    SearchPlayer()
                    if (!batting && !bawling && !all) {
                        hideFilters()
                    }else{
                      showResult()
                    }
                    hideKeyboard()
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Optionally handle text change
                return false
            }
        })
    }

    private fun showResult() {
        val animation2: Animation = AnimationUtils.loadAnimation(context, R.anim.show_slide_up_anim)
        binding.rvLl.let {
            it.visibility = View.VISIBLE
            it.startAnimation(animation2)
            it.animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    it.clearAnimation()
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })
        }
    }

    private fun hideKeyboard() {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
            setupSearchView()
        }
    }
}