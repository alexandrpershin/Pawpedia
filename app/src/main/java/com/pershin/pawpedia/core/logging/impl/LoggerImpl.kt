package com.pershin.pawpedia.core.logging.impl

import android.util.Log
import com.pershin.pawpedia.BuildConfig
import com.pershin.pawpedia.core.logging.Logger
import javax.inject.Inject

internal class LoggerImpl @Inject constructor() : Logger {
    override fun e(tag: String, error: String) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, error)
        } else {
            // Send non-fatals to Crashlytics
        }
    }

    override fun i(tag: String, error: String) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, error)
        }
    }
}
