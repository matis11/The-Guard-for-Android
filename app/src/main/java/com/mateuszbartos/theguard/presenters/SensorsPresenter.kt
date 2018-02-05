package com.mateuszbartos.theguard.presenters

import com.mateuszbartos.theguard.SensorsFirebaseStore
import com.mateuszbartos.theguard.models.ApiDto
import rx.Observable
import rx.subjects.BehaviorSubject


class SensorsPresenter(val deviceList: List<String>) {

    private val sensorDataLoadedSubject = BehaviorSubject.create<List<ApiDto>>()

    init {
        val sensorsFirebaseStore = SensorsFirebaseStore()
        sensorsFirebaseStore.listObservable()
                .subscribe(sensorDataLoadedSubject)
    }

    fun sensorDataLoadedObservable(): Observable<List<ApiDto>> {
        return sensorDataLoadedSubject.asObservable()
    }
}