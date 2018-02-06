package com.mateuszbartos.theguard.presenters

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
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
    private val userTokenSubject = BehaviorSubject.create<String>()

    init {
        val currentUser = FirebaseAuth.getInstance().currentUser

        currentUser
                ?.getIdToken(true)
                ?.addOnSuccessListener {
                    userTokenSubject.onNext(it.token)
                }

        userTokenSubject
                .flatMap {
                    ApiClient.get().getUserDevices(ApiDto.DeviceOwner(currentUser?.email!!, it))
                            .onErrorReturn { Response.error(HttpURLConnection.HTTP_UNAVAILABLE, RealResponseBody(null, null)) }
                            .lift(IgnoreOnComplete<Response<List<Device>>>())
                            .subscribeOn(Schedulers.io())
                }
                .map { it.body() }
                .filter { it != null }
                .flatMap { Observable.from(it) }
                .flatMap {
                    SensorsFirebaseStore(it.serial).listObservable()
                }
                .subscribe(sensorDataLoadedSubject)
    }


    fun sensorDataLoadedObservable(): Observable<List<DeviceData>> {
        return sensorDataLoadedSubject.asObservable()
    }
}