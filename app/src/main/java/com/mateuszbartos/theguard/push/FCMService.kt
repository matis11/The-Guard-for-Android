package com.mateuszbartos.theguard.push

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mateuszbartos.theguard.R


class FCMService : FirebaseMessagingService() {

    companion object {
        val NOTIFICATION_ID = 751
        val REQUEST_CODE = 786
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        val title = remoteMessage?.notification?.title
        val content = remoteMessage?.notification?.body

        val notificationBuilder = Notification.Builder(applicationContext)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.mipmap.ic_launcher)

        val notifyMgr = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notifyMgr.notify(NOTIFICATION_ID, notificationBuilder.build())

    }

}