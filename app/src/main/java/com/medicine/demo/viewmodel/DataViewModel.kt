package com.medicine.demo.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.medicine.demo.base.BaseViewModel
import com.medicine.demo.base.Result
import com.medicine.demo.data.remote.AssociatedDrug
import kotlinx.coroutines.launch

class DataViewModel : BaseViewModel() {
    var mData = MutableLiveData<Result<ArrayList<AssociatedDrug>>>()
    private lateinit var alFinalData: ArrayList<AssociatedDrug>
    private lateinit var alData: ArrayList<AssociatedDrug>

    fun getData() {
        viewModelScope.launch {
            try {
                val response = getRemote().getData()
                val it = response.asJsonObject
                if (it.has("problems") && it["problems"].asJsonArray.size() > 0) {
                    val data = it["problems"].asJsonArray[0].asJsonObject
                    val dataKey = data.keySet()
                    val parameters: HashMap<String, JsonArray> = HashMap()
                    dataKey.forEach { key ->
                        parameters[key] = data.get(key).asJsonArray
                    }
                    alFinalData = ArrayList()
                    parameters.keys.forEach { htKey ->
                        parameters[htKey]?.let { htDataJson ->
                            alData = ArrayList()
                            for (i in 0 until htDataJson.size()) {
                                val list = parseJson(htDataJson[i].asJsonObject)
                                list.forEach {
                                    it.problems = htKey
                                }
                                alFinalData.addAll(list)
                            }
                        }
                    }
                    Log.e("parse", "")
                }
                mData.value = Result.Success(alFinalData)
            } catch (e: Exception) {
                mData.value = Result.Error(e)
            }
        }
    }

    private fun parseJson(data: JsonObject?): ArrayList<AssociatedDrug> {
        if (data != null) {
            val it = data.keySet()
            it.forEach { key ->
                try {
                    when {
                        data[key] is JsonArray -> {
                            val arry = data.getAsJsonArray(key)
                            val size = arry.size()
                            if (key.contains("associate")) {
                                for (i in 0 until size) {
                                    val item = Gson().fromJson(
                                        arry[i].asJsonObject.toString(),
                                        AssociatedDrug::class.java
                                    )
                                    alData.add(item)
                                }
                            } else {
                                for (i in 0 until size) {
                                    parseJson(arry[i].asJsonObject)
                                }
                            }
                        }
                    }
                } catch (e: Throwable) {
                    try {
                        Log.e("parse", key + ":" + data.get(key).asString)
                    } catch (ee: Exception) {
                    }
                    e.printStackTrace()
                }
            }
        }
        return alData
    }
}