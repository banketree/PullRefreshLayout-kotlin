package com.yan.pullrefreshlayout


import android.view.animation.Interpolator

/**
 * @author yanxianwei
 */
class ViscousInterpolator @JvmOverloads constructor(private val currentViscousScale: Float = VISCOUS_FLUID_SCALE) :
    Interpolator {

    private val VISCOUS_FLUID_NORMALIZE: Float
    private val VISCOUS_FLUID_OFFSET: Float

    init {
        // must be set to 1.0 (used in viscousFluid())
        VISCOUS_FLUID_NORMALIZE = 1.0f / viscousFluid(currentViscousScale, 1.0f)
        // account for very small floating-point error
        VISCOUS_FLUID_OFFSET =
            1.0f - VISCOUS_FLUID_NORMALIZE * viscousFluid(currentViscousScale, 1.0f)
    }

    private fun viscousFluid(viscousScale: Float, x: Float): Float {
        var x = x
        x *= viscousScale
        if (x < 1.0f) {
            x -= 1.0f - Math.exp((-x).toDouble()).toFloat()
        } else {
            val start = 0.36787944117f   // 1/e == exp(-1)
            x = 1.0f - Math.exp((1.0f - x).toDouble()).toFloat()
            x = start + x * (1.0f - start)
        }
        return x
    }

    override fun getInterpolation(input: Float): Float {
        val interpolated = VISCOUS_FLUID_NORMALIZE * viscousFluid(currentViscousScale, input)
        return if (interpolated > 0) {
            interpolated + VISCOUS_FLUID_OFFSET
        } else interpolated
    }

    companion object {
        /**
         * Controls the viscous fluid effect (how much of it).
         */
        private const val VISCOUS_FLUID_SCALE = 4.5f
    }
}