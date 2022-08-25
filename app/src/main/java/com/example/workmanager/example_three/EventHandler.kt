package com.example.workmanager.example_three

import android.content.Context
import android.util.Log
import androidx.work.*
import com.example.workmanager.example_one.utils.NUM_A
import com.example.workmanager.example_one.utils.makeStatusNotification
import java.util.concurrent.TimeUnit

private const val TAG = "EventHandler"

class EventHandler(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        Log.e(TAG, "doWork: ")
        val numA = inputData.getInt(NUM_A, 0)
        makeStatusNotification("Example Three: ${numA + 1}", applicationContext)
        val outputData = workDataOf(NUM_A to numA + 1)
        return Result.success(outputData)
    }

    /**
     * https://stackoverflow.com/questions/50999673/workmanager-not-repeating-the-periodicworkrequest
     * */
    companion object {
        fun oneOffRequest(context: Context) {
            val oneTimeWorkRequest = OneTimeWorkRequestBuilder<EventHandler>()
                .setInitialDelay(10, TimeUnit.SECONDS)
                .setConstraints(setCons())
                .build()
            WorkManager.getInstance(context).enqueue(oneTimeWorkRequest)
        }

        fun periodRequest(context: Context, data: Data) {
            val periodWorkRequest = PeriodicWorkRequestBuilder<EventHandler>(10, TimeUnit.SECONDS)
                .setInputData(data)
                .setInitialDelay(5, TimeUnit.SECONDS)
                .setConstraints(setCons())
                .addTag("periodic")
                .build()
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "periodic",
                ExistingPeriodicWorkPolicy.REPLACE,
                periodWorkRequest
            )
        }

        private fun setCons(): Constraints {
            val constraints = Constraints.Builder()
                .build()
            return constraints
        }
    }

}