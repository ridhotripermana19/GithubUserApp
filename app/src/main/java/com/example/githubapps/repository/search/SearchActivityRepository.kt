package com.example.githubapps.repository.search

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.githubapps.R
import com.example.githubapps.model.user.User
import com.example.githubapps.model.user.UserItem
import com.example.githubapps.network.ClientNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivityRepository(private val application: Application) {

    val showProgress = MutableLiveData<Boolean>()
    val userList = MutableLiveData<List<UserItem>>()

    fun searchUser(query: String) {
        showProgress.value = true
        /* Network Call */
        val retrofit = Retrofit.Builder().baseUrl(ClientNetwork.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(ClientNetwork::class.java)

        service.searchUsers(query).enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                showProgress.value = false
                Toast.makeText(application, R.string.error_access_api, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                showProgress.value = false
                userList.value = response.body()?.userItems
                if (response.body()?.totalCount == 0)
                    Toast.makeText(application, R.string.user_not_found, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
