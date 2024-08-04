package com.theapplicationpad.fosst_carapp.Mvvm.Model.RoomModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val password: String,
    val email: String,
    val profilePic: String
)
