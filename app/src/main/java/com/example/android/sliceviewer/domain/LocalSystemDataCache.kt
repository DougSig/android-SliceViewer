package com.example.android.sliceviewer.domain

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.android.sliceviewer.ui.model.SystemPackage

class LocalSystemDataCache(val sharedPrefs: SharedPreferences) : SystemDataCache {
    override fun putAllSlices(systemPackages: List<SystemPackage>) {
        sharedPrefs.edit {
            putString(KEY_SYSTEM_PACKAGES, systemPackages.toString())
        }
    }

    override fun getAllSlices(): List<SystemPackage> {
        sharedPrefs.getString(KEY_SYSTEM_PACKAGES, "")
        return emptyList()
    }

    companion object {
        const val KEY_SYSTEM_PACKAGES = "systemPackages"
    }
}