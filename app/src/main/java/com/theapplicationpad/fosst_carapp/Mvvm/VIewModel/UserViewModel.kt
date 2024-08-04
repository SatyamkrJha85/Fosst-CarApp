package com.theapplicationpad.fosst_carapp.Mvvm.VIewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theapplicationpad.fosst_carapp.Mvvm.Model.RoomModel.User
import com.theapplicationpad.fosst_carapp.Mvvm.PreferenceDatabase.UserDao
import com.theapplicationpad.fosst_carapp.Mvvm.PreferenceDatabase.UserPreferences
import kotlinx.coroutines.launch

class UserViewModel(private val userDao: UserDao, private val userPreferences: UserPreferences) : ViewModel() {
    fun insertUser(username: String, password: String, email: String, profilePic: String) {
        viewModelScope.launch {
            val user = User(username = username, password = password, email = email, profilePic = profilePic)
            userDao.insertUser(user)
            userPreferences.saveUser(username, email, profilePic)
        }
    }

    suspend fun getUser(username: String, password: String): User? {
        return userDao.getUser(username, password)
    }

    val isLoggedIn = userPreferences.isLoggedIn
    val userName = userPreferences.userName
    val userEmail = userPreferences.userEmail
    val userProfilePic = userPreferences.userProfilePic

    fun clearUser() {
        viewModelScope.launch {
            userPreferences.clearUser()
        }
    }
}
