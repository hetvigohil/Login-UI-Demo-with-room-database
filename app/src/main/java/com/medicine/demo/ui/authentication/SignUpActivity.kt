package com.medicine.demo.ui.authentication

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.medicine.demo.R
import com.medicine.demo.base.BaseActivity
import com.medicine.demo.viewmodel.AuthenticationViewModel
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity(R.layout.activity_sign_up), TextWatcher {

    private lateinit var viewModel: AuthenticationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ivBack.setOnClickListener {
            finish()
        }
        edtUserName.addTextChangedListener(this)
        edtFirstName.addTextChangedListener(this)
        edtLastName.addTextChangedListener(this)
        edtPassword.addTextChangedListener(this)
        viewModel = ViewModelProvider(this).get(AuthenticationViewModel::class.java)

        btnSignUp.setOnClickListener {
            Handler().postDelayed({
                if (edtUserName.text.toString().contains(" ").not()) {
                    pbSignUp.visibility = View.VISIBLE
                    btnSignUp.isEnabled = false
                    viewModel.insertUserDetailInLocalDatabase(
                        edtUserName.text.toString(),
                        edtFirstName.text.toString(),
                        edtLastName.text.toString(),
                        edtPassword.text.toString()
                    )
                } else
                    makeToast(getString(R.string.no_space_allow_in_user_name))
            }, 2000)
        }

        viewModel.isUserExist = { isExist ->
            pbSignUp.visibility = View.GONE
            btnSignUp.isEnabled = true
            if (isExist)
                makeToast(getString(R.string.user_already_exist))
            else
                finish()
        }
    }


    override fun afterTextChanged(s: Editable?) {
        edtUserName.text?.let { userName ->
            edtFirstName.text?.let { firstName ->
                edtLastName.text?.let { lastName ->
                    edtPassword.text?.let { password ->
                        btnSignUp.isEnabled =
                            userName.isNotEmpty() && firstName.isNotEmpty() && lastName.isNotEmpty()
                                    && password.isNotEmpty()
                    } ?: kotlin.run { btnSignUp.isEnabled = false }
                } ?: kotlin.run { btnSignUp.isEnabled = false }
            } ?: kotlin.run { btnSignUp.isEnabled = false }
        } ?: kotlin.run { btnSignUp.isEnabled = false }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}