//301237966

package com.damien.liscio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.damien.liscio.ui.theme.DamienLiscio_COMP304_FinalTest_F24Theme
import com.damien.liscio.views.InsertStockScreen
import com.damien.liscio.workers.StockSyncWorker

class DamienActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startStockSync()
        enableEdgeToEdge()
        setContent {
            DamienLiscio_COMP304_FinalTest_F24Theme {
                Scaffold(
                    content = { padding ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding),
                        ) {
                            InsertStockScreen(
                                modifier = Modifier
                            )
                        }
                    }
                )
            }
        }
    }
    private fun startStockSync() {
        val syncStockWorkRequest = OneTimeWorkRequestBuilder<StockSyncWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresBatteryNotLow(true)
                    .build()
            )
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniqueWork(
            "StockSyncWorker",
            ExistingWorkPolicy.KEEP,
            syncStockWorkRequest
        )
    }
}

