package com.medicine.demo.base

import androidx.lifecycle.ViewModel
import com.medicine.demo.data.datamanager.DataManager
import com.medicine.demo.data.preferences.IPreference
import com.medicine.demo.data.remote.ApiService
import com.medicine.demo.data.roomdatabase.AppDatabase

open class BaseViewModel : ViewModel() {

    fun getPreference(): IPreference {
        return DataManager.getInstance().getPreference()
    }

    fun getDatabase(): AppDatabase {
        return DataManager.getInstance().getDatabase()
    }

    fun getRemote(): ApiService {
        return DataManager.getInstance().getRemote()
    }
}