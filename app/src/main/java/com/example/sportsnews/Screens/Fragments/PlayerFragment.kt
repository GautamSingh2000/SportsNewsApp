package com.mindgeeks.sportsnews.Screens.Fragments

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
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mindgeeks.sportsnews.Adapter.PlayerSearchAdapter
import com.mindgeeks.sportsnews.Model.RequestModels.PlayerSearchRequest
import com.mindgeeks.sportsnews.Model.ResponseModel.PlayerData
import com.mindgeeks.sportsnews.R
import com.mindgeeks.sportsnews.Utils.Constants
import com.mindgeeks.sportsnews.Utils.SessionManager
import com.mindgeeks.sportsnews.ViewModels.MainViewModel
import com.mindgeeks.sportsnews.databinding.FragmentPlayerBinding

class PlayerFragment : Fragment() {
    private lateinit var sessionManager: SessionManager
    private lateinit var viewmodel: MainViewModel
    private lateinit var binding: FragmentPlayerBinding
    private var playerlist: ArrayList<PlayerData> = ArrayList()
    var pages = 1
    var query_val = ""
    var isLoading = false
    private var all = false
    private var batting = false
    private var bawling = false


    lateinit var adapter: PlayerSearchAdapter
    private val SPLASH_TIME_OUT = 2000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPlayerBinding.inflate(layoutInflater)
        activity?.findViewById<TextView>(R.id.title)?.text = "Players"
        viewmodel = MainViewModel(requireContext())
        adapter = PlayerSearchAdapter()
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

        val request = PlayerSearchRequest(
            userId = sessionManager.GetValue(Constants.USER_ID).toString(),
            securityToken = sessionManager.GetValue(Constants.SECURITY_TOKEN).toString(),
            versionCode = sessionManager.GetValue(Constants.VERSION_CODE).toString(),
            versionName = sessionManager.GetValue(Constants.VERSION_NAME).toString(),
            nationality = "IN",
        )

        viewmodel.PlayerSearch(request).observe(viewLifecycleOwner) {
            if (it.status == 200) {
                if (!it.allPlayers.isNullOrEmpty()) {
                    playerlist = it.allPlayers as ArrayList<PlayerData>
                    binding.rv.layoutManager = LinearLayoutManager(requireContext())
                    binding.rv.adapter = adapter
                    adapter.setData(requireContext(), playerlist)
                    binding.playernamseShimmer.stopShimmer()
                    binding.playernamseShimmer.visibility = View.GONE
                    binding.rv.visibility = View.VISIBLE
                } else {
                    binding.rvLl.visibility = View.GONE
                    binding.nodatafound.root.visibility = View.VISIBLE
                }
            } else {
                binding.nodatafound.root.visibility = View.VISIBLE
                binding.playernamseShimmer.stopShimmer()
                binding.playernamseShimmer.visibility = View.GONE
                binding.rvLl.visibility = View.GONE
            }
        }
    }

    private fun SearchPlayer(query: String) {

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
            playerName = query,
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
                viewmodel.PlayerSearch(request).observe(viewLifecycleOwner) {
                    if (it.status == 200) {
                        playerlist.addAll(it.allPlayers as ArrayList<PlayerData>)
                        if (playerlist.size > 0) {
                            adapter.setData(requireContext(), playerlist)
                            binding.playernamseShimmer.stopShimmer()
                            binding.playernamseShimmer.visibility = View.GONE
                            binding.rv.visibility = View.VISIBLE
                            binding.nodatafound.root.visibility = View.GONE
                            binding.preogressIndicator.visibility = View.GONE
                        } else {
                            binding.nodatafound.root.visibility = View.VISIBLE
                            binding.playernamseShimmer.stopShimmer()
                            binding.playernamseShimmer.visibility = View.GONE
                            binding.preogressIndicator.visibility = View.GONE
                            binding.rv.visibility = View.GONE
                        }
                    } else {
                        binding.nodatafound.root.visibility = View.VISIBLE
                        binding.playernamseShimmer.stopShimmer()
                        binding.preogressIndicator.visibility = View.GONE
                        binding.playernamseShimmer.visibility = View.GONE
                        binding.rvLl.visibility = View.GONE
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
        }

        binding.bawlingFilter.setOnClickListener {
            bawling = !bawling
            updateFilterUI(binding.bawlingFilter, binding.bawlingFilterText, bawling)
        }

        binding.maleFilter.setOnClickListener {
            all = !all
            updateFilterUI(binding.maleFilter, binding.maleFilterText, all)
        }

    }

    private fun updateFilterUI(filterButton: CardView, filterText: TextView, isActive: Boolean) {
        if (isActive) {
            filterButton.cardElevation = 0f
            filterButton.setCardBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.dark_gray_blue
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
    }

    private fun showFilter() {
        val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.slide_down)
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
        val animation2: Animation = AnimationUtils.loadAnimation(context, R.anim.hide_slide_down)
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
        val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.slide_up)
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
        val animation2: Animation = AnimationUtils.loadAnimation(context, R.anim.show_slide_up)
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
                    query_val = query
                    SearchPlayer(query_val.trim())
                    if (binding.filterLl.visibility == View.VISIBLE) {
                        hideFilters()
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