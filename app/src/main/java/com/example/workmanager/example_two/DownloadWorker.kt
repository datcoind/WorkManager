package com.example.workmanager.example_two

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class DownloadWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    // Define the parameter keys:
    private val KEY_X_ARG = "X"
    private val KEY_Y_ARG = "Y"
    private val KEY_Z_ARG = "Z"

    // The result key:
    private val KEY_RESULT = "result"

    override fun doWork(): Result {
        // Thực hiện công việc trong method này.

        // Fetch the arguments (and specify default values):
        val x = inputData.getLong(KEY_X_ARG, 0)
        val y = inputData.getLong(KEY_Y_ARG, 0)
        val z = inputData.getLong(KEY_Z_ARG, 0)

        val timeToSleep = x + y + z
        Thread.sleep(timeToSleep)

        //...set the output, and we're done!
        val output = Data.Builder()
            .putInt(KEY_RESULT, timeToSleep.toInt())
            .build()

        // Indicate success or failure with your return value.

        // Việc trả về Result.success() để chỉ ra rằng task đã thực hiện complete và success.
        // Việc trả về Result.retry() để nói với WorkManager retry work một lần nữa.
        // Việc trả về Result.failure() chỉ ra rằng một hoặc nhều lỗi sảy ra.
        return Result.success(output)
    }
}