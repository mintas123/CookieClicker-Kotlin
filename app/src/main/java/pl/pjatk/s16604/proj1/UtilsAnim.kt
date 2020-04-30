package pl.pjatk.s16604.proj1

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils

fun animate(context: Context, obj: View) {
    val animationZoom = AnimationUtils.loadAnimation(context, R.anim.zoom_in)
    val animationZoomOut = AnimationUtils.loadAnimation(context, R.anim.zoom_out)

    obj.startAnimation(animationZoom)
    obj.startAnimation(animationZoomOut)

}

fun animateShake(context: Context, obj: View) {
    val animationShake = AnimationUtils.loadAnimation(context, R.anim.shake)
    obj.startAnimation(animationShake)
}

fun animateShakeBlocked(context: Context, obj: View) {
    val animationShake = AnimationUtils.loadAnimation(context, R.anim.shake_blocked)
    obj.startAnimation(animationShake)
}