package com.our.app

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.messaging.FirebaseMessaging
import com.our.app.features.phase_one.teacherlobby.container.TeacherContainerFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FragmentChangeListener {

    val viewModel: MainActivityViewModel by viewModels<MainActivityViewModelImpl>()
    private var currentFrag: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                // Log and toast the token
                viewModel.postFcmToken(token)
            } else {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
            }
        }

        val intent = intent
        val fragmentName = intent.getStringExtra("fragment")

        print(intent)
        val body = intent.getStringExtra("body")
        if (body != null) {
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

        if (body != null) {
            Log.d("Test", body!!)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    override fun onBackPressed() {
        if (onBackPressedDispatcher.hasEnabledCallbacks()) {
            onBackPressedDispatcher.onBackPressed()
        } else {
            super.onBackPressed()
        }
    }

    override fun onFragmentChanged(fragment: Fragment) {
        currentFrag = fragment
    }
}

interface FragmentChangeListener {
    fun onFragmentChanged(fragment: Fragment)
}