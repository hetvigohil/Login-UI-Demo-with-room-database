package com.medicine.demo.viewmodel

import androidx.lifecycle.viewModelScope
import com.medicine.demo.base.BaseViewModel
import com.medicine.demo.data.preferences.PreferenceManager
import com.medicine.demo.data.roomdatabase.userdata.UserDataItem
import kotlinx.coroutines.launch

class AuthenticationViewModel : BaseViewModel() {

    var isUserExist: ((isExist: Boolean) -> Unit)? = null
    var isLogin: ((state: Int) -> Unit)? = null

    companion object {
        const val USER_NOT_EXIST = 2
        const val PASSWORD_WRONG = 3
        const val SUCCESS = 1
    }

    /**
     * Method used to store user data in local database
     */
    fun insertUserDetailInLocalDatabase(
        userName: String,
        firstName: String,
        lastName: String,
        password: String
    ) {
        val userDetail = UserDataItem()
        userDetail.userName = userName
        userDetail.userPassword = password
        userDetail.firstName = firstName
        userDetail.lastName = lastName
        viewModelScope.launch {
            if (getDatabase().getUserDetailDao().isUserNameExist(userName) == null) {
                getDatabase().getUserDetailDao().insertUserDetail(userDetail)
                isUserExist?.invoke(false)
            } else
                isUserExist?.invoke(true)
        }
    }


    fun checkUserLogin(
        userName: String,
        password: String
    ) {
        viewModelScope.launch {
            val item = getDatabase().getUserDetailDao().isUserNameExist(userName)
            when {
                item == null -> isLogin?.invoke(USER_NOT_EXIST)
                item.userPassword.equals(password).not() -> isLogin?.invoke(PASSWORD_WRONG)
                else -> {
                    getPreference().putString(PreferenceManager.USER_NAME, userName)
                    getPreference().putString(PreferenceManager.PASSWORD, password)
                    isLogin?.invoke(SUCCESS)
                }
            }
        }
    }

    fun isLogin(): Boolean {
        return getPreference().getString(PreferenceManager.USER_NAME, "")
            .isNotEmpty() && getPreference().getString(PreferenceManager.PASSWORD, "").isNotEmpty()
    }
}