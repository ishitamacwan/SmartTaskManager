package com.example.taskmanagerassignment.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import kotlin.math.min
import android.animation.ValueAnimator


class TaskProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // Progress properties
    private var progress = 0f
    private var maxProgress = 100f

    // Appearance properties
     val backgroundPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 40f
        color = Color.LTGRAY
        alpha = 50
    }

     val progressPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 40f
        strokeCap = Paint.Cap.ROUND
        color = Color.BLUE
    }

     val textPaint = Paint().apply {
        isAntiAlias = true
        textAlign = Paint.Align.CENTER
        textSize = 40f
        color = Color.BLACK
        isFakeBoldText = true
    }

     val descriptionPaint = Paint().apply {
        isAntiAlias = true
        textAlign = Paint.Align.CENTER
        textSize = 20f
        color = Color.DKGRAY
    }

    private val oval = RectF()

    // Smooth animation
    fun setProgress(newProgress: Float) {
        val animator = ValueAnimator.ofFloat(progress, newProgress.coerceIn(0f, maxProgress))
        animator.duration = 1000 // Animation duration (1 second)
        animator.addUpdateListener { animation ->
            progress = animation.animatedValue as Float
            invalidate() // Redraw with new progress
        }
        animator.start()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val padding = 20f
        val diameter = min(w, h) - padding * 2
        val centerX = w / 2f
        val centerY = h / 2f

        oval.set(
            centerX - diameter / 2,
            centerY - diameter / 2,
            centerX + diameter / 2,
            centerY + diameter / 2
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw background circle
        canvas.drawOval(oval, backgroundPaint)

        // Calculate sweep angle
        val sweepAngle = (progress / maxProgress) * 360f

        // Draw progress arc
        canvas.drawArc(oval, -90f, sweepAngle, false, progressPaint)

        // Draw percentage text
        val centerX = width / 2f
        val centerY = height / 2f
        val percentageText = "${progress.toInt()}%"

        canvas.drawText(percentageText, centerX, centerY, textPaint)

        // Draw description text
        canvas.drawText("Tasks Completed", centerX, centerY + textPaint.textSize + 10, descriptionPaint)
    }
}
