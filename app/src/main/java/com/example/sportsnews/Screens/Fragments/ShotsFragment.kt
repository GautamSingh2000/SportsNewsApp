    package com.mindgeeks.sportsnews.Screens.Fragments

    import android.os.Bundle
    import androidx.fragment.app.Fragment
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.TextView
    import androidx.media3.exoplayer.ExoPlayer
    import com.mindgeeks.sportsnews.Adapter.VideoAdapter
    import com.mindgeeks.sportsnews.Model.OtherModel.VideoItem
    import com.mindgeeks.sportsnews.R
    import com.mindgeeks.sportsnews.databinding.FragmentShotsBinding

    class ShotsFragment : Fragment() {
        private lateinit var player: ExoPlayer

        lateinit var binding: FragmentShotsBinding
        private var videoPagerAdapter: VideoAdapter? = null

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
        ): View? {
            binding = FragmentShotsBinding.inflate(layoutInflater)

            activity?.findViewById<TextView>(R.id.title)?.text = "Shorts"
            val videoList = listOf(
                VideoItem("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                VideoItem("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                VideoItem("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                VideoItem("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                VideoItem("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                VideoItem("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                VideoItem("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                VideoItem("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                VideoItem("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                VideoItem("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                VideoItem("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                VideoItem("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                VideoItem("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                VideoItem("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                VideoItem("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                VideoItem("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                VideoItem("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                VideoItem("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                VideoItem("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                VideoItem("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                VideoItem("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                VideoItem("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                VideoItem("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                VideoItem("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                VideoItem("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                VideoItem("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                VideoItem("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                VideoItem("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h"),
                VideoItem("https://mindgeeksind.blr1.digitaloceanspaces.com/rrta58k0b762jqw6t8wocv7nfo7h")
            )

            videoPagerAdapter = VideoAdapter(requireContext(), videoList)
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

