package com.mateuszbartos.theguard

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder

object GsonProvider {
    private var instance: Gson? = null

    fun get(): Gson {
        if (instance == null) {
            init()
        }
        return instance!!
    }

    private fun init() {
        instance = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
    }
}
