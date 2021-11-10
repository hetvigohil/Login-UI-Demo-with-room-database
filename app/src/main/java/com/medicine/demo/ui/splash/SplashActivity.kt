package com.medicine.demo.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.medicine.demo.ui.authentication.SignInActivity
import com.medicine.demo.ui.dashboard.DashboardActivity
import com.medicine.demo.viewmodel.AuthenticationViewModel


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(this).get(AuthenticationViewModel::class.java)
        if (viewModel.isLogin())
            startActivity(Intent(this, DashboardActivity::class.java))
        else
            startActivity(Intent(this, SignInActivity::class.java))
        finish()
    }
}