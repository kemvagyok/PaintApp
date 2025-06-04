package com.example.painting.data.di

import android.content.Context
import com.example.painting.data.analytics.AnalyticsLogger
import com.example.painting.data.analytics.FirebaseAnalyticsLogger
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AnalyticsModule {
    @Provides
    @Singleton
    fun provideFirebaseAnalytics(@ApplicationContext context: Context): FirebaseAnalytics {
        return FirebaseAnalytics.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideAnalyticsLogger(firebaseAnalytics: FirebaseAnalytics): AnalyticsLogger {
        return FirebaseAnalyticsLogger(firebaseAnalytics)
    }
}