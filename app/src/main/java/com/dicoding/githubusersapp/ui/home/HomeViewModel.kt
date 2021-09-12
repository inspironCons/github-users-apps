package com.dicoding.githubusersapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubusersapp.model.Users
import com.dicoding.githubusersapp.network.ServiceBuilder
import com.dicoding.githubusersapp.network.users.IUsers
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException

class HomeViewModel:ViewModel() {
    private val listUsers = MutableLiveData<ArrayList<Users>>()
    val catchErrorMessage = MutableLiveData<String>()
    private val request = ServiceBuilder.buildService(IUsers::class.java)
    fun getUsers(search:String?) = runBlocking{
        try {
            if (search != null) {
                val getData = request.searchUser(search)
                val data = getData.users
                val listItem = ArrayList<Users>()
                for(i in data.indices){
                    val users = Users()
                    users.username = data[i].login
                    users.avatar = data[i].avatarUrl
                    listItem.add(users)
                }
                listUsers.postValue(listItem)
            }
        }catch (e:HttpException){
            catchErrorMessage.postValue("${e.message()} - ${e.code()}")
        }catch (e:Exception){
            catchErrorMessage.postValue(e.localizedMessage)
        }
    }
    fun getUsersList():LiveData<ArrayList<Users>>{
        return listUsers
    }
}