package com.bluetoolth.cupping

import android.Manifest
import android.app.Activity
import android.content.IntentSender
import android.content.pm.PackageManager
import android.webkit.GeolocationPermissions
import com.bluetoolth.cupping.main.MainActivity
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority

/**
 * Created by KimBH on 2023/02/21.
 */
class LocationUtil(private val activity: Activity) {
    private var origin: String? = null
    private var callback: GeolocationPermissions.Callback? = null

    fun checkPermission(origin: String?, callback: GeolocationPermissions.Callback?) {
        this.origin = origin
        this.callback = callback
        if (activity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            origin?.let {
                checkLocation()
            }
        } else {
            activity.requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MainActivity.LOCATION_PERMISSION
            )
        }
    }

    fun checkLocation() {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(5000)
            .build()

        val task = LocationServices.getSettingsClient(activity).checkLocationSettings(
            LocationSettingsRequest.Builder().addLocationRequest(locationRequest).build())

        task.addOnSuccessListener {
            callback?.invoke(origin, true, false)
        }

        task.addOnFailureListener {
            if (it is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    it.startResolutionForResult(
                        activity,
                        MainActivity.LOCATION_PERMISSION
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                    callback?.invoke(origin, true, false)
                }
            } else {
                callback?.invoke(origin, true, false)
            }
        }
    }
}