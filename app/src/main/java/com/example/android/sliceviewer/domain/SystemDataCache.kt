package com.example.android.sliceviewer.domain

import com.example.android.sliceviewer.ui.model.SystemPackage

interface SystemDataCache {
    fun putAllSlices(systemPackages: List<SystemPackage>)
    fun getAllSlices(): List<SystemPackage>
}