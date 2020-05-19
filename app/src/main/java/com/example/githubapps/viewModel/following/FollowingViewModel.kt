package com.example.githubapps.viewModel.following

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.githubapps.repository.following.FollowingFragmentRepository
import com.example.githubapps.model.user.UserResponse

class FollowingViewModel(application: Application) : AndroidViewModel(application) {

    private val repository =
        FollowingFragmentRepository(
            application
        )
    val showProgress : LiveData<Boolean>
    val followerList : LiveData<List<UserResponse>>

    init {
        this.followerList = repository.followerList
        this.showProgress = repository.showProgress
    }

    fun getFollowing(following : String) {
        repository.getFollowing(following)
    }
}