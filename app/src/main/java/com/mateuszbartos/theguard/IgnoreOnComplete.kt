package com.mateuszbartos.theguard

import rx.Observable
import rx.Subscriber

class IgnoreOnComplete<T> : Observable.Operator<T, T> {

    override fun call(subscriber: Subscriber<in T>): Subscriber<in T> {
        return object : Subscriber<T>() {
            override fun onCompleted() {
                // ignore
            }

            override fun onError(e: Throwable) {
                subscriber.onError(e)
            }

            override fun onNext(t: T) {
                subscriber.onNext(t)
            }
        }
    }

}