package com.nifa.fuel_buddy.core.domain

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber

object CustomScope {

    private val TAG = CustomScope::class.java.simpleName

    fun getApplicationScope(): CoroutineScope {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            Timber.d("$TAG >> customCoroutineScopeException $throwable")
        }

        val context = Dispatchers.IO + SupervisorJob() + coroutineExceptionHandler

        return CoroutineScope(context)
    }
}