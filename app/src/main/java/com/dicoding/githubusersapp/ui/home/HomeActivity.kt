package com.dicoding.githubusersapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.githubusersapp.R
import com.dicoding.githubusersapp.adapter.MainAdapter
import com.dicoding.githubusersapp.databinding.ActivityHomeBinding
import com.dicoding.githubusersapp.model.Users
import com.dicoding.githubusersapp.ui.detailuser.DetailActivity
import java.util.Calendar
import java.util.concurrent.Executors


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var rvList: RecyclerView
    private lateinit var adapterList: MainAdapter
    private lateinit var homeViewModel: HomeViewModel
    private var toast:Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        homeViewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(HomeViewModel::class.java)

        rvList = binding.rvList
        adapterList = MainAdapter()
        searchUsername()
        whenErrorSearch()
        clickItems()
        showRecycleView()

        binding.tvGreetings.text = greeting()
    }

    private fun searchUsername() {
        binding.searchBarValue.addTextChangedListener { s->
            val search = s.toString()
            adapterList.setItem(emptyList())
            if(search.length >= 5){
                showLoading(true)
                val executor = Executors.newSingleThreadExecutor()
                executor.execute {
                    homeViewModel.getUsers(search)
                }
            }else{
                showEmptyState(true)
            }
        }
    }

    private fun whenErrorSearch() {
        homeViewModel.catchErrorMessage.observe(this){msg->
            toast?.cancel()
            toast = Toast.makeText(this@HomeActivity,msg,Toast.LENGTH_LONG)
            toast?.show()
            showLoading(false)
            showEmptyState(true)
        }
    }

    private fun showRecycleView() {
        rvList.layoutManager = GridLayoutManager(this, 2)
        homeViewModel.getUsersList().observe(this){ users->
            showEmptyState(false)
            if(users.isNotEmpty()){
                adapterList.setItem(users)
            }else{
                showEmptyState(true)
            }
            showLoading(false)
        }
        rvList.adapter = adapterList
    }

    private fun clickItems() {
        adapterList.setOnItemCallback(object : MainAdapter.OnItemCallback {
            override fun onItemClicked(user: Users) {
                val mIntent = Intent(this@HomeActivity, DetailActivity::class.java)
                mIntent.putExtra(EXTRA_USERNAME,user.username)
                startActivity(mIntent)
            }
        })
    }

    private fun greeting(): String {
        val calendar: Calendar = Calendar.getInstance()
        return when (calendar.get(Calendar.HOUR_OF_DAY)) {
            in 0..11 -> resources.getString(R.string.good_morning_reviewer)
            in 12..15 -> resources.getString(R.string.good_afternoon_reviewer)
            in 16..20 -> resources.getString(R.string.good_evening_reviewer)
            else -> resources.getString(R.string.good_night_reviewer)
        }
    }

    private fun showEmptyState(state:Boolean){
        if(state){
            binding.viewEmptyState?.visibility = View.VISIBLE
        }else{
            binding.viewEmptyState?.visibility = View.GONE
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.loader.visibility = View.VISIBLE
        } else {
            binding.loader.visibility = View.GONE
        }
    }

    companion object{
        const val EXTRA_USERNAME = "EXTRA_USERNAME"
    }

}