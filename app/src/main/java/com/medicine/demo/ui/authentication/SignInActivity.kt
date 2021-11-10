package com.medicine.demo.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.medicine.demo.R
import com.medicine.demo.base.BaseActivity
import com.medicine.demo.ui.dashboard.DashboardActivity
import com.medicine.demo.viewmodel.AuthenticationViewModel
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : BaseActivity(R.layout.activity_sign_in), TextWatcher {

    private lateinit var viewModel: AuthenticationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvSignUpLabel.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        edtUserName.addTextChangedListener(this)
        edtPassword.addTextChangedListener(this)

        viewModel = ViewModelProvider(this).get(AuthenticationViewModel::class.java)
        btnSignIn.setOnClickListener {
            pbSignIn.visibility = View.VISIBLE
            btnSignIn.isEnabled = false
            Handler().postDelayed({
                viewModel.checkUserLogin(edtUserName.text.toString(), edtPassword.text.toString())
            }, 2000)
        }

        viewModel.isLogin = { state ->
            pbSignIn.visibility = View.GONE
            btnSignIn.isEnabled = true
            when (state) {
                AuthenticationViewModel.SUCCESS -> {
                    startActivity(Intent(this, DashboardActivity::class.java))
                    finish()
                }
                AuthenticationViewModel.USER_NOT_EXIST -> {
                    makeToast(getString(R.string.user_not_exist))
                }
                AuthenticationViewModel.PASSWORD_WRONG -> {
                    makeToast(getString(R.string.password_incorrect))
                }
            }
        }
    }

    override fun afterTextChanged(s: Editable?) {
        edtUserName.text?.let { userName ->
            edtPassword.text?.let { password ->
                btnSignIn.isEnabled =
                    userName.isNotEmpty() && userName.isNotEmpty() && password.isNotEmpty()
                            && password.isNotEmpty()
            } ?: kotlin.run { btnSignIn.isEnabled = false }
        } ?: kotlin.run { btnSignIn.isEnabled = false }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}