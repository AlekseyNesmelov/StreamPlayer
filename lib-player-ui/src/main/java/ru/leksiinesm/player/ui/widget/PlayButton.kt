package ru.leksiinesm.player.ui.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class PlayButton @JvmOverloads constructor(
    context: Context,
    attrsSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(
    context,
    attrsSet,
    defStyleAttr,
    defStyleRes
) {

    private val iconPaint = Paint()
    private val iconPath = Path()

    private val loadingPaint = Paint()
    private val loadingRect = RectF()

    private val animator = ValueAnimator.ofFloat(0f, 360f)
    private var animationStarted = false
    private var progress = 0f
    private var progressWidth = 0f

    init {
        iconPaint.color = android.graphics.Color.RED
        iconPaint.style = Paint.Style.FILL

        loadingPaint.color = android.graphics.Color.GREEN
        loadingPaint.style = Paint.Style.STROKE
        loadingPaint.strokeWidth = 30f

        animator.duration = 5000
        animator.repeatCount = ValueAnimator.INFINITE
        animator.addUpdateListener {
            progress = it.animatedValue as Float
            progressWidth = progress % 60
            invalidate()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val size = min(widthSize, heightSize)
        setMeasuredDimension(size, size);
    }

    override fun onDraw(canvas: Canvas) {
        if (!animationStarted) {
            animator.start()
            animationStarted = true
        }
        canvas.apply {
            loadingRect.left = 15f
            loadingRect.right = width.toFloat() - 15f
            loadingRect.bottom = height.toFloat() - 15f
            loadingRect.top = 15f


            iconPath.lineTo(0F, height.toFloat())
            iconPath.lineTo(width.toFloat(), height / 2f)
            iconPath.lineTo(0F, 0F)

            //drawPath(iconPath, iconPaint)

            drawArc(loadingRect, progress + 0f, progressWidth + 90f, false, loadingPaint)
            drawArc(loadingRect, progress + 180f, progressWidth + 90f, false, loadingPaint)
        }
    }

    companion object {

    }
}