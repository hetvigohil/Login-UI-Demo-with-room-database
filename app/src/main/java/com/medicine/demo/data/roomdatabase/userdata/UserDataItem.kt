package com.medicine.demo.data.roomdatabase.userdata

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_data")
data class UserDataItem(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "user_id")
        var userId: Int = 0,
        @ColumnInfo(name = "user_name")
        var userName: String = "",
        @ColumnInfo(name = "user_password")
        var userPassword: String = "",
        @ColumnInfo(name = "first_name")
        var firstName: String = "",
        @ColumnInfo(name = "last_name")
        var lastName: String = "")