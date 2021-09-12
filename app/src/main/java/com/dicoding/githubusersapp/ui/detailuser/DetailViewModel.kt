package com.dicoding.githubusersapp.ui.detailuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubusersapp.model.UserDetail
import com.dicoding.githubusersapp.network.ServiceBuilder
import com.dicoding.githubusersapp.network.users.IUsers
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException

class DetailViewModel: ViewModel() {
    private val detailUser = MutableLiveData<UserDetail>()
    val catchErrorMessage = MutableLiveData<String>()
    private val request = ServiceBuilder.buildService(IUsers::class.java)

    fun getDetailUser(username:String) = runBlocking {
        try{
            val user = request.getDetailUsername(username)
            val userDetail = UserDetail()
            userDetail.username = user.login
            userDetail.name = user.name
            userDetail.avatar = user.avatarUrl
            userDetail.repository = user.publicRepos
            userDetail.location = user.location
            userDetail.company = user.company
            userDetail.follower = user.followers
            userDetail.following = user.following
            detailUser.postValue(userDetail)
        }catch (e:HttpException){
            catchErrorMessage.postValue("${e.message()} - ${e.code()}")
        }catch (e:Exception){
            catchErrorMessage.postValue(e.localizedMessage)
        }
    }

    fun getDataDetail():LiveData<UserDetail>{
        return detailUser
    }
}