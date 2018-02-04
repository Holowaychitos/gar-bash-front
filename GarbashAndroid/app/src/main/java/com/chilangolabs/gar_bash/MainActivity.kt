package com.chilangolabs.gar_bash

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setStatusBarWhite(window)

        initListeners()

    }

    fun initListeners() {
        btnLogin.setOnClickListener {
            val intent = Intent(this, GeneralActivity::class.java)
            val options = ActivityOptions.makeSceneTransitionAnimation(this, imgLoginProfile, ViewCompat.getTransitionName(imgLoginProfile))
            startActivity(intent, options.toBundle())
        }
    }

}
