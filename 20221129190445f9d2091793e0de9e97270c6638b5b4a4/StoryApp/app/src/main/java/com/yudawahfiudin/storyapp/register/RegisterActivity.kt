package com.yudawahfiudin.storyapp.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.yudawahfiudin.storyapp.R
import com.yudawahfiudin.storyapp.data.Resources
import com.yudawahfiudin.storyapp.databinding.ActivityRegisterBinding
import com.yudawahfiudin.storyapp.login.LoginActivity
import com.yudawahfiudin.storyapp.model.ViewModelFactory
import com.yudawahfiudin.storyapp.preference.UserPreference

class RegisterActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        setupViewModel()
        setupView()
        setupAction()
        moveToLogin()
        setAnimation()
    }

    private fun setupViewModel() {
        val pref = UserPreference.getInstance(dataStore)
        registerViewModel = ViewModelProvider(this, ViewModelFactory(pref))[RegisterViewModel::class.java]
    }

    private fun setupView() {
        registerViewModel.userInfo.observe(this) {
            when (it) {
                is Resources.Success -> {
                    showLoad(false)
                    Toast.makeText(this, it.data, Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LoginActivity::class.java))
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

    private fun setupAction() {
        binding.registerButton.setOnClickListener{
            if (valid()) {
                val name = binding.edRegisterName.text.toString()
                val email = binding.edRegisterEmail.text.toString()
                val password = binding.edRegisterPassword.text.toString()
                registerViewModel.register(name, email, password)
            } else {
                Toast.makeText(
                    this,
                    resources.getString(R.string.check_input),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun valid() =
        binding.edRegisterEmail.error == null && binding.edRegisterPassword.error == null && binding.edRegisterName.error == null && !binding.edRegisterEmail.text.isNullOrEmpty() && !binding.edRegisterPassword.text.isNullOrEmpty() && !binding.edRegisterName.text.isNullOrEmpty()


    private fun setAnimation() {
        val appIcon = ObjectAnimator.ofFloat(binding.imageView, View.ALPHA, 1f).setDuration(700)
        val tvName = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(700)
        val etName = ObjectAnimator.ofFloat(binding.edRegisterName, View.ALPHA, 1f).setDuration(700)
        val tvEmail = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(700)
        val etEmail = ObjectAnimator.ofFloat(binding.edRegisterEmail, View.ALPHA, 1f).setDuration(700)
        val tvPass = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(700)
        val etPass = ObjectAnimator.ofFloat(binding.edRegisterPassword, View.ALPHA, 1f).setDuration(700)
        val btnLogin = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(700)
        val btnRegister = ObjectAnimator.ofFloat(binding.registerButton, View.ALPHA, 1f).setDuration(700)
        val tvHaveAcc = ObjectAnimator.ofFloat(binding.textViewHaveAccount, View.ALPHA, 1f).setDuration(700)
        val crYuda = ObjectAnimator.ofFloat(binding.copyrightTextView, View.ALPHA, 1f).setDuration(700)

        val textAnim = AnimatorSet().apply {
            playTogether(tvName, tvEmail, tvPass)
        }
        val layoutAnim = AnimatorSet().apply {
            playTogether(etName, etEmail, etPass)
        }

        AnimatorSet().apply {
            playSequentially(
                appIcon,
                textAnim,
                layoutAnim,
                btnRegister,
                tvHaveAcc,
                btnLogin,
                crYuda


            )
            start()
        }
    }

    private fun moveToLogin() {
        binding.apply {
            loginButton.setOnClickListener {
                Intent(this@RegisterActivity, LoginActivity::class.java).also { intent ->
                    startActivity(intent)
                }
            }
        }
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