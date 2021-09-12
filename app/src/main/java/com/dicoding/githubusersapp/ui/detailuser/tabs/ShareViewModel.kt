package com.dicoding.githubusersapp.ui.detailuser.tabs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubusersapp.model.ListFollows
import com.dicoding.githubusersapp.network.ServiceBuilder
import com.dicoding.githubusersapp.network.users.IUsers
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException

class ShareViewModel:ViewModel() {
    val username = MutableLiveData<String>()
    val follows = MutableLiveData<List<Int>>()
    val catchErrorMessage = MutableLiveData<String>()
    private val listFollowing = MutableLiveData<ArrayList<ListFollows>>()
    private val listFollowers = MutableLiveData<ArrayList<ListFollows>>()

    private val request = ServiceBuilder.buildService(IUsers::class.java)

    fun sendExtra(user:String?,followers:Int?,followings:Int?){
        username.value = user as String
        follows.postValue(listOf(followers as Int,followings as Int))
    }

    fun getListFollowing(username: String) = runBlocking {
        try{
            val user = request.getFollowingUser(username)
            val listItem = ArrayList<ListFollows>()
            for(i in user.indices){
                val userDetail = ListFollows()
                userDetail.username = user[i].login
                userDetail.avatar = user[i].avatarUrl
                listItem.add(userDetail)
            }
            listFollowing.postValue(listItem)
        }catch (e:HttpException){
            catchErrorMessage.postValue("${e.message()} - ${e.code()}")
        }catch (e:Exception){
            catchErrorMessage.postValue(e.localizedMessage)
        }
    }
    fun showListFollowing():LiveData<ArrayList<ListFollows>>{
        return listFollowing
    }

    fun getListFollowers(username: String) = runBlocking {
        try{
            val user = request.getFolloweUser(username)
            val listItem = ArrayList<ListFollows>()
            for(i in user.indices){
                val userDetail = ListFollows()
                userDetail.username = user[i].login
                userDetail.avatar = user[i].avatarUrl
                listItem.add(userDetail)
            }
            listFollowers.postValue(listItem)
        }catch (e:HttpException){
            catchErrorMessage.postValue("${e.message()} - ${e.code()}")
        }catch (e:Exception){
            catchErrorMessage.postValue(e.localizedMessage)
        }
    }
    fun showListFollowers():LiveData<ArrayList<ListFollows>>{
        return listFollowers
    }
}