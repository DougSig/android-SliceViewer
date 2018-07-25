package com.example.android.sliceviewer.domain

import androidx.work.Worker
import com.example.android.sliceviewer.util.Injection

class SystemScannerWorker : Worker() {



    override fun doWork(): Result {
        // TODO: migrate to Dagger2.
        val repository = Injection.provideSystemRepository(
            Injection.provideSystemDataCache(
                Injection.provideSharedPrefs(applicationContext)
            ),
            Injection.provideSystemDataSource(applicationContext)
        )
        repository.refreshSlices()
        return Result.SUCCESS
    }
}