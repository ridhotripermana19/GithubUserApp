package com.example.githubapps.model.favorite

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteModel(
    var id: Int = 0,
    var name: String? = null,
    var fullname: String? = null,
    var image: String? = null,
    var location: String? = null,
    var company: String? = null
) : Parcelable