package com.example.customview

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.addListener
import com.example.customview.databinding.ActivityMainBinding
import com.example.customview.utils.Action
import com.example.customview.utils.SelectedColors
import com.google.android.material.slider.Slider
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() {

    private lateinit var color: SelectedColors

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        updateRainbowWheelSize(START_SCALE_VALUE)

        binding.apply {
            resetButton.setOnClickListener {
                tvTitle.text = getString(R.string.click_circle)
                image.visibility = View.INVISIBLE
                customText.visibility = View.INVISIBLE
            }

            rainbowCircleView.setOnClickListener {
                val animator = ObjectAnimator.ofFloat(
                    it,
                    ROTATION_PROPERTY,
                    STARTING_ROTATION_VALUE,
                    getRandomRotation()
                )
                animator.interpolator = LinearInterpolator()
                animator.duration = DURATION_VALUE
                animator.addListener(
                    onEnd = { endAnim() },
                    onStart = { startAnim() }
                )
                animator.setFloatValues(it.rotation, it.rotation + getRandomRotation())
                animator.start()
            }

            seekBar.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
                override fun onStartTrackingTouch(slider: Slider) { }

                override fun onStopTrackingTouch(slider: Slider) {
                    updateRainbowWheelSize(slider.value)
                }
            })

        }

    }

    private fun showText() {
        binding.apply {
            customText.visibility = View.VISIBLE
            image.visibility = View.INVISIBLE

            customText.invalidate()
            customText.setColor(color)
            customText.text = color.name
        }
    }

    private fun showImage() {
        binding.apply {
            image.visibility = View.VISIBLE
            customText.visibility = View.INVISIBLE

            image.invalidate()
            Picasso.get()
                .load(URL)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(image)
        }
    }

    private fun updateRainbowWheelSize(progress: Float) {
        var size = progress / MAX_SCALE_VALUE
        if (progress < MIN_SCALE_VALUE) size = MIN_SCALE_VALUE / MAX_SCALE_VALUE
        val scaleDownX =
            ObjectAnimator.ofFloat(binding.rainbowCircleView, SCALE_X_PROPERTY, size)
        val scaleDownY =
            ObjectAnimator.ofFloat(binding.rainbowCircleView, SCALE_Y_PROPERTY, size)
        val scaleDown = AnimatorSet()
        scaleDown.play(scaleDownX).with(scaleDownY)
        scaleDown.start()
    }

    private fun getRandomRotation(): Float =
        (MIN_ROTATION_VALUE..MAX_ROTATION_VALUE).random().toFloat()

    private fun startAnim() {
        binding.tvTitle.text = getString(R.string.selectedColor)
    }

    private fun endAnim() {
        color = binding.rainbowCircleView.getColor()
        binding.tvTitle.text = getString(R.string.title, color.name, color.action)
        when (color.action) {
            Action.SHOW_TEXT -> showText()
            Action.SHOW_IMAGE -> showImage()
        }
    }

    companion object {
        private const val URL = "https://loremflickr.com/640/360"
        private const val MIN_ROTATION_VALUE = 200
        private const val MAX_ROTATION_VALUE = 720
        private const val DURATION_VALUE = 1000L
        private const val STARTING_ROTATION_VALUE = 0f
        private const val START_SCALE_VALUE = 50f
        private const val MIN_SCALE_VALUE = 10f
        private const val MAX_SCALE_VALUE = 100f
        private const val SCALE_X_PROPERTY = "scaleX"
        private const val SCALE_Y_PROPERTY = "scaleY"
        private const val ROTATION_PROPERTY = "rotation"
    }
}
