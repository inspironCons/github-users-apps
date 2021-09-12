package com.dicoding.githubusersapp.ui.detailuser

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.githubusersapp.R
import com.dicoding.githubusersapp.databinding.ActivityDetailBinding
import com.dicoding.githubusersapp.ui.detailuser.tabs.ShareViewModel
import com.dicoding.githubusersapp.ui.detailuser.tabs.TabsAdapter
import com.dicoding.githubusersapp.ui.home.HomeActivity
import com.google.android.material.tabs.TabLayoutMediator
import java.util.concurrent.Executors


class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var shareViewModel: ShareViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
        shareViewModel = ViewModelProvider(this).get(ShareViewModel::class.java)
        getData()
        showData()
        showTabs()
        onBackNav()

        viewModel.catchErrorMessage.observe(this){ error->
            Toast.makeText(this,error,Toast.LENGTH_LONG).show()
        }
    }

    private fun getData() {
        val intent = intent.extras
        if(intent != null){
            val username = intent.getString(HomeActivity.EXTRA_USERNAME) as String
            val excecutors = Executors.newSingleThreadExecutor()
            excecutors.execute{
                viewModel.getDetailUser(username)
            }
        }
    }

    private fun showData() {
        binding.apply {
            viewModel.getDataDetail().observe(this@DetailActivity){ detail->
                if(detail != null){
                    Glide.with(this@DetailActivity)
                        .load(detail.avatar)
                        .into(avatarProfile)
                    tvUsername.text = detail.username
                    tvRealName.text = detail.name
                    tvRepoValue.text = detail.repository.toString()
                    tvLocationValue.text = detail.location
                    tvCompanyValue.text = detail.company
                    shareViewModel.sendExtra(detail.username,detail.follower,detail.following)
                }
            }
        }
    }

    private fun showTabs(){
        val tabsAdapter = TabsAdapter(this)
        binding.viewFollows.adapter = tabsAdapter
        binding.apply {
            shareViewModel.follows.observe(this@DetailActivity){ count->
                TabLayoutMediator(tabsFollows,viewFollows){ tab,position->
                    when(position){
                        0->tab.text = resources.getString(R.string.follower,count[0])
                        1->tab.text = resources.getString(R.string.following,count[1])
                    }
                }.attach()
            }
        }
    }

    private fun onBackNav() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }
}