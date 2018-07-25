package com.example.android.sliceviewer.domain

import com.example.android.sliceviewer.ui.model.SystemPackage

class OnDeviceSystemRepository(
    private val systemDataCache: SystemDataCache,
    private val systemDataSource: SystemDataSource
) {
    fun refreshSlices() {
        systemDataCache.putAllSlices(systemDataSource.getAllSlices())
    }

    fun getAllSlices(): List<SystemPackage> {
        return systemDataCache.getAllSlices()
    }
}