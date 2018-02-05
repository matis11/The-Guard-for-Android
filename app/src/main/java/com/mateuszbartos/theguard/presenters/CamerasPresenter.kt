package com.mateuszbartos.theguard.presenters

import android.content.Context
import android.preference.PreferenceManager
import com.mateuszbartos.theguard.IgnoreOnComplete
import com.mateuszbartos.theguard.api.ApiClient
import com.mateuszbartos.theguard.models.ApiDto
import com.mateuszbartos.theguard.models.Device
import okhttp3.internal.http.RealResponseBody
import retrofit2.Response
import rx.Observable
import rx.schedulers.Schedulers
import rx.subjects.BehaviorSubject
import java.net.HttpURLConnection

class CamerasPresenter(context: Context) {

    private val devicesSubject = BehaviorSubject.create<List<Device>>()

    init {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val token = sharedPreferences.getString("token", "")
        val email = sharedPreferences.getString("email", "")

        ApiClient.get().getUserDevices(ApiDto.DeviceOwner(email, token))
                .onErrorReturn { Response.error(HttpURLConnection.HTTP_UNAVAILABLE, RealResponseBody(null, null)) }
                .lift(IgnoreOnComplete<Response<List<Device>>>())
                .subscribeOn(Schedulers.io())
                .map { it.body() }
                .flatMap { Observable.from(it) }
                .toList()
                .subscribe(devicesSubject)
    }


    fun devicesIdsObservable(): Observable<List<Device>> {
        return devicesSubject.asObservable()
    }
}