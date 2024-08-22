package com.mindgeeks.sportsnews.Components

import android.content.Context
import android.media.MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START
import android.util.AttributeSet
import android.view.View
import android.widget.VideoView

class CustomVideoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : VideoView(context, attrs, defStyleAttr) {

    private var videoWidth = 0
    private var videoHeight = 0

    init {
        // Disable the default MediaController
        setOnPreparedListener { it.setOnInfoListener { _, what, _ ->
            if (what == MEDIA_INFO_VIDEO_RENDERING_START) {
                hideMediaController()
            }
            true
        }}
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = View.getDefaultSize(videoWidth, widthMeasureSpec)
        var height = View.getDefaultSize(videoHeight, heightMeasureSpec)
        if (videoWidth > 0 && videoHeight > 0) {
            if (videoWidth * height > width * videoHeight) {
                height = width * videoHeight / videoWidth
            } else if (videoWidth * height < width * videoHeight) {
                width = height * videoWidth / videoHeight
            }
        }
        setMeasuredDimension(width, height)
    }

    fun setVideoSize(width: Int, height: Int) {
        videoWidth = width
        videoHeight = height
        requestLayout()
    }

    private fun hideMediaController() {
        setMediaController(null)
    }
}
