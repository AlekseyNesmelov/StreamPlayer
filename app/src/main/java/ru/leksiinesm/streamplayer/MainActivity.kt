package ru.leksiinesm.streamplayer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.leksiinesm.service.MediaPlayerService

class MainActivity : AppCompatActivity() {

    private var serviceConnection = MediaPlayerServiceConnection()

    private var isServiceBound = false
    private var playerService: MediaPlayerService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            if (isServiceBound) {
                triggerPlay()
            } else {
                Intent(this, MediaPlayerService::class.java).also { intent ->
                    startService(intent)
                    bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService()
    }

    private fun triggerPlay() {
        if (playerService!!.isStarted()) {
            playerService?.stop()
            unbindService()
        } else {
            playerService?.start(COMEDY_RADIO_URL)
        }
    }

    private fun unbindService() {
        if (isServiceBound) {
            unbindService(serviceConnection)
            isServiceBound = false
        }
    }

    inner class MediaPlayerServiceConnection : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as MediaPlayerService.LocalBinder
            playerService = binder.getService()
            isServiceBound = true
            triggerPlay()
        }

        override fun onServiceDisconnected(className: ComponentName) {
            isServiceBound = false
        }
    }

    companion object {
        private const val COMEDY_RADIO_URL = "http://ic4.101.ru:8000/a202"
    }
}
