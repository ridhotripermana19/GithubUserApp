package com.example.githubapps.viewModel.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.githubapps.repository.details.DetailsActivityRepository
import com.example.githubapps.model.user.UserResponse

class DetailsActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val repository =
        DetailsActivityRepository(
            application
        )
    val showProgress : LiveData<Boolean>
    val detailList : LiveData<UserResponse>
    val valueList: LiveData<UserResponse>

    init {
        this.showProgress = repository.showProgress
        this.detailList = repository.detailList
        this.valueList = repository.valueList
    }

    fun getDetails(username: String) {
        repository.getDetails(username)
    }
}