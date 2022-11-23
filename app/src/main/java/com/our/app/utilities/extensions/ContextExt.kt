package com.our.app.utilities.extensions

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import androidx.fragment.app.FragmentActivity

fun Context?.isAvailable(): Context? {
    if (this == null) {
        return null
    } else if (this !is Application) {
        if (this is FragmentActivity) {
            return if (this.isDestroyed) null else this
        } else if (this is Activity) {
            return if (this.isDestroyed) null else this
        }
    }
    return this
}

fun Context.activity(): Activity? {
    return when (this) {
        is Activity -> this
        is ContextWrapper -> this.baseContext.activity()
        else -> null
    }
}