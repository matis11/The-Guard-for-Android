package com.mateuszbartos.theguard.presenters

import com.mateuszbartos.theguard.SensorsFirebaseStore
import rx.Observable
import rx.subjects.BehaviorSubject


class SensorsPresenter {

    private val sensorDataLoadedSubject = BehaviorSubject.create<String>()

    init {
        val sensorsFirebaseStore = SensorsFirebaseStore()
        sensorsFirebaseStore.readObservable()
                .map {
                    val result = StringBuilder()
                    it.forEach { result.append(it) }
                    return@map result.toString()
                }
                .subscribe(sensorDataLoadedSubject)
    }

    fun sensorDataLoadedObservable(): Observable<String> {
        return sensorDataLoadedSubject.asObservable()
    }
}