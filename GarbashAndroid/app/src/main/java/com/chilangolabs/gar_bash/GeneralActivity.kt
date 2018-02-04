package com.chilangolabs.gar_bash

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import com.chilangolabs.gar_bash.network.Api
import com.chilangolabs.gar_bash.network.OnRqListener
import com.chilangolabs.gar_bash.network.request.Location
import com.chilangolabs.gar_bash.network.request.UserUpdatePickGarbage
import com.chilangolabs.gar_bash.utils.loge
import com.tomtom.online.sdk.map.MapConstants
import com.tomtom.online.sdk.map.MapFragment
import com.tomtom.online.sdk.map.OnMapReadyCallback
import com.tomtom.online.sdk.map.TomtomMap
import com.tomtom.online.sdk.map.model.MapTilesType
import com.tomtom.online.sdk.map.model.MapTrafficType
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_general.*
import java.util.concurrent.Executors

class GeneralActivity : BaseActivity() {

    var tomtomMap: TomtomMap? = null

    val networkScheduler: Scheduler = Schedulers.from(Executors.newFixedThreadPool(4))
    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general)
        setStatusBarWhite(window)

        Api.initRetrofit()

        val map = map_fragment as MapFragment

        val schedules = mutableListOf("12 PM ~ 1 AM",
                "1 AM ~ 2 AM",
                "2 AM ~ 3 AM",
                "3 AM ~ 4 AM",
                "4 AM ~ 5 AM",
                "5 AM ~ 6 AM",
                "6 AM ~ 7 AM",
                "7 AM ~ 8 AM",
                "8 AM ~ 9 AM",
                "9 AM ~ 10 AM",
                "10 AM ~ 11 AM",
                "11 AM ~ 12 AM",
                "12 AM ~ 1 PM",
                "1 PM ~ 2 PM",
                "2 PM ~ 3 PM",
                "3 PM ~ 4 PM",
                "4 PM ~ 5 PM",
                "5 PM ~ 6 PM",
                "6 PM ~ 7 PM",
                "7 PM ~ 8 PM",
                "8 PM ~ 9 PM",
                "9 PM ~ 10 PM",
                "10 PM ~ 11 PM",
                "11 PM ~ 12 PM")

        spinner.setItems(schedules)

        map.getAsyncMap(onMapReadyCallback)

    }

    var positionTime = 0

    override fun onResume() {
        super.onResume()
        spinner.setOnItemSelectedListener { _, position, _, _ ->
            positionTime = position
        }

        btnSendPick.setOnClickListener {

            val daysChecked = arrayOf(ckbxSun?.isChecked, ckbxMon?.isChecked, ckbxTuesday?.isChecked, ckbxWed?.isChecked, ckbxThursday?.isChecked, ckbxFri?.isChecked, ckbxSath?.isChecked)
            val arrayChecks = daysChecked.map {
                it?.compareTo(false)
            }

            val rq = UserUpdatePickGarbage("Sebastian Tellez", arrayChecks, Location(tomtomMap?.userLocation?.altitude, tomtomMap?.userLocation?.longitude), positionTime)
            loge(rq)

            val progress = ProgressDialog(this)
            progress.setTitle("Sending Request")
            progress.setMessage("Wait please...")
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            progress.isIndeterminate = true

            progress.show()

            Api.updateGarbagePick(rq, object : OnRqListener {
                override fun success(response: UserUpdatePickGarbage?) {
                    progress.dismiss()
                    Toast.makeText(this@GeneralActivity, "Request received", Toast.LENGTH_SHORT).show()
                }

                override fun error(t: Throwable) {
                    progress.dismiss()
                    loge("Error Update", t)
                }

            })

        }
    }

    private val onMapReadyCallback = OnMapReadyCallback { tmtmMap ->
        tomtomMap = tmtmMap
        tomtomMap?.uiSettings?.setMapTilesType(MapTilesType.VECTOR)
        tomtomMap?.isMyLocationEnabled = true
        tomtomMap?.uiSettings?.mapTrafficType = MapTrafficType.TRAFFIC_FLOW
        tomtomMap?.setPadding(16.0, 16.0, 16.0, 16.0)
        val location = tomtomMap?.userLocation
        tomtomMap?.centerOn(location?.altitude ?: 37.787581, location?.longitude ?: -122.396603, 16, MapConstants.ORIENTATION_NORTH)

    }

}
