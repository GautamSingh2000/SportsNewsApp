package com.mindgeeks.sportsnews.screens.Fragments

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.mindgeeks.sportsnews.Components.customDialog
import com.mindgeeks.sportsnews.R
import com.mindgeeks.sportsnews.adapters.adapterSearch
import com.mindgeeks.sportsnews.databinding.FragmentSearchBinding
import com.mindgeeks.sportsnews.models.OtherModel.search_Hint
import com.mindgeeks.sportsnews.view_models.ViewModel_Main

class Fragment_Search : Fragment() {
    private var S_query = ""
    private lateinit var binding: FragmentSearchBinding
    private var viewModelMain: ViewModel_Main? = null
    private lateinit var hintlist: MutableList<search_Hint>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        activity?.findViewById<TextView>(R.id.title)?.text = "Search"
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        hintlist = mutableListOf()
        GetSearchHint()
        setupSearchView()
        binding.searchView.isIconified = false

        binding.searchView.post {
            showSoftKeyboard(binding.searchView)
        }

        binding.lastsearchCv.setOnClickListener {
            if(!S_query.equals("")) {
                binding.searchView.setQuery(S_query,true)
                GetResult(S_query.trim())
            }
        }

        binding.searchView.findViewById<View>(androidx.appcompat.R.id.search_close_btn)
            ?.setOnClickListener {
                binding.searchView.setQuery("", false)
                binding.searchView.clearFocus()
                hideKeyboard()
            }

        binding.searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                hideKeyboard()
            } else {
                showSoftKeyboard(binding.searchView)
            }
        }

        return binding.root
    }

    private fun setUpHintRecyclerView() {
        hintlist.shuffle()
        binding.searchHintRc.adapter = adapterSearch(hintlist) { description ->
            Log.d("Clicked", "Description: $description")
            S_query = description
            binding.searchView.setQuery(description.trim(), true)
            GetResult(description.trim())
        }
        binding.searchHintRc.adapter?.notifyDataSetChanged()
    }


    private fun showSoftKeyboard(view: View) {
        binding.searchView.requestFocus()
        // in below code
        // 1.  we are accessing the system Service of taking input then in
        // in 2. line we are opening softKeyboard
        val imgr =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    private fun setupSearchView() {
        binding.searchView.isIconified = false
        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query?.length!! > 0) {
                    if (!query.equals(S_query)) {
                        S_query = query
                        GetResult(S_query)
                    }
                }
                hideKeyboard()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun GetResult(query: String) {
        Log.e("SearchFragment", "GetResult called with query $query")
        if (query.isNotEmpty() && query.isNotBlank()) {
            viewModelMain = ViewModel_Main(requireContext())
            binding.searchLl.visibility = View.GONE
            binding.resultTextView.visibility = View.GONE
            binding.loading.visibility = View.VISIBLE
            binding.loadinganim.apply {
                progress = 0f
                Log.e("SearchFragment", "Playing animation")
                playAnimation()
                addAnimatorListener(object : AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {
                        Log.e("SearchFragment", "Animation started")
                    }

                    override fun onAnimationEnd(animation: Animator) {
                        Log.e("SearchFragment", "Animation ended")
                        playAnimation() // Restart the animation
                    }

                    override fun onAnimationCancel(animation: Animator) {
                        Log.e("SearchFragment", "Animation cancelled")
                    }

                    override fun onAnimationRepeat(animation: Animator) {
                        Log.e("SearchFragment", "Animation repeated")
                    }
                })
            }

            viewModelMain?.Search(query)?.observe(viewLifecycleOwner) { response ->
                Log.e("SearchFragment", "Response received: $response")
                if (response != null && response.status == 200) {

                        binding.lastsearchText.text = S_query
                        if (!binding.lastsearchCv.isVisible) {
                            binding.lastsearchCv.visibility = View.VISIBLE
                            val show = android.view.animation.AnimationUtils.loadAnimation(
                                requireContext(),
                                R.anim.slide_down_anim
                            )
                            binding.lastsearchCv.animation = show
                        }
//                        else {
//                            val hide = android.view.animation.AnimationUtils.loadAnimation(
//                                requireContext(),
//                                R.anim.slide_up_anim
//                            )
//                            binding.lastsearchCv.animation = hide
//                            hide.setAnimationListener(object : AnimationListener {
//                                override fun onAnimationStart(animation: Animation?) {
//
//                                }
//
//                                override fun onAnimationEnd(animation: Animation?) {
//                                    binding.lastsearchCv.visibility = View.GONE
//                                }
//
//                                override fun onAnimationRepeat(animation: Animation?) {
//
//                                }
//
//                            })
//                    }

                    displayResult(response.answer.ans[0])
                    viewModelMain = null
                } else {

                    val padding = PaddingValues(
                        start = 50.dp,
                        top = 55.dp,
                        end = 50.dp,
                        bottom = 10.dp
                    )

                    val NoDataFoundDialog = customDialog(
                        context = requireContext(),
                        Title1 = "Something Went Wrong !",
                        Title2 = "Try Again Later",
                        cancelable = false,
                        Error = " Error : ${response.message}",
                        animationID = R.raw.something_went_wrong,
                        padding = padding,
                        repeat = false
                    )

                    binding.loading.visibility = View.GONE
                    binding.searchLl.visibility = View.VISIBLE
                    Toast.makeText(
                        context,
                        "Error: ${response?.message ?: "Unknown error"}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            Toast.makeText(context, "Please enter a valid value!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayResult(result: String) {
        val cleanAnswer = result.replace(Regex("[^a-zA-Z0-9 ]"), "").toString()
        binding.loading.visibility = View.GONE
        binding.searchHintRc.visibility = View.GONE
        binding.resultTextView.visibility = View.VISIBLE
        binding.resultTextView.text = cleanAnswer
        binding.searchLl.visibility = View.VISIBLE
    }

    private fun GetSearchHint() {
        hintlist.add(
            search_Hint(
                title = "Test your cricket knowledge",
                description = "How many players are present in one team?"
            )
        )

        hintlist.add(
            search_Hint(
                title = "IPL Facts",
                description = "Which team has won the most IPL titles?"
            )
        )
        hintlist.add(
            search_Hint(
                title = "Cricket Formats",
                description = "What are the three formats of cricket?"
            )
        )
        hintlist.add(
            search_Hint(
                title = "Historic Matches",
                description = "Which match is considered the greatest ODI match ever?"
            )
        )
        hintlist.add(
            search_Hint(
                title = "Cricket Terminology",
                description = "What does 'duck' mean in cricket?"
            )
        )
        hintlist.add(
            search_Hint(
                title = "All-rounders",
                description = "Who is the best all-rounder in cricket history?"
            )
        )
        hintlist.add(
            search_Hint(
                title = "Cricket Grounds",
                description = "What is the largest cricket stadium in the world?"
            )
        )
        hintlist.add(
            search_Hint(
                title = "Famous Rivalries",
                description = "Which rivalry is known as the Ashes?"
            )
        )
        hintlist.add(
            search_Hint(
                title = "Fastest Centuries",
                description = "Who scored the fastest century in ODI cricket?"
            )
        )

        hintlist.add(
            search_Hint(
                title = "Famous Umpires",
                description = "Who is considered the best cricket umpire of all time?"
            )
        )

        hintlist.add(
            search_Hint(
                title = "Cricket Leagues",
                description = "What is the Caribbean Premier League?"
            )
        )

        hintlist.add(
            search_Hint(
                title = "Iconic Cricketers",
                description = "Who is known as the 'Little Master' in cricket?"
            )
        )

        hintlist.add(
            search_Hint(
                title = "Historic Venues",
                description = "Where is the Lord's Cricket Ground located?"
            )
        )

        hintlist.add(
            search_Hint(
                title = "Memorable Innings",
                description = "Which cricketer scored 400 runs in a single Test innings?"
            )
        )

        hintlist.add(
            search_Hint(
                title = "Women's Cricket",
                description = "Who is the highest run-scorer in women's ODI cricket?"
            )
        )

        hintlist.add(
            search_Hint(
                title = "Legendary Captains",
                description = "Who captained Australia to three consecutive World Cup victories?"
            )
        )

        hintlist.add(
            search_Hint(
                title = "Cricket Innovations",
                description = "What is the 'Super Over' in cricket?"
            )
        )

        hintlist.add(
            search_Hint(
                title = "Unbeaten Records",
                description = "Which cricketer has the record for the highest individual score in ODIs?"
            )
        )

        hintlist.add(
            search_Hint(
                title = "Unique Deliveries",
                description = "What is a 'googly' in cricket?"
            )
        )

        hintlist.add(
            search_Hint(
                title = "Top Run-Scorers",
                description = "Who has the most runs in T20 International cricket?"
            )
        )

        hintlist.add(
            search_Hint(
                title = "Retired Legends",
                description = "Which Indian cricketer is known as the 'God of Cricket'?"
            )
        )

        hintlist.add(
            search_Hint(
                title = "Cricket Formats",
                description = "What is Test cricket?"
            )
        )

        hintlist.add(
            search_Hint(
                title = "Notable Tournaments",
                description = "What is the ICC Champions Trophy?"
            )
        )

        hintlist.add(
            search_Hint(
                title = "Fielding Greats",
                description = "Who is considered the best fielder in cricket history?"
            )
        )

        hintlist.add(
            search_Hint(
                title = "Cricket Milestones",
                description = "Who was the first cricketer to score 10,000 runs in Test cricket?"
            )
        )

        hintlist.add(
            search_Hint(
                title = "Historic Matches",
                description = "What was the result of the 2005 Ashes series?"
            )
        )

        hintlist.add(
            search_Hint(
                title = "Bowling Techniques",
                description = "What is a 'yorker' in cricket?"
            )
        )

        hintlist.add(
            search_Hint(
                title = "Cricket World Records",
                description = "Who holds the record for the fastest fifty in T20 Internationals?"
            )
        )

        setUpHintRecyclerView()
    }

    private fun hideKeyboard() {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onDetach() {
        super.onDetach()
        hideKeyboard()
    }

    override fun onDestroy() {
        super.onDestroy()
        hideKeyboard()
    }
}

