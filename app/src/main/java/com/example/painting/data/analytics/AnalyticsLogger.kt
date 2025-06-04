package com.example.painting.data.analytics

import android.os.Bundle

interface AnalyticsLogger {
    fun logEvent(name: String, params: Bundle? = null)
}
