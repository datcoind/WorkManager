package com.example.workmanager

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.workmanager.databinding.ActivityMainBinding
import com.example.workmanager.utils.RESULT
import com.example.workmanager.utils.SUMMATION
import com.example.workmanager.viewmodels.CalcViewModel
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding

    private val viewModel by lazy {
        ViewModelProvider(this)[CalcViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        with(mBinding) {
            btnCancel.setOnClickListener {
                viewModel.cancelWork()
            }

            btnHandling.setOnClickListener {
                val numA = abs(edtNumA.text.toString().toInt())
                val numB = abs(edtNumB.text.toString().toInt())
                viewModel.add(numA, numB)

            }

            btnSeeResult.setOnClickListener {
                val intent = Intent(this@MainActivity, ResultActivity::class.java)
                intent.putExtra(RESULT, viewModel.result)
                startActivity(intent)
            }
        }

        viewModel.outputWorkInfo.observe(this) { listOfWorkInfo ->
            if (listOfWorkInfo.isNullOrEmpty()) {
                return@observe
            }
            val workInfo = listOfWorkInfo[0]

            if (workInfo.state.isFinished) {
                showWorkFinished()

                val result = workInfo.outputData.getInt(SUMMATION, -1)

                if (result != -1) {
                    mBinding.btnSeeResult.visibility = View.VISIBLE
                    viewModel.result = result
                }

            } else {
                showWorkInProgress()
            }
        }
    }

    private fun showWorkInProgress() {
        with(mBinding) {
            btnHandling.visibility = View.GONE
            btnCancel.visibility = View.VISIBLE
            progressBar.visibility = View.VISIBLE
            btnSeeResult.visibility = View.GONE
        }
    }

    private fun showWorkFinished() {
        with(mBinding) {
            btnHandling.visibility = View.VISIBLE
            btnCancel.visibility = View.GONE
            progressBar.visibility = View.GONE
        }
    }
}