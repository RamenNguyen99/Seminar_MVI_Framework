package com.example.demo_mvi_framework.ui.main.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.demo_mvi_framework.data.model.User
import com.example.demo_mvi_framework.databinding.ActivityUserDetailBinding

class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = intent.extras?.getSerializable("USER") as User
        Log.i("TAG", "UserDetailActivity onCreate: $user")
        initView(user)
    }

    private fun initView(user: User) {
        binding.run {
            Glide.with(imgAvatar.context).load(user.avatar).into(imgAvatar)
            tvUserName.text = user.name
            tvEmail.text = user.email
        }
    }
}
