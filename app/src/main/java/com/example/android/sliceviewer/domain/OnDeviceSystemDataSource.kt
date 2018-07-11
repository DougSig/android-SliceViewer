/*
 * Copyright 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.sliceviewer.domain

import android.app.slice.SliceProvider
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.ProviderInfo
import android.net.Uri
import androidx.slice.SliceViewManager
import com.example.android.sliceviewer.ui.model.PackageSlice
import com.example.android.sliceviewer.ui.model.SystemPackage

class OnDeviceSystemDataSource(val context: Context) : SystemDataSource {
    override fun getAllSlices(): List<SystemPackage> {
        val systemPackages = mutableListOf<SystemPackage>()
        val sliceProviders = mutableListOf<ProviderInfo>()
        val sliceViewManager = SliceViewManager.getInstance(context)
        context.packageManager.getInstalledPackages(PackageManager.GET_PROVIDERS).forEach {
            val systemPackage = SystemPackage(it.packageName, mutableListOf())
            it.providers?.forEach {
                if (SliceProvider.SLICE_TYPE == it.toUriString()) {
                    sliceProviders.add(it)
                    val authority = it.authority.split(";")[0]
                    val slices = sliceViewManager.getSliceDescendants(
                        Uri.Builder()
                            .scheme(ContentResolver.SCHEME_CONTENT)
                            .authority(authority)
                            .build()
                    )
                    systemPackage.slices.addAll(slices.map { PackageSlice(it) })
                }
            }
            if (systemPackage.slices.isNotEmpty()) {
                systemPackages.add(systemPackage)
            }
        }
        return systemPackages
    }

    private fun ProviderInfo.toUriString(): String {
        authority ?: return ""
        val parsedAuthority = authority.split(";".toRegex())
            .dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        return Uri.Builder()
            .scheme(ContentResolver.SCHEME_CONTENT)
            .authority(parsedAuthority)
            .build()
            .toString()
    }
}