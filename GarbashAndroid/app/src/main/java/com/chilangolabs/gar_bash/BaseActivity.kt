package com.chilangolabs.gar_bash

import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager


/**
 * Created by gorrotowi on 03/02/18.
 */
open class BaseActivity : AppCompatActivity() {

    fun setStatusBarWhite(window: Window) {
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

}