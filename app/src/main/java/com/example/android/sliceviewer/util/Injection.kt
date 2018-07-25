package com.example.android.sliceviewer.util

import android.content.Context
import android.content.SharedPreferences
import com.example.android.sliceviewer.domain.LocalSystemDataCache
import com.example.android.sliceviewer.domain.OnDeviceSystemDataSource
import com.example.android.sliceviewer.domain.OnDeviceSystemRepository
import com.example.android.sliceviewer.domain.SystemDataCache
import com.example.android.sliceviewer.domain.SystemDataSource

object Injection {

    fun provideSharedPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences("slice_viewer", Context.MODE_PRIVATE)
    }

    fun provideSystemDataCache(sharedPrefs: SharedPreferences): SystemDataCache {
        return LocalSystemDataCache(sharedPrefs)
    }

    fun provideSystemDataSource(context: Context): SystemDataSource {
        return OnDeviceSystemDataSource(context)
    }

    fun provideSystemRepository(
        systemDataCache: SystemDataCache,
        systemDataSource: SystemDataSource
    ): OnDeviceSystemRepository {
        return OnDeviceSystemRepository(systemDataCache, systemDataSource)
    }
}