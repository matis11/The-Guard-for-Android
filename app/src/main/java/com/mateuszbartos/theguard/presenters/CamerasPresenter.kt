package com.mateuszbartos.theguard.presenters

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
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

    private val userTokenSubject = BehaviorSubject.create<String>()
    private val devicesSubject = BehaviorSubject.create<List<Device>>()

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
                .subscribe(devicesSubject)
    }


    fun devicesIdsObservable(): Observable<List<Device>> {
        return devicesSubject.asObservable()
    }
}