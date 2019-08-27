package ru.leksiinesm.playerlib.focus

import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.os.Build
import androidx.annotation.RequiresApi
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class AudioFocusManagerImpl(private val audioManager: AudioManager) : AudioFocusManager {

    private val _focusChangeObservable = BehaviorSubject.createDefault(AudioFocusState.LOST_FOCUS)
    private val focusChangeListener = FocusListener()
    private var playOnFocus = false

    override fun start() {
        val requestResult = requestFocus()
        if (requestResult == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            _focusChangeObservable.onNext(AudioFocusState.IN_FOCUS)
        } else if (requestFocus() == AudioManager.AUDIOFOCUS_REQUEST_DELAYED) {
            playOnFocus = true
        }
    }

    override fun release() {
        audioManager.abandonAudioFocus { focusChangeListener }
    }

    override val focusChangeObservable: Observable<AudioFocusState>
        get() = _focusChangeObservable.hide()

    private fun requestFocus() : Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            audioManager.requestAudioFocus(getFocusRequest())
        } else {
            audioManager.requestAudioFocus(focusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getFocusRequest() : AudioFocusRequest {
        return AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN).run {
            setAudioAttributes(getAudioAttributes())
            setAcceptsDelayedFocusGain(true)
            setOnAudioFocusChangeListener(focusChangeListener)
            build()
        }
    }

    private fun getAudioAttributes() : AudioAttributes {
        return AudioAttributes.Builder().run {
            setUsage(AudioAttributes.USAGE_MEDIA)
            setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            build()
        }
    }

    inner class FocusListener : AudioManager.OnAudioFocusChangeListener {
        override fun onAudioFocusChange(state: Int) {
            return when(state) {
                AudioManager.AUDIOFOCUS_GAIN -> {
                    if (playOnFocus) {
                        _focusChangeObservable.onNext(AudioFocusState.IN_FOCUS)
                    } else {

                    }
                }
                else -> _focusChangeObservable.onNext(AudioFocusState.LOST_FOCUS)
            }
        }
    }
}