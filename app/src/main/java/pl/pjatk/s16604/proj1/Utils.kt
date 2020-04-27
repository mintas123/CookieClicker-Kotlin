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