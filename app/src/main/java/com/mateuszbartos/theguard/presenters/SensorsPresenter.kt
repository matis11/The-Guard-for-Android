package com.mateuszbartos.theguard.presenters

import com.mateuszbartos.theguard.SensorsFirebaseStore
import com.mateuszbartos.theguard.models.SensorData
import rx.Observable
import rx.subjects.BehaviorSubject


class SensorsPresenter(val deviceList: List<String>) {

    private val sensorDataLoadedSubject = BehaviorSubject.create<List<SensorData>>()

    init {
        val sensorsFirebaseStore = SensorsFirebaseStore()
        sensorsFirebaseStore.readObservable()
                .subscribe(sensorDataLoadedSubject)
    }

    fun sensorDataLoadedObservable(): Observable<List<SensorData>> {
        return sensorDataLoadedSubject.asObservable()
    }
}