package com.example.githubapps.model.user

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserResponse(
    @SerializedName("avatar_url")
    var avatarUrl: String? = null,
    @SerializedName("blog")
    val blog: String? = null,
    @SerializedName("company")
    var company: String? = null,
    @SerializedName("followers")
    val followers: Int = 0,
    @SerializedName("followers_url")
    val followersUrl: String? = null,
    @SerializedName("following")
    val following: Int = 0,
    @SerializedName("following_url")
    val followingUrl: String? = null,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("location")
    var location: String? = null,
    @SerializedName("login")
    var login: String? = null,
    @SerializedName("name")
    var name: String? = null
) : Parcelable