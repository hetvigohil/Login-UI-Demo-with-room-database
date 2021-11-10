package com.medicine.demo.data.datamanager

import com.medicine.demo.data.preferences.PreferenceManager
import com.medicine.demo.data.remote.ApiClient
import com.medicine.demo.data.remote.ApiService
import com.medicine.demo.data.roomdatabase.AppDatabase

class DataManager : IDataManager {

    companion object {

        private var SINGLETON_INSTANCE: IDataManager? = null

        fun getInstance(): IDataManager {
            if (SINGLETON_INSTANCE == null) {
                SINGLETON_INSTANCE = DataManager()
            }
            return SINGLETON_INSTANCE!!
        }
    }

    override fun getPreference() = PreferenceManager.getInstance()

    override fun getDatabase(): AppDatabase {
        return AppDatabase.getDatabase()
    }

    override fun getRemote(): ApiService {
        return ApiClient.getInstance("https://api.jsonbin.io/").create(ApiService::class.java)
    }
}