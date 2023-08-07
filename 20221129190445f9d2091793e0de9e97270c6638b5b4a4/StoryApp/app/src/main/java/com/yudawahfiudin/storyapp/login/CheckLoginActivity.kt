package com.yudawahfiudin.storyapp.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.yudawahfiudin.storyapp.databinding.ActivityCheckLoginBinding
import com.yudawahfiudin.storyapp.view.main.MainViewModel
import com.yudawahfiudin.storyapp.model.ViewModelFactory
import com.yudawahfiudin.storyapp.preference.UserPreference
import com.yudawahfiudin.storyapp.view.main.MainActivity


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class CheckLoginActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityCheckLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        setupView()
        onAction()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun onAction() {
        binding.apply {
            mainViewModel.getUserIsLogin().observe(this@CheckLoginActivity) {
                if (it) {
                    Intent(this@CheckLoginActivity, MainActivity::class.java).also { intent ->
                        startActivity(intent)
                        finish()
                    }
                } else {
                    progressBar.alpha = 1f
                    Thread {
                        val handler = Handler(Looper.getMainLooper())
                        var status = 0
                        while (status < 100) {
                            status += 2
                            try {
                                Thread.sleep(50)
                            } catch (e: InterruptedException) {
                                e.printStackTrace()
                            }
                            handler.post {
                                progressBar.progress = status
                                if (status == 100) {
                                    Intent(
                                        this@CheckLoginActivity,
                                        LoginActivity::class.java
                                    ).also { intent ->
                                        startActivity(intent)
                                        finishAffinity()
                                    }
                                }
                            }
                        }
                    }.start()
                }
            }
        }
    }
}