package com.mateuszbartos.theguard.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.aakira.playermanager.DataSourceCreator
import com.github.aakira.playermanager.ExoPlayerManager
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import com.google.android.exoplayer2.util.Util
import com.mateuszbartos.theguard.R

class PlayerActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context, url: String): Intent {

            return Intent(context, PlayerActivity::class.java)
                    .putExtra("url", url)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_activity)


        val receivedIntent = intent
        val url = receivedIntent.getStringExtra("url")


        // inject from xml
        val simpleExoPlayerView: SimpleExoPlayerView = findViewById(R.id.playerView)

        val playerManager: ExoPlayerManager = ExoPlayerManager(this)

        playerManager.injectView(simpleExoPlayerView)

        val dataSource = DataSourceCreator.UrlBuilder(
                url = url,
                userAgent = Util.getUserAgent(this, "UserAgent")
        )
        playerManager.setHlsSource(dataSource.build())
        playerManager.play()
    }
}