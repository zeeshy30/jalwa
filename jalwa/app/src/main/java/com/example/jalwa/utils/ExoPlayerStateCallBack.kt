package com.example.jalwa.utils

import com.google.android.exoplayer2.Player

interface ExoPlayerStateCallback {
    fun onVideoDurationRetrieved(duration: Long, player: Player)

    fun onVideoBuffering(player: Player)

    fun onStartedPlaying(player: Player)

    fun onFinishedPlaying(player: Player)
}
