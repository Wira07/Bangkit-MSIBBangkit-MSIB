package com.yudawahfiudin.storyapp.view.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.yudawahfiudin.storyapp.preference.UserPreference
import kotlinx.coroutines.flow.first

class MainViewModel(private val pref: UserPreference) : ViewModel() {

    suspend fun getUserToken() = pref.getUserToken().first()
    suspend fun removeUserToken() = pref.removeUserToken()
    suspend fun removeUserIsLogin() = pref.removeUserIsLogin()
    fun getUserIsLogin() = pref.getUserIsLogin().asLiveData()
}