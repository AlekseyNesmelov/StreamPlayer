package ru.leksiinesm.player.ui.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.ImageView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat.create
import com.example.lib_player_ui.R
import kotlin.math.min

/**
 * Play button widget, that can show a progress bar
 *
 * @author Alexey Nesmelov
 */
class PlayButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ImageView(
    context,
    attrs,
    defStyleAttr,
    defStyleRes
), LoadingListener {

    private var isPlaying = false

    private var showLoadingAutomatically = false
    private var loadingDuration = DEFAULT_PROGRESS_DURATION.toLong()
    private var loadingWidth = DEFAULT_PROGRESS_WIDTH
    private var loadingColor1 = Color.BLACK
    private var loadingColor2 = Color.BLACK

    private val loadingPaint1 = Paint()
    private val loadingPaint2 = Paint()
    private val loadingRect = RectF()
    private val loadingAnimator = ValueAnimator.ofFloat(START_PROGRESS_ANGLE, STOP_PROGRESS_ANGLE)
    private var isLoadingStarted = false
    private var progress = 0f

    private val iconPlayToStop: AnimatedVectorDrawableCompat =
        create(context, R.drawable.play_to_stop_anim)!!
    private val iconStopToPlay: AnimatedVectorDrawableCompat =
        create(context, R.drawable.stop_to_play_anim)!!

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.PlayButton,
            0, 0
        ).apply {
            try {
                showLoadingAutomatically = getBoolean(R.styleable.PlayButton_showLoadingAutomatically, false)
                loadingDuration = getInteger(R.styleable.PlayButton_loadingDuration, DEFAULT_PROGRESS_DURATION).toLong()
                loadingWidth =
                    getDimensionPixelSize(R.styleable.PlayButton_loadingWidth, DEFAULT_PROGRESS_WIDTH.toInt()).toFloat()
                loadingColor1 = getColor(R.styleable.PlayButton_loadingColor1, Color.BLACK)
                loadingColor2 = getColor(R.styleable.PlayButton_loadingColor2, Color.BLACK)
            } finally {
                recycle()
            }
        }
        setImageDrawable(iconPlayToStop)

        loadingPaint1.style = Paint.Style.STROKE
        loadingPaint1.color = loadingColor1
        loadingPaint1.strokeWidth = loadingWidth
        loadingPaint1.isAntiAlias = true

        loadingPaint2.style = Paint.Style.STROKE
        loadingPaint2.color = loadingColor2
        loadingPaint2.strokeWidth = loadingWidth
        loadingPaint2.isAntiAlias = true

        loadingAnimator.duration = loadingDuration
        loadingAnimator.repeatCount = ValueAnimator.INFINITE
        loadingAnimator.addUpdateListener {
            progress = it.animatedValue as Float
            invalidate()
        }
    }

    override fun onLoaded() {
        if (showLoadingAutomatically) {
            hideProgress()
        } else {
            throw IllegalStateException("Button is used as loading listener, but showLoadingAutomatically flag is false")
        }
    }

    override fun performClick(): Boolean {
        if (isPlaying) {
            setImageDrawable(iconStopToPlay)
            iconStopToPlay.start()
            if (showLoadingAutomatically) {
                hideProgress()
            }
        } else {
            setImageDrawable(iconPlayToStop)
            iconPlayToStop.start()
            if (showLoadingAutomatically) {
                showProgress()
            }
        }
        isPlaying = isPlaying.not()
        return super.performClick()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        val centerX = width / 2
        val centerY = height / 2
        val centerDelta = min(width, height) / 2
        val progressDelta = loadingWidth / 2

        loadingRect.left = centerX - centerDelta + progressDelta
        loadingRect.right = centerX + centerDelta - progressDelta
        loadingRect.bottom = centerY + centerDelta - progressDelta
        loadingRect.top = centerY - centerDelta + progressDelta
    }

    override fun onDraw(canvas: Canvas) {
        canvas.apply {
            if (isLoadingStarted) {
                drawArc(loadingRect, progress, SWEEP_ANGLE, false, loadingPaint1)
                drawArc(loadingRect, progress + DELTA_ANGLE, SWEEP_ANGLE, false, loadingPaint2)
            }
            super.onDraw(this)
        }
    }

    fun showPlayIcon() {
        setImageDrawable(iconPlayToStop)
        isPlaying = false
    }

    fun showStopIcon() {
        setImageDrawable(iconStopToPlay)
        isPlaying = true
    }

    /**
     * Start showing progress bar.
     */
    fun showProgress() {
        if (!isLoadingStarted) {
            loadingAnimator.start()
            isLoadingStarted = true
            invalidate()
        }
    }

    /**
     * Stop showing progress bar.
     */
    fun hideProgress() {
        if (isLoadingStarted) {
            loadingAnimator.cancel()
            isLoadingStarted = false
            invalidate()
        }
    }

    /**
     * Should we start a progress bar automatically on starting and hide it in [onLoaded] callback.
     */
    fun showLoadingAutomatically(value: Boolean) {
        showLoadingAutomatically = value
    }

    companion object {
        const val DELTA_ANGLE = 180f
        const val SWEEP_ANGLE = 100f

        const val START_PROGRESS_ANGLE = 0f
        const val STOP_PROGRESS_ANGLE = 360f

        const val DEFAULT_PROGRESS_DURATION = 3000
        const val DEFAULT_PROGRESS_WIDTH = 30f
    }
}