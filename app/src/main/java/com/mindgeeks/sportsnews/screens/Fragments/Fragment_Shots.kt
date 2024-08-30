    package com.mindgeeks.sportsnews.screens.Fragments

    import android.os.Bundle
    import androidx.fragment.app.Fragment
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.TextView
    import android.widget.Toast
    import androidx.compose.foundation.layout.PaddingValues
    import androidx.compose.ui.unit.dp
    import androidx.media3.exoplayer.ExoPlayer
    import com.mindgeeks.sportsnews.Components.customDialog
    import com.mindgeeks.sportsnews.adapters.adapter_Shorts
    import com.mindgeeks.sportsnews.models.OtherModel.video_Item
    import com.mindgeeks.sportsnews.R
    import com.mindgeeks.sportsnews.Utils.Constants
    import com.mindgeeks.sportsnews.Utils.SessionManager
    import com.mindgeeks.sportsnews.databinding.FragmentShotsBinding
    import com.mindgeeks.sportsnews.models.RequestModels.ShortsRequestModel
    import com.mindgeeks.sportsnews.view_models.ViewModel_Main

    class Fragment_Shots : Fragment() {
        private lateinit var player: ExoPlayer
            private lateinit var sessionManager: SessionManager
            private  var viewmodel: ViewModel_Main? = null
        lateinit var binding: FragmentShotsBinding
        private var videoPagerAdapter: adapter_Shorts? = null

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
        ): View? {
            binding = FragmentShotsBinding.inflate(layoutInflater)

            activity?.findViewById<TextView>(R.id.title)?.text = "Shorts"

            viewmodel = ViewModel_Main(requireContext())
           sessionManager = SessionManager(requireContext())
            var request = ShortsRequestModel(
                userId = sessionManager.GetValue(Constants.USER_ID).toString(),
                securityToken = sessionManager.GetValue(Constants.SECURITY_TOKEN).toString(),
                versionCode = sessionManager.GetValue(Constants.VERSION_CODE).toString(),
                versionName = sessionManager.GetValue(Constants.VERSION_NAME).toString(),
                deviceId =  sessionManager.GetValue(Constants.DEVICE_ID).toString(),
                page =  "1"
            )

            viewmodel!!.GEtShorts(request).observe(viewLifecycleOwner){
                if(it.status ==     200)
                {
                    if(it.shorts.size>=1)
                    {
                        videoPagerAdapter = adapter_Shorts(requireContext(), it.shorts)
                        binding.shortRc.adapter = videoPagerAdapter
                    }else{
                        viewmodel = null
                        Toast.makeText(requireContext(),"No Short found!!",Toast.LENGTH_SHORT).show()
                    }
                }else{
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

            return binding.root
        }

        override fun onDestroyView() {
            super.onDestroyView()
            binding.shortRc.adapter = null

        }
    }

