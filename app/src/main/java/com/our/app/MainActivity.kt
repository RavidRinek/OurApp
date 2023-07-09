package com.our.app

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.auth.User
import com.google.firebase.messaging.FirebaseMessaging
import com.our.app.features.phase_one.teacherlobby.TeacherLobbyFragment
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

        val intent = intent
        val fragmentName = intent.getStringExtra("fragment")

        print(intent)
        val body = intent.getStringExtra("body")
        if(body!=null) {
            Log.d("Test", body!!)
        }
        if (fragmentName != null && fragmentName == "TeacherLobbyFragment") {
            if (body != null) {
                print(body)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val body = intent!!.getStringExtra("body")

        if(body!=null) {
            Log.d("Test", body!!)
        }
    }
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }
}
