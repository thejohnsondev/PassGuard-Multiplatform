package com.thejohnsondev.ui.components.animation.appear

data class AnimatedAppearParams(
    val doAnimateBlur: Boolean,
    val doAnimateTranslationY: Boolean,
    val doAnimateAlpha: Boolean,
    val delayBeforeAnim: Long,
    val blurScaleAnimStart: Float,
    val blurScaleAnimEnd: Float,
    val blurScaleAnimDuration: Int,
    val yTranslationAnimStart: Float,
    val yTranslationAnimEndPx: Float,
    val yTranslationAnimEndDuration: Int,
    val alphaAnimStart: Float,
    val alphaAnimEnd: Float,
    val alphaAnimDuration: Int,
) {
    companion object {
        fun default(
            doAnimateBlur: Boolean = true,
            doAnimateTranslationY: Boolean = true,
            doAnimateAlpha: Boolean = true,
            delayBeforeAnim: Long = 200L,
            blurScaleAnimStart: Float = 20f,
            blurScaleAnimEnd: Float = 0f,
            blurScaleAnimDuration: Int = 600,
            logoYAnimStart: Float = -100f,
            logoYAnimEndPx: Float = 0f,
            logoYAnimEndDuration: Int = 700,
            logoAlphaAnimStart: Float = 0f,
            logoAlphaAnimEnd: Float = 1f,
            logoAlphaAnimDuration: Int = 300,
        ) = AnimatedAppearParams(
            doAnimateBlur = doAnimateBlur,
            doAnimateTranslationY = doAnimateTranslationY,
            doAnimateAlpha = doAnimateAlpha,
            delayBeforeAnim = delayBeforeAnim,
            blurScaleAnimStart = blurScaleAnimStart,
            blurScaleAnimEnd = blurScaleAnimEnd,
            blurScaleAnimDuration = blurScaleAnimDuration,
            yTranslationAnimStart = logoYAnimStart,
            yTranslationAnimEndPx = logoYAnimEndPx,
            yTranslationAnimEndDuration = logoYAnimEndDuration,
            alphaAnimStart = logoAlphaAnimStart,
            alphaAnimEnd = logoAlphaAnimEnd,
            alphaAnimDuration = logoAlphaAnimDuration,
        )
    }
}
