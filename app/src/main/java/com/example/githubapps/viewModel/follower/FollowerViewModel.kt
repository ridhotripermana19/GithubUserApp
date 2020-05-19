package com.example.githubapps.viewModel.follower

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.githubapps.repository.follower.FollowerFragmentRepository
import com.example.githubapps.model.user.UserResponse

class FollowerViewModel(application: Application) : AndroidViewModel(application) {

    private val repository =
        FollowerFragmentRepository(
            application
        )
    val showProgress : LiveData<Boolean>
    val followerList : LiveData<List<UserResponse>>

    init {
        this.followerList = repository.followerList
        this.showProgress = repository.showProgress
    }

    fun getfollowers(follower : String) {
        repository.getFollowers(follower)
    }
}