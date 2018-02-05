package com.mateuszbartos.theguard

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mateuszbartos.theguard.models.ApiDto
import rx.Observable
import rx.subjects.BehaviorSubject

class SensorsFirebaseStore {

    private val database = FirebaseDatabase.getInstance()
    private val basePath: String
    private val gson: Gson = GsonProvider.get()

    constructor() {
        basePath = "sensor/00000000fb021b9a/"
    }

    constructor(customPath: String) {
        basePath = customPath
    }

    fun readObservable(): Observable<List<ApiDto>> {
        val dataSubject: BehaviorSubject<List<ApiDto>> = BehaviorSubject.create()

        val pathReference = database.getReference(basePath)

        pathReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.value.toString()
                val type = object : TypeToken<List<ApiDto>>() {}.type

                dataSubject.onNext(gson.fromJson(value, type))
            }

            override fun onCancelled(databaseError: DatabaseError) {
                dataSubject.onError(databaseError.toException())
            }
        })

        return dataSubject
    }

    fun write(dataToSave: List<ApiDto>) {
        checkNotNull(dataToSave)

        val serializedData = gson.toJson(dataToSave)
        val pathReference = database.getReference(basePath)

        pathReference.setValue(serializedData)
    }

    fun add(dataToAdd: List<ApiDto>) {
        checkNotNull(dataToAdd)

        val serializedData = gson.toJson(dataToAdd)
        val pathReference = database.getReference(basePath)

        pathReference.push().setValue(serializedData)
    }

    fun listObservable(): Observable<List<ApiDto>> {
        val listSubject: BehaviorSubject<List<ApiDto>> = BehaviorSubject.create()

        val pathReference = database.getReference(basePath)
        val list: MutableList<ApiDto> = ArrayList()

        pathReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list.clear()

                dataSnapshot.children.mapTo(list) {
                    val sensorValueMap = it.value as HashMap<*, *>
                    val sensorValue = sensorValueMap["value"]
                    val doubleValue: Double

                    doubleValue = try {
                        sensorValue as Double
                    } catch (e: Exception) {
                        (sensorValue as Long).toDouble()
                    }

                    ApiDto(it.key, doubleValue)
                }

                listSubject.onNext(list)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                listSubject.onError(databaseError.toException())
            }
        })

        return listSubject
    }
}
