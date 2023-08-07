package com.yudawahfiudin.storyapp.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.yudawahfiudin.storyapp.databinding.ActivityLoginBinding
import com.yudawahfiudin.storyapp.R.string
import com.yudawahfiudin.storyapp.data.Resources
import com.yudawahfiudin.storyapp.model.ViewModelFactory
import com.yudawahfiudin.storyapp.preference.UserPreference
import com.yudawahfiudin.storyapp.register.RegisterActivity
import com.yudawahfiudin.storyapp.utils.*
import com.yudawahfiudin.storyapp.view.main.MainActivity


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : AppCompatActivity() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loading: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loading = showAlertLoading(this)

        setupViewModel()
        setupView()
        moveToRegister()
        processedLogin()
        setAnimation()
    }

    private fun setupViewModel() {
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[LoginViewModel::class.java]
    }

    private fun setupView() {

        loginViewModel.userInfo.observe(this) {
            when (it) {
                is Resources.Success -> {
                    showLoad(false)
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                }
                is Resources.Loading -> showLoad(true)
                is Resources.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    showLoad(false)
                }
            }
        }
    }


    private fun processedLogin() {
        binding.loginButton.setOnClickListener {
            if (validate()) {
                val email = binding.edLoginEmail.text.toString()
                val password = binding.edLoginPassword.text.toString()
                loginViewModel.login(email, password)
            } else {
                Toast.makeText(
                    this,
                    resources.getString(string.check_input),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.registerButton.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun validate(): Boolean {
        val valid: Boolean?
        val email = binding.edLoginEmail.text?.trim().toString()
        val password = binding.edLoginPassword.text?.trim().toString()
        when {
            email.isEmpty() -> {
                binding.edLoginEmail.error = getString(string.error_empty_email)
                valid = java.lang.Boolean.FALSE
            }
            password.isEmpty() -> {
                binding.edLoginPassword.error = getString(string.error_empty_password)
                valid = java.lang.Boolean.FALSE
            }
            else -> {
                valid = java.lang.Boolean.TRUE
                binding.edLoginEmail.error = null
                binding.edLoginPassword.error = null
            }
        }
        return valid
    }

    private fun setAnimation() {
        val appIcon = ObjectAnimator.ofFloat(binding.imageView, View.ALPHA, 1f).setDuration(700)
        val tvEmail = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(700)
        val etEmail = ObjectAnimator.ofFloat(binding.edLoginEmail, View.ALPHA, 1f).setDuration(700)
        val tvPass = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(700)
        val etPass = ObjectAnimator.ofFloat(binding.edLoginPassword, View.ALPHA, 1f).setDuration(700)
        val btnLogin = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(700)
        val btnRegister = ObjectAnimator.ofFloat(binding.registerButton, View.ALPHA, 1f).setDuration(700)
        val tvHaveAcc = ObjectAnimator.ofFloat(binding.textViewHaveAccount, View.ALPHA, 1f).setDuration(700)
        val crYuda = ObjectAnimator.ofFloat(binding.copyrightTextView, View.ALPHA, 1f).setDuration(700)

        val textAnim = AnimatorSet().apply {
            playTogether(tvEmail, tvPass)
        }
        val layoutAnim = AnimatorSet().apply {
            playTogether(etPass, etEmail)
        }

        AnimatorSet().apply {
            playSequentially(
                appIcon,
                textAnim,
                layoutAnim,
                btnLogin,
                tvHaveAcc,
                btnRegister,
                crYuda


            )
            start()
        }
    }

    private fun moveToRegister() {
        binding.apply {
            registerButton.setOnClickListener {
                Intent(this@LoginActivity, RegisterActivity::class.java).also { intent ->
                    startActivity(intent)
                }
            }
        }
    }

    override fun onBackPressed() {
        val finish = Intent(Intent.ACTION_MAIN)
        finish.addCategory(Intent.CATEGORY_HOME)
        finish.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(finish)
    }

    private fun showLoad(isLoad: Boolean) {
        if (isLoad){
            binding.progressBar.visibility = View.VISIBLE
        }
        else {
            binding.progressBar.visibility = View.GONE
        }
    }
}