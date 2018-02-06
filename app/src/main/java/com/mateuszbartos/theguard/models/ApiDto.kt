package com.mateuszbartos.theguard.models

class ApiDto {
    data class DeviceOwner(val owner: String, val token: String)

    data class RegisterDevice(val serial: String, val name: String, val token: String)

    data class DeviceName(val serial: String, val name: String, val token: String)

    data class DeviceArmed(val serial: String, val armed: Boolean, val token: String)

    data class DeviceNotifications(val serial: String, val token: String)

    data class RegisterNotifications(val email: String, val fcmToken: String, val deviceId: String)
}