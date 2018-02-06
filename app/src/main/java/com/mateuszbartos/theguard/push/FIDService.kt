package com.mateuszbartos.theguard.push

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import com.mateuszbartos.theguard.api.ApiClient
import com.mateuszbartos.theguard.models.ApiDto


class FIDService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        val refreshedToken = FirebaseInstanceId.getInstance().token

        val currentUser = FirebaseAuth.getInstance().currentUser

        currentUser
                ?.getIdToken(true)
                ?.addOnSuccessListener {
                    ApiClient.get().registerForNotifications(ApiDto.RegisterNotifications(currentUser.email!!, it.token!!, refreshedToken!!))
                            .subscribe()
                }
    }

}