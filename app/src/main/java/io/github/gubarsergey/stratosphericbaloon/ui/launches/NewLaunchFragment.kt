package io.github.gubarsergey.stratosphericbaloon.ui.launches

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.gubarsergey.stratosphericbaloon.App
import io.github.gubarsergey.stratosphericbaloon.R
import io.github.gubarsergey.stratosphericbaloon.db.RealmMapper
import io.github.gubarsergey.stratosphericbaloon.extension.makeInvisible
import io.github.gubarsergey.stratosphericbaloon.extension.makeVisible
import io.github.gubarsergey.stratosphericbaloon.extension.notNullContext
import io.github.gubarsergey.stratosphericbaloon.db.launches.LaunchesRepository
import io.github.gubarsergey.stratosphericbaloon.db.launches.realm.LaunchMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_new_launch.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.longToast
import org.jetbrains.anko.support.v4.toast

private const val LOCATION_PERMISSION_REQUEST_CODE = 444
private const val DEFAULT_LONGITUDE = 44.5
private const val DEFAULT_LATITIUDE = 38.0

class NewLaunchFragment : Fragment(), AnkoLogger {

    private val retrofit by lazy {
        (activity?.application as App).getRetrofit()
    }

    private val launchesRepository by lazy {
        LaunchesRepository(notNullContext, retrofit, LaunchMapper())
    }

    private var longitude = 44.5
    private var latitude = 38.0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_launch, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {

        if (ContextCompat.checkSelfPermission(notNullContext, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 444)
        } else {
            setupLocationListener()
        }
        new_launch_check_button.setOnClickListener {
            launchesRepository.getNearLaunches(longitude, latitude)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    new_launch_result_text_view.makeInvisible()
                }
                .subscribe({ launches ->
                    info("Got launches $it")
                    if (launches.isEmpty()) {
                        new_launch_result_text_view.makeVisible()
                    } else {
                        longToast(R.string.launch_not_allowed)
                    }
                }) {
                    info("Fail $it")
                }
        }
    }

    @SuppressLint("MissingPermission")
    private fun setupLocationListener() {
        val lm = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val locationListener = object : android.location.LocationListener  {
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

            }

            override fun onProviderEnabled(provider: String?) {
            }

            override fun onProviderDisabled(provider: String?) {
            }

            override fun onLocationChanged(location: Location) {
                latitude = location.latitude
                longitude = location.longitude
            }

        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10f, locationListener)
        lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        setupLocationListener()
    }
}