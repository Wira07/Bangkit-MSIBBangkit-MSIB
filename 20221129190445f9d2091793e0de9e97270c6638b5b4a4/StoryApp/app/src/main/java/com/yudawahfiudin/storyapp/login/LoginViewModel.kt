package com.yudawahfiudin.storyapp.login

import android.util.Log
import androidx.lifecycle.*
import com.google.gson.Gson
import com.yudawahfiudin.storyapp.data.Resources
import com.yudawahfiudin.storyapp.preference.UserPreference
import com.yudawahfiudin.storyapp.remote.ApiConfig
import com.yudawahfiudin.storyapp.remote.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel(private val pref: UserPreference) : ViewModel() {

    private val _userInfo = MutableLiveData<Resources<String>>()
    val userInfo: LiveData<Resources<String>> = _userInfo
    
    fun login(email: String, password: String) {
        _userInfo.postValue(Resources.Loading())
        val client = ApiConfig.getApiService().login(email, password)

        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val result = response.body()?.loginResult?.token

                    result?.let { saveUserToken(it) }
                    saveUserSession(true)
                    _userInfo.postValue(Resources.Success(result))
                } else {
                    val errorResponse = Gson().fromJson(
                        response.errorBody()?.charStream(),
                        LoginResponse::class.java
                    )
                    _userInfo.postValue(Resources.Error(errorResponse.message))
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e(
                    LoginViewModel::class.java.simpleName,
                    "onFailure login"
                )
                _userInfo.postValue(Resources.Error(t.message))
            }
        })
    }

    fun saveUserToken(token: String) {
        viewModelScope.launch {
            pref.saveUserToken(token)
        }
    }

    fun saveUserSession(isLogin: Boolean) {
        viewModelScope.launch {
            pref.saveUserIsLogin(isLogin)
        }
    }
}