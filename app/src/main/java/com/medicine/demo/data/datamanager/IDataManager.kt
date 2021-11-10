package com.medicine.demo.data.datamanager

import com.medicine.demo.data.preferences.IPreference
import com.medicine.demo.data.remote.ApiService
import com.medicine.demo.data.roomdatabase.AppDatabase

interface IDataManager {
    fun getPreference(): IPreference
    fun getDatabase(): AppDatabase
    fun getRemote(): ApiService
}