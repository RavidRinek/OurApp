package com.our.app.utilities.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.our.app.MainActivity
import com.our.app.R

class MyFirebaseMessagingService  : FirebaseMessagingService() {
    // [START receive_message]

    companion object {
        private const val TAG = "MyFirebaseMsgService"
        const val ACTION_NOTIFICATION_CLICK = "com.our.app.utilities.service.NOTIFICATION_CLICK"
    }
    private fun showToast(title: String) {
        // Display the title of the notification in a toast
        Toast.makeText(applicationContext, title, Toast.LENGTH_SHORT).show()
    }
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: ${remoteMessage.from}")

        //-------------------------------------------------------------//
        remoteMessage.notification?.let { notification ->
          //  Log.d(TAG, "Message Notification Body: ${notification.body}")
            val title = notification.title
            val message = notification.body
            if (title != null) {
                sendNotification(message ?: "", applicationContext)
                scheduleJob()
            }
            val lbm = LocalBroadcastManager.getInstance(this)
            val dataIntent = Intent().apply {
                putExtra("badge", notification.body)
            }
            lbm.sendBroadcast(dataIntent)
        }
        //-------------------------------------------------------------//

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
            var message = remoteMessage.data.get("body") as String
            sendNotification(message!! , applicationContext)
            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                scheduleJob()
            } else {
                // Handle message within 10 seconds
                handleNow()
            }
        } // Check if message contains a notification payload.
       /* remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            sendNotification(it.body!!, applicationContext)
        }*/

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    //-------------------------------------------//

    //-------------------------------------------//
    // [END receive_message]

    // [START on_new_token]
    /**
     * Called if the FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * FCM registration token is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
//        sendRegistrationToServer(token)
    }
    // [END on_new_token]

    private fun scheduleJob() {
        // [START dispatch_job]
        val work = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .build()
        WorkManager.getInstance(this)
            .beginWith(work)
            .enqueue()
        // [END dispatch_job]
    }

    private fun handleNow() {
        Log.d(TAG, "Short lived task is done.")
    }

    private fun sendRegistrationToServer(token: String?) {
        // TODO: Implement this method to send token to your app server.
        Log.d(TAG, "sendRegistrationTokenToServer($token)")
    }

    fun getNotificationClickPendingIntent(context: Context): PendingIntent {
        val intent = Intent(ACTION_NOTIFICATION_CLICK)
        intent.setClass(context, MainActivity::class.java)
        return PendingIntent.getBroadcast(context, 0, intent,   PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun sendNotification(messageBody: String, context: Context) {
        //val intent = Intent(this, MainActivity::class.java)
        //intent.action = ACTION_NOTIFICATION_CLICK
        //val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val intent = Intent(context, MainActivity::class.java)
       // intent.putExtra("fragment", "TeacherLobbyFragment") // Pass the fragment name as an extra
        print(messageBody)

        intent.putExtra("body", messageBody) // Pass the user object as an extra
        //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
       // intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
      //  intent.action = Intent.ACTION_VIEW






              val pendingIntent = PendingIntent.getActivity(context, 0, intent,  PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val channelId = "fcm_default_channel"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)

            .setSmallIcon(R.drawable.ic_our_logo)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_CALL)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_HIGH)
            channel.importance = NotificationManager.IMPORTANCE_HIGH
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(1 /* ID of notification */, notificationBuilder.build())
    }

    internal class MyWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {
        override fun doWork(): Result {
            // TODO(developer): add long running task here.
            return Result.success()
        }
    }
}