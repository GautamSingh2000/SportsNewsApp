    package com.mindgeeks.sportsnews.screens.Fragments

    import android.os.Bundle
    import androidx.fragment.app.Fragment
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.TextView
    import androidx.media3.exoplayer.ExoPlayer
    import com.mindgeeks.sportsnews.adapters.adapter_Shorts
    import com.mindgeeks.sportsnews.models.OtherModel.video_Item
    import com.mindgeeks.sportsnews.R
    import com.mindgeeks.sportsnews.databinding.FragmentShotsBinding

    class Fragment_Shots : Fragment() {
        private lateinit var player: ExoPlayer

        lateinit var binding: FragmentShotsBinding
        private var videoPagerAdapter: adapter_Shorts? = null

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
        ): View? {
            binding = FragmentShotsBinding.inflate(layoutInflater)

            activity?.findViewById<TextView>(R.id.title)?.text = "Shorts"
            val videoList = listOf(
                video_Item("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                video_Item("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                video_Item("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                video_Item("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                video_Item("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                video_Item("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                video_Item("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                video_Item("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                video_Item("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                video_Item("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                video_Item("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                video_Item("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                video_Item("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                video_Item("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                video_Item("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                video_Item("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                video_Item("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                video_Item("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                video_Item("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                video_Item("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                video_Item("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                video_Item("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                video_Item("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                video_Item("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                video_Item("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                video_Item("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                video_Item("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                video_Item("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                video_Item("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h")
            )

            videoPagerAdapter = adapter_Shorts(requireContext(), videoList)
            binding.shortRc.adapter = videoPagerAdapter

            return binding.root
        }

//        override fun onPause() {
//            super.onPause()
//            val currentPosition = binding.shortRc.currentItem
//            val fragment = (binding.shortRc.adapter as FragmentStateAdapter).createFragment(currentPosition)
//            if (fragment is ShotsFragment) {
//                fragment.pausePlayer()
//            }
//        }

        override fun onDestroyView() {
            super.onDestroyView()
            binding.shortRc.adapter = null

        }
    }

