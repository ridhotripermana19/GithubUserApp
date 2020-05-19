package com.example.githubapps.model.user

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("incomplete_results")
        val incompleteResults: Boolean,
    @SerializedName("items")
        val userItems: List<UserItem>,
    @SerializedName("total_count")
        val totalCount: Int
)