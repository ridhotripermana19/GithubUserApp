package com.example.githubapps.viewModel.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.githubapps.repository.search.SearchActivityRepository
import com.example.githubapps.model.user.UserItem

class SearchActivityViewModel(application: Application): AndroidViewModel(application) {

    private val repository  =
        SearchActivityRepository(
            application
        )
    val showProgress : LiveData<Boolean>
    val userList : LiveData<List<UserItem>>

    init {
        this.showProgress = repository.showProgress
        this.userList = repository.userList
    }

    fun searchUser(query: String) {
        repository.searchUser(query)
    }
}