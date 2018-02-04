package com.chilangolabs.gar_bash.utils

import android.support.annotation.LayoutRes
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.animation.AnticipateInterpolator

/**
 * Created by gorrotowi on 03/02/18.
 */

public fun updateConstraints(v: ConstraintLayout, @LayoutRes id: Int) {
    val newConstraintSet = ConstraintSet()
//    newConstraintSet.clone(, id)
    newConstraintSet.applyTo(v)
    val transition = ChangeBounds()
    transition.interpolator = AnticipateInterpolator()
    TransitionManager.beginDelayedTransition(v, transition)
}