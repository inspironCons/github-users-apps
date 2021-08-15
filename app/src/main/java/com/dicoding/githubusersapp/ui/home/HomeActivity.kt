package com.dicoding.githubusersapp.ui.home

import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.githubusersapp.R
import com.dicoding.githubusersapp.adapter.MainAdapter
import com.dicoding.githubusersapp.databinding.ActivityHomeBinding
import com.dicoding.githubusersapp.model.Users
import com.dicoding.githubusersapp.ui.detailuser.DetailActivity
import java.util.Calendar


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var rvList:RecyclerView
    private lateinit var adapterList: MainAdapter
    private lateinit var dataUsername:Array<String>
    private lateinit var dataName:Array<String>
    private lateinit var dataAvatar:TypedArray
    private lateinit var dataCompany:Array<String>
    private lateinit var dataLocation:Array<String>
    private lateinit var dataRepository:IntArray
    private lateinit var dataFollower:IntArray
    private lateinit var dataFollowing:IntArray

    private var users = arrayListOf<Users>()

    companion object{
        const val EXTRA_DATA = "EXTRA_DATA"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar:ActionBar? = supportActionBar
        actionBar?.hide()


        rvList = binding.rvList
        adapterList = MainAdapter()
        prepareItem()
        addItem()
        clickItems()
        showRecycleView()

        //dynamic greeting
        binding.tvGreetings.text = greeting()
    }

    private fun showRecycleView() {
        rvList.layoutManager = GridLayoutManager(this,2)
        adapterList.setItem(users)
        rvList.adapter = adapterList
    }

    private fun prepareItem() {
        dataUsername = resources.getStringArray(R.array.username)
        dataName =resources.getStringArray(R.array.name)
        dataAvatar =resources.obtainTypedArray(R.array.avatar)
        dataCompany =resources.getStringArray(R.array.company)
        dataLocation =resources.getStringArray(R.array.location)
        dataRepository =resources.getIntArray(R.array.repository)
        dataFollower =resources.getIntArray(R.array.followers)
        dataFollowing =resources.getIntArray(R.array.following)
    }

    private fun addItem() {
        for(position in dataUsername.indices){
            users.add(
                Users(
                    username = dataUsername[position],
                    name = dataName[position],
                    avatar = dataAvatar.getResourceId(position,-1),
                    company = dataCompany[position],
                    location = dataLocation[position],
                    repository = dataRepository[position],
                    follower = dataFollower[position],
                    following = dataFollowing[position]
                )
            )
        }

    }

    private fun clickItems(){
        adapterList.setOnItemCallback(object : MainAdapter.OnItemCallback {
            override fun onItemClicked(user: Users) {
                val mIntent = Intent(this@HomeActivity,DetailActivity::class.java)
                mIntent.putExtra(EXTRA_DATA,user)
                startActivity(mIntent)
            }
        })
    }

    private fun greeting():String{
        val calendar:Calendar = Calendar.getInstance()
        return when(calendar.get(Calendar.HOUR_OF_DAY)){
            in 0..11->"Good Morning Reviewer"
            in 12..15->"Good Afternoon Reviewer"
            in 16..20->"Good Evening Reviewer"
            else->"Good Night Reviewer"
        }
    }
}