package com.example.customview.custom_views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.customview.utils.SelectedColors

class RainbowCircleView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint = Paint()

    private val colors = SelectedColors.entries.map { it.color }

    init {
        paint.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        val centerX = width / CENTER_FACTOR
        val centerY = height / CENTER_FACTOR
        val radius = minOf(centerX, centerY)
        val sweepAngle = FULL_ANGLE / colors.size

        for (i in colors.indices) {
            paint.color = colors[i]
            canvas.drawArc(
                centerX - radius, centerY - radius, centerX + radius, centerY + radius,
                i * sweepAngle, sweepAngle, USE_CENTER, paint
            )
        }
        super.onDraw(canvas)
    }

    fun getColor() = SelectedColors.entries.random()

    companion object {
        const val CENTER_FACTOR = 2f
        private const val FULL_ANGLE = 360f
        private const val USE_CENTER = true
    }
}
