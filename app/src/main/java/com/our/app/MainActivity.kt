package com.our.app

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessaging
//import com.our.data.features.phase_one.datasources.PhaseOneApiService
import com.our.data.features.phase_one.models.TeacherProfileResponse
import dagger.hilt.android.AndroidEntryPoint
import com.our.data.common.datasources.MockApiService
import com.our.data.features.phase_one.datasources.PhaseOneApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                // Log and toast the token

                val msg = getString(R.string.msg_token_fmt, token)
                Log.d(TAG, msg)
                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            } else {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
            }
        }
    }


}