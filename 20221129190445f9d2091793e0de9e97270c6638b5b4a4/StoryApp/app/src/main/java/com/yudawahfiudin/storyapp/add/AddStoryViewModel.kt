package com.yudawahfiudin.storyapp.add

import androidx.lifecycle.ViewModel
import com.yudawahfiudin.storyapp.preference.UserPreference
import kotlinx.coroutines.flow.first

class AddStoryViewModel(private val pref: UserPreference) : ViewModel() {

    suspend fun userToken() = pref.getUserToken().first()

}