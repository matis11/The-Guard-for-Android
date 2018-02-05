package com.mateuszbartos.theguard.presenters

import android.content.Context
import android.preference.PreferenceManager
import com.mateuszbartos.theguard.IgnoreOnComplete
import com.mateuszbartos.theguard.SensorsFirebaseStore
import com.mateuszbartos.theguard.api.ApiClient
import com.mateuszbartos.theguard.models.ApiDto
import com.mateuszbartos.theguard.models.Device
import com.mateuszbartos.theguard.models.DeviceData
import okhttp3.internal.http.RealResponseBody
import retrofit2.Response
import rx.Observable
import rx.schedulers.Schedulers
import rx.subjects.BehaviorSubject
import java.net.HttpURLConnection


class SensorsPresenter(context: Context) {

    private val sensorDataLoadedSubject = BehaviorSubject.create<List<DeviceData>>()

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
                .flatMap { SensorsFirebaseStore(it.serial).listObservable() }
                .subscribe(sensorDataLoadedSubject)
    }


    fun sensorDataLoadedObservable(): Observable<List<DeviceData>> {
        return sensorDataLoadedSubject.asObservable()
    }
}