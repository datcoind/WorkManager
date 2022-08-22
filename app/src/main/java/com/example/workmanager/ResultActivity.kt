package com.example.workmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.workmanager.databinding.ActivityResultBinding
import com.example.workmanager.utils.RESULT

class ResultActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_result)

        val result = intent.getIntExtra(RESULT, 0)
        mBinding.txtResult.text = result.toString()
    }
}