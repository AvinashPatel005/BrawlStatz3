package com.kal.brawlstatz3.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.kal.brawlstatz3.MainActivity
import com.kal.brawlstatz3.R
import kotlin.math.log

class MessageService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val data = remoteMessage.data
        val title = remoteMessage.notification?.title ?: data["title"]
        val body = remoteMessage.notification?.body ?: data["body"]

        val route = data["route"]
        val url = data["url"]

        // Image from FCM console
        val imageUrl = remoteMessage.notification?.imageUrl?.toString()

        showNotification(title, body, route, url, imageUrl)
    }
    private fun showNotification(title: String?, body: String?, route: String?, url: String?, imageUrl: String?) {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("route", route)
            putExtra("url", url)
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        var style: NotificationCompat.Style? = null

        if (!imageUrl.isNullOrEmpty()) {
            try {
                val bitmap = BitmapFactory.decodeStream(java.net.URL(imageUrl).openStream())
                style = NotificationCompat.BigPictureStyle()
                    .bigPicture(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val builder = NotificationCompat.Builder(this, "bs001")
            .setSmallIcon(R.drawable.ic_notification_logo)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        if (style != null) builder.setStyle(style)

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            "bs001", "App Notifications", NotificationManager.IMPORTANCE_HIGH
        )
        manager.createNotificationChannel(channel)
        manager.notify(System.currentTimeMillis().toInt(), builder.build())
    }


    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }


}