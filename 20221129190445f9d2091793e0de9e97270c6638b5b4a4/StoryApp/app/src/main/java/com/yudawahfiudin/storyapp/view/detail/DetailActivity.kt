package com.yudawahfiudin.storyapp.view.detail

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.yudawahfiudin.storyapp.databinding.ActivityDetailBinding
import com.yudawahfiudin.storyapp.model.StoryModel
import com.yudawahfiudin.storyapp.utils.loadImage

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        getStory()
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

    @SuppressLint("SetTextI18n")
    private fun getStory() {
        binding.apply {
            val story = intent?.extras?.getParcelable<StoryModel>(DATA_STORY)

            imgPoster.loadImage(story?.photoUrl.toString())
            tvName.text = story?.name.toString()
            tvDescUser.text = story?.description.toString()
        }
    }

    companion object {
        const val DATA_STORY = "data_story"
    }
}