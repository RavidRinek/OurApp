package com.our.app.utilities.bindingDelegates

import android.os.Handler
import android.os.Looper
import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

interface ViewBindingProperty<in R : Any, T : ViewBinding> : ReadOnlyProperty<R, T> {

    @MainThread
    fun clear()
}

abstract class LifecycleViewBindingProperty<in R : Any, T : ViewBinding>(
        private val viewBinder: (R) -> T
) : ViewBindingProperty<R, T> {

    private var viewBinding: T? = null

    protected abstract fun getLifecycleOwner(thisRef: R): LifecycleOwner

    @MainThread
    override fun getValue(thisRef: R, property: KProperty<*>): T {
        viewBinding?.let { return it }

        val lifecycle = getLifecycleOwner(thisRef).lifecycle
        val viewBinding = viewBinder(thisRef)
        if (lifecycle.currentState == Lifecycle.State.DESTROYED) {
            // We can access to ViewBinding after on destroy, but don't save to prevent memory leak
        } else {
            lifecycle.addObserver(ClearOnDestroyLifecycleObserver())
            this.viewBinding = viewBinding
        }
        return viewBinding
    }

    @MainThread
    override fun clear() {
        mainHandler.post { viewBinding = null }
    }

    inner class ClearOnDestroyLifecycleObserver : DefaultLifecycleObserver {

        @MainThread
        override fun onDestroy(owner: LifecycleOwner): Unit = clear()
    }

    private companion object {

        private val mainHandler = Handler(Looper.getMainLooper())
    }
}

open class LazyViewBindingProperty<in R : Any, T : ViewBinding>(
    private val onViewDestroyed: (T) -> Unit,
    protected val viewBinder: (R) -> T,
) : ViewBindingProperty<R, T> {

    constructor(viewBinder: (R) -> T) : this({}, viewBinder)

    protected var viewBinding: Any? = null

    @Suppress("UNCHECKED_CAST")
    @MainThread
    override fun getValue(thisRef: R, property: KProperty<*>): T {
        return viewBinding as? T ?: viewBinder(thisRef).also { viewBinding ->
            this.viewBinding = viewBinding
        }
    }

    @Suppress("UNCHECKED_CAST")
    @MainThread
    @CallSuper
    override fun clear() {
        val viewBinding = this.viewBinding as T?
        if (viewBinding != null) {
            onViewDestroyed(viewBinding)
        }
        this.viewBinding = null
    }
}