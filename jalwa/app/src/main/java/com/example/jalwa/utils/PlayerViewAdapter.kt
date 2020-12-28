package com.example.jalwa.utils

import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.ui.PlayerView
import com.squareup.picasso.Picasso

class PlayerViewAdapter {

    companion object{
        private var playersMap: MutableMap<Int, SimpleExoPlayer>  = mutableMapOf()
        private var currentPlayingVideo: Pair<Int, SimpleExoPlayer>? = null
        fun releaseAllPlayers(){
            playersMap.map {
                it.value.release()
            }
        }

        fun releaseRecycledPlayers(index: Int){
            playersMap[index]?.release()
        }

        fun pauseCurrentPlayingVideo(){
            if (currentPlayingVideo != null){
                currentPlayingVideo?.second?.playWhenReady = false
            }
        }

        fun playCurrentPlayingVideo(){
            if (currentPlayingVideo != null){
                currentPlayingVideo?.second?.playWhenReady = true
            }
        }

        fun playIndexThenPausePreviousPlayer(index: Int){
            if (playersMap[index]?.playWhenReady == false) {
                pauseCurrentPlayingVideo()
                playersMap[index]?.playWhenReady = true
                currentPlayingVideo = Pair(index, playersMap[index]!!)
            }
        }

        @JvmStatic
        @BindingAdapter("photoUrl")
        fun loadImage(imageView: ImageView, photoUrl: String) {
            if (photoUrl != "") {
                Picasso.with(imageView.context).load(photoUrl).resize(70, 100).into(imageView)
            }
        }

        @JvmStatic
        @BindingAdapter("video_url", "on_state_change", "index")
        fun PlayerView.loadVideo(url: String, callback: ExoPlayerStateCallback, index: Int) {
            if (url == null) return
            val player = SimpleExoPlayer.Builder(context).build()

            // When changing track, retain the latest frame instead of showing a black screen
            setKeepContentOnPlayerReset(true)
            // We'll show the controller
            this.useController = true
            // Provide url to load the video from here
            val mediaItem: MediaItem = MediaItem.fromUri(url)
            player.setMediaItem(mediaItem)
            player.prepare()

            this.player = player
            if (playersMap.containsKey(index))
                playersMap.remove(index)
            if (index != null)
                playersMap[index] = player

            player.addListener(object : Player.EventListener {
                override fun onPlayerError(error: ExoPlaybackException) {
                    if (error != null) {
                        super.onPlayerError(error)
                    }
                    Toast.makeText(
                            this@loadVideo.context,
                            "Oops! Error occurred while playing media.",
                            Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onPlaybackStateChanged(state: Int) {
                    super.onPlaybackStateChanged(state)

                    if (state == Player.STATE_BUFFERING) callback.onVideoBuffering(player) // Buffering.. set progress bar visible here
                    if (state == Player.STATE_ENDED) callback.onFinishedPlaying(player)
                    if (state == Player.STATE_READY) {
                        // [PlayerView] has fetched the video duration so this is the block to hide the buffering progress bar
                        callback.onVideoDurationRetrieved((this@loadVideo.player as SimpleExoPlayer).duration, player)
                    }
                    if (state == Player.STATE_READY && player.playWhenReady) {
                        // [PlayerView] has started playing/resumed the video
                        callback.onStartedPlaying(player)
                    }
                }
            })
        }
    }
}