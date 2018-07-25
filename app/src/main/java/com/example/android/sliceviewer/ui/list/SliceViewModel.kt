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

package com.example.android.sliceviewer.ui.list

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.slice.widget.SliceView
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.android.sliceviewer.domain.SystemDataCache
import com.example.android.sliceviewer.domain.SystemScannerWorker
import com.example.android.sliceviewer.domain.UriDataSource
import com.example.android.sliceviewer.ui.model.SystemPackage

class SliceViewModel(
    private val uriDataSource: UriDataSource,
    private val systemDataCache: SystemDataCache,
    private val workManager: WorkManager
) : ViewModel() {

    val selectedMode = MutableLiveData<Int>().apply { value = SliceView.MODE_LARGE }
    val systemSlices = MutableLiveData<List<SystemPackage>>().apply {
        value = systemDataCache.getAllSlices()
    }

    val systemSlicesLoadingState = MutableLiveData<Boolean>().apply {
        value = false
    }

    val slices
        get() = uriDataSource.getSavedUris()

    fun addSlice(uri: Uri) {
        uriDataSource.saveUri(uri)
    }

    fun removeFromPosition(position: Int) {
        uriDataSource.removeSavedUriFromPosition(position)
    }

    fun refreshSystemSlices() {
        val scannerWork = OneTimeWorkRequestBuilder<SystemScannerWorker>().build()
        workManager.enqueue(scannerWork)
        systemSlicesLoadingState.value = true
        workManager.getStatusById(scannerWork.id).map {
            if (it != null && it.state.isFinished) {
                systemSlicesLoadingState.value = false
                systemSlices.value = systemDataCache.getAllSlices()
            }
        }
    }
}

fun <X, Y> LiveData<X>.map(body: (X) -> Y): LiveData<Y> {
    return Transformations.map(this, body)
}
