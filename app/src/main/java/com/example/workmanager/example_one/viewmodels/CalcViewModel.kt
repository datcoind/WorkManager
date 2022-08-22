package com.example.workmanager.example_one.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.work.*
import com.example.workmanager.MainApplication
import com.example.workmanager.example_one.utils.NUM_A
import com.example.workmanager.example_one.utils.NUM_B
import com.example.workmanager.example_one.utils.SUMMATION_WORK_NAME
import com.example.workmanager.example_one.utils.TAG_OUTPUT
import com.example.workmanager.example_one.work.SquareWorker
import com.example.workmanager.example_one.work.SummationWorker

class CalcViewModel : ViewModel() {
    internal val outputWorkInfo: LiveData<List<WorkInfo>>
    private val workManager = WorkManager.getInstance(MainApplication.context)
    internal var result: Int = 0

    init {
        outputWorkInfo = workManager.getWorkInfosByTagLiveData(TAG_OUTPUT)
    }

    internal fun add(numA: Int, numB: Int) {
        val dataBuilder = Data.Builder().putInt(NUM_A, numA)
            .putInt(NUM_B, numB).build()

        val numARequest = OneTimeWorkRequestBuilder<SquareWorker>()
            .setInputData(dataBuilder).build()

//        workManager.enqueue(numARequest)
//        var continuation = workManager.beginWith(numARequest)
        var continuation = workManager.beginUniqueWork(
            SUMMATION_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            numARequest
        )

        val summation = OneTimeWorkRequestBuilder<SummationWorker>().addTag(TAG_OUTPUT).build()
        continuation = continuation.then(summation)

        continuation.enqueue()
    }

    internal fun cancelWork() {
        workManager.cancelUniqueWork(SUMMATION_WORK_NAME)
    }


}