package com.bluetoolth.cupping.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bluetoolth.cupping.R
import com.bluetoolth.cupping.databinding.ActivitySplashBinding
import com.bluetoolth.cupping.main.MainActivity

/**
 * Created by KimBH on 2023/02/22.
 */
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivitySplashBinding>(this@SplashActivity, R.layout.activity_splash).apply {
            lifecycleOwner = this@SplashActivity
        }

        with(viewModel) {
            isSplashEnd.observe(this@SplashActivity) {
                if (it) {
                    Intent(this@SplashActivity, MainActivity::class.java).run {
                        startActivity(this)
                        finish()
                    }
                }
            }
        }
    }
}