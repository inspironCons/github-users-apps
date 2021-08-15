package com.dicoding.githubusersapp.ui.detailuser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.dicoding.githubusersapp.databinding.ActivityDetailBinding
import com.dicoding.githubusersapp.model.Users
import com.dicoding.githubusersapp.ui.home.HomeActivity
import io.alterac.blurkit.BlurLayout


class DetailActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailBinding
    private lateinit var blurLayout:BlurLayout
    private lateinit var data:Users
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //binding blur
        blurLayout = binding.blurLayout

        data = intent.getParcelableExtra(HomeActivity.EXTRA_DATA)!!
        showData()
        onBackNav()
    }

    override fun onStart() {
        super.onStart()
        blurLayout.startBlur()
    }

    override fun onStop() {
        blurLayout.pauseBlur()
        super.onStop()
    }

    private fun showData(){
        binding.imageBg.setImageDrawable(AppCompatResources.getDrawable(this,data.avatar))
        binding.avatarProfile.setImageResource(data.avatar)
        binding.tvUsername.text = data.username
        binding.tvRealName.text = data.name
        binding.tvRepoValue.text = data.repository.toString()
        binding.tvLocationValue.text = data.location
        binding.tvFollowerValue.text = data.follower.toString()
        binding.tvFollowingValue.text = data.following.toString()
        binding.tvCompanyValue.text = data.company
    }

    private fun onBackNav(){
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }
}