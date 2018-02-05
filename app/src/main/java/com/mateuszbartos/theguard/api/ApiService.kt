package com.mateuszbartos.theguard.api

import com.mateuszbartos.theguard.models.ApiDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

interface ApiService {
    @POST("v1/devices/get")
    fun getUserDevices(@Body device: ApiDto.DeviceOwner): Observable<Response<String>>

    @POST("v1/devices/add")
    fun addDevice(@Body device: ApiDto.RegisterDevice): Observable<Response<String>>

    @POST("v1/devices/assign")
    fun assignDevice(@Body device: ApiDto.DeviceOwner): Observable<Response<String>>

    @POST("v1/devices/changeRaspName")
    fun changeName(@Body device: ApiDto.DeviceName): Observable<Response<String>>

    @POST("v1/devices/changeIsArmed")
    fun armDevice(@Body device: ApiDto.DeviceArmed): Observable<Response<String>>

    @POST("v1/devices/getNotifications")
    fun getNotifications(@Body device: ApiDto.DeviceNotifications): Observable<Response<String>>

    @POST("v1/devices/fcmTokenUpdate")
    fun registerForNotifications(@Body device: ApiDto.RegisterNotifications): Observable<Response<String>>
}