package com.example.customview.custom_views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.customview.utils.Colors
import com.example.customview.utils.SelectedColors
import com.example.customview.custom_views.RainbowCircleView.Companion.CENTER_FACTOR

class DrawTextView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val widths: FloatArray
    private val width: Float

    var text = ""
    private var color = Color.GRAY
    private val fontPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        fontPaint.textSize = FONT_SIZE
        fontPaint.style = Paint.Style.FILL
        fontPaint.isFakeBoldText = true

        /* вычисляем ширину текста */
        width = fontPaint.measureText(text)

        /* вычисляем ширину каждого символа */
        widths = FloatArray(text.length)
        fontPaint.getTextWidths(text, widths)
    }

    override fun onDraw(canvas: Canvas) {
        val centerX = width / CENTER_FACTOR
        val textHeight = fontPaint.descent() - fontPaint.ascent()

        fontPaint.color = color

        /* рисуем текст */
        canvas.drawText(text, centerX, textHeight, fontPaint)
    }

    fun setColor(selectedColor: SelectedColors) {
        color =  when(selectedColor) {
            SelectedColors.RED -> Color.RED
            SelectedColors.ORANGE -> Color.parseColor(Colors.ORANGE_COLOR)
            SelectedColors.YELLOW -> Color.parseColor(Colors.YELLOW_COLOR)
            SelectedColors.GREEN -> Color.GREEN
            SelectedColors.CYAN -> Color.CYAN
            SelectedColors.BLUE -> Color.BLUE
            SelectedColors.PURPLE -> Color.parseColor(Colors.PURPLE_COLOR)
        }
    }

    companion object {
        private const val FONT_SIZE = 100f
    }
}
