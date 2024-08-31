import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.mindgeeks.sportsnews.R
import com.mindgeeks.sportsnews.Utils.Constants
import com.mindgeeks.sportsnews.Utils.SessionManager
import com.mindgeeks.sportsnews.adapters.adapter_Shorts
import com.mindgeeks.sportsnews.databinding.FragmentShotsBinding
import com.mindgeeks.sportsnews.models.OtherModel.video_Item
import com.mindgeeks.sportsnews.models.RequestModels.ShortsRequestModel
import com.mindgeeks.sportsnews.models.ResponseModel.ShortsResponseModel
import com.mindgeeks.sportsnews.view_models.ViewModel_Main
class Fragment_Shots : Fragment() {
    private lateinit var player: ExoPlayer
    private lateinit var sessionManager: SessionManager
    private var viewmodel: ViewModel_Main? = null
    lateinit var binding: FragmentShotsBinding
    private var videoPagerAdapter: adapter_Shorts? = null
    private var pageNumber = 1
    private var isLoading = false
    private var isLastPage = false
    private val shortsList = mutableListOf<ShortsResponseModel.Short>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentShotsBinding.inflate(layoutInflater)
        activity?.findViewById<TextView>(R.id.title)?.text = "Shorts"
        sessionManager = SessionManager(requireContext())

        setupAdapter()
        loadShorts(pageNumber,true)

        // Add scroll listener to load more items
        binding.shortRc.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                // Check if we've reached the last item and are not already loading new items
                if (position == shortsList.size - 1 && !isLoading && !isLastPage) {
                    pageNumber++
                    loadShorts(pageNumber,false)
                }
            }
        })

        binding.refresh.setOnRefreshListener {
            videoPagerAdapter = null
            setupAdapter()
            pageNumber = 1
            loadShorts(pageNumber,true)
        }

        return binding.root
    }

    private fun setupAdapter() {
        videoPagerAdapter = adapter_Shorts(requireContext(), shortsList)
        binding.shortRc.adapter = videoPagerAdapter
    }

    private fun loadShorts(page: Int,refresh :Boolean) {
        viewmodel = null
        viewmodel = ViewModel_Main(requireContext())
        isLoading = true
        val request = ShortsRequestModel(
            userId = sessionManager.GetValue(Constants.USER_ID).toString(),
            securityToken = sessionManager.GetValue(Constants.SECURITY_TOKEN).toString(),
            versionCode = sessionManager.GetValue(Constants.VERSION_CODE).toString(),
            versionName = sessionManager.GetValue(Constants.VERSION_NAME).toString(),
            deviceId = sessionManager.GetValue(Constants.DEVICE_ID).toString(),
            page = page.toString()
        )
        viewmodel!!.GEtShorts(request).observe(viewLifecycleOwner) {
            isLoading = false
            binding.refresh.isRefreshing  = false
            if (it.status == 200) {
                if (it.shorts.isNotEmpty()) {
                    if(refresh)
                    {
                        shortsList.clear()
                        shortsList.addAll(it.shorts)
                    }else {
                        shortsList.addAll(it.shorts)
                    }
                    videoPagerAdapter?.notifyDataSetChanged()
                } else {
                    isLastPage = true
                    Toast.makeText(requireContext(), "No more shorts found!!", Toast.LENGTH_SHORT).show()
                }
            } else {
                isLoading = false
                Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.shortRc.adapter = null
    }
}
