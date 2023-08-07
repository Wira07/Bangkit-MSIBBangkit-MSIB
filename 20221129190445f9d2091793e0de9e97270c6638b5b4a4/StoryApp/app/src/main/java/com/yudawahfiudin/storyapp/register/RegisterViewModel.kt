package com.yudawahfiudin.storyapp.register


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.yudawahfiudin.storyapp.data.Resources
import com.yudawahfiudin.storyapp.preference.UserPreference
import com.yudawahfiudin.storyapp.remote.ApiConfig
import com.yudawahfiudin.storyapp.remote.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(private val pref: UserPreference) : ViewModel() {

    private val _userInfo = MutableLiveData<Resources<String>>()
    val userInfo: LiveData<Resources<String>> = _userInfo

    fun register(name: String, email: String, password: String) {
        _userInfo.postValue(Resources.Loading())
        val client = ApiConfig.getApiService().register(name, email, password)

        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    val message = response.body()?.message.toString()
                    _userInfo.postValue(Resources.Success(message))

                } else {
                    val errorResponse = Gson().fromJson(
                        response.errorBody()?.charStream(),
                        RegisterResponse::class.java
                    )
                    _userInfo.postValue(Resources.Error(errorResponse.message))
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e(
                    RegisterViewModel::class.java.simpleName,
                    "onFailure register"
                )
                _userInfo.postValue(Resources.Error(t.message))
            }
        })
    }
}