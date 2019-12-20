package ru.leksiinesm.player.ui.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.ImageView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat.create
import com.example.lib_player_ui.R
import ru.leksiinesm.core.logger.Logger
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
) {

    private var isPlaying = false

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

    private var iconPlayToStop = initPlayToStopAnim()
    private var iconStopToPlay = initStopToPlayAnim()

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.PlayButton,
            0, 0
        ).apply {
            try {
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
        loadingPaint1.strokeCap = Paint.Cap.ROUND

        loadingPaint2.style = Paint.Style.STROKE
        loadingPaint2.color = loadingColor2
        loadingPaint2.strokeWidth = loadingWidth
        loadingPaint2.strokeCap = Paint.Cap.ROUND

        loadingAnimator.duration = loadingDuration
        loadingAnimator.repeatCount = ValueAnimator.INFINITE
        loadingAnimator.addUpdateListener {
            progress = it.animatedValue as Float
            invalidate()
        }
    }

    override fun performClick(): Boolean {
        if (isPlaying) {
            iconStopToPlay.stop()
            iconStopToPlay = initStopToPlayAnim()
            setImageDrawable(iconStopToPlay)
            iconStopToPlay.start()
        } else {
            iconPlayToStop.stop()
            iconPlayToStop = initPlayToStopAnim()
            setImageDrawable(iconPlayToStop)
            iconPlayToStop.start()
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

    fun setPlaying(playing: Boolean) {
        Logger.d("PlayerButton", "setPlaying: $playing")
        isPlaying = playing
        if (isPlaying) {
            if (!iconPlayToStop.isRunning) {
                Logger.d("PlayerButton", "1")
                iconStopToPlay = initStopToPlayAnim()
                setImageDrawable(iconStopToPlay)
            }
        } else {
            if (!iconStopToPlay.isRunning) {
                Logger.d("PlayerButton", "2")
                iconPlayToStop = initPlayToStopAnim()
                setImageDrawable(iconPlayToStop)
            }
        }
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

    private fun initPlayToStopAnim() = create(context, R.drawable.play_to_stop_anim)!!

    private fun initStopToPlayAnim() = create(context, R.drawable.stop_to_play_anim)!!

    companion object {
        const val DELTA_ANGLE = 180f
        const val SWEEP_ANGLE = 100f

        const val START_PROGRESS_ANGLE = 0f
        const val STOP_PROGRESS_ANGLE = 360f

        const val DEFAULT_PROGRESS_DURATION = 2000
        const val DEFAULT_PROGRESS_WIDTH = 30f
    }
}