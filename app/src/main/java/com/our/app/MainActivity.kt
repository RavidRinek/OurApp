package com.our.app

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.messaging.FirebaseMessaging
import com.our.app.features.phase_one.teacher.teacher_lobby.TeacherLobbyFragment
import com.our.app.utilities.extensions.activity
import com.our.app.utilities.service.FirebasePushEvent
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val viewModel: MainActivityViewModel by viewModels<MainActivityViewModelImpl>()
    private var currentFrag: Fragment? = null
    private var activityResultLauncher: ActivityResultLauncher<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        OurApp.instance.activity()



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
        initRegisteredActivityForRes()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this);
    }

    override fun onResume() {
        super.onResume()
        val body = intent!!.getStringExtra("body")

        if (body != null) {
            Log.d("Test", body!!)
        }
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: FirebasePushEvent) {
        (currentFrag as? TeacherLobbyFragment)?.updateUpcomingLessons()
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

    fun onFragmentChanged(fragment: Fragment) {
        currentFrag = fragment
    }

    private fun initRegisteredActivityForRes() {
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Timber.d("notification permission failed")
            }
        }.also {
            activityResultLauncher = it
        }
    }

    fun requestPushNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            activityResultLauncher?.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}