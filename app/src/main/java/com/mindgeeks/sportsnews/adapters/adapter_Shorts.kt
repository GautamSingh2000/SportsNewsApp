package com.mindgeeks.sportsnews.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.RecyclerView
import com.mindgeeks.sportsnews.models.OtherModel.video_Item
import com.mindgeeks.sportsnews.R


class adapter_Shorts(val context: Context, private val videoList: List<video_Item>) : RecyclerView.Adapter<adapter_Shorts.VideoViewHolder>() {

    inner class VideoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val playerView: PlayerView = view.findViewById(R.id.videoView2)
        val muteButton: ImageView = view.findViewById(R.id.volume_btn)
        val shareButton: ImageView = view.findViewById(R.id.share)
        val playbtn: ImageView = view.findViewById(R.id.playbtn)
        var player: ExoPlayer? = null
        var isMuted = false
        var playState = true

        fun bind(videoItem: video_Item) {
            playerView.useController = false
            player = ExoPlayer.Builder(context).build().apply {
                setMediaItem(MediaItem.fromUri(videoItem.videoUrl))
                prepare()
                addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(state: Int) {
                        if (state == Player.STATE_ENDED) {
                            seekTo(0)
                            playWhenReady = true
                        }
                    }
                })
            }
            playerView.player = player
            playerView.setOnClickListener {
                player?.let {
                    if (it.isPlaying) {
                        playbtn.setImageResource(R.drawable.pause)
                        playbtn.visibility = View.VISIBLE
                        playState = true
                        it.pause()
                    } else {
                        playbtn.setImageResource(R.drawable.play)
                        val anim = AnimationUtils.loadAnimation(context,R.anim.fade_out_anim)
                        playbtn.startAnimation(anim)
                        anim.setAnimationListener(object : AnimationListener{
                            override fun onAnimationStart(animation: Animation?) {

                            }
                            override fun onAnimationEnd(animation: Animation?) {
                                playbtn.visibility = View.GONE
                                playState = true
                                it.play()
                            }

                            override fun onAnimationRepeat(animation: Animation?) {
                            }

                        })
                    }
                }
            }

            muteButton.setOnClickListener {
                isMuted =!isMuted
                if (isMuted) {
                    muteButton.setImageResource(R.drawable.volume_mute)
                    player?.volume = 0f
                } else {
                    muteButton.setImageResource(R.drawable.volume_on)
                    player?.volume = 1f
                }
            }

            shareButton.setOnClickListener {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, videoItem.videoUrl)
                }
                context.startActivity(Intent.createChooser(shareIntent, "Share link"))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_shot_layout, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(videoList[position])
    }

    override fun getItemCount(): Int = videoList.size

    override fun onViewAttachedToWindow(holder: VideoViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.player?.prepare()
        holder.playbtn.setImageResource(R.drawable.play)
            holder.player?.play()
    }

    override fun onViewDetachedFromWindow(holder: VideoViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.player?.pause()
        holder.playbtn.setImageResource(R.drawable.play)
    }
}