package com.example.githubapps.repository.following

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.githubapps.R
import com.example.githubapps.model.user.UserResponse
import com.example.githubapps.network.ClientNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class FollowingFragmentRepository(private val application: Application) {

    val showProgress = MutableLiveData<Boolean>()
    val followerList = MutableLiveData<List<UserResponse>>()

    fun getFollowing(following : String) {
        showProgress.value = true
        val retrofit = Retrofit.Builder().baseUrl(ClientNetwork.BASE_URL).addConverterFactory(
            GsonConverterFactory.create()).build()
        val service = retrofit.create(ClientNetwork::class.java)

        service.getFollowing(following).enqueue(object : Callback<List<UserResponse>> {
            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                showProgress.value = false
                Toast.makeText(application, " ${R.string.error} \n + ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<UserResponse>>, response: Response<List<UserResponse>>) {
                showProgress.value = false
                followerList.postValue(response.body())
            }

        })

    }
}