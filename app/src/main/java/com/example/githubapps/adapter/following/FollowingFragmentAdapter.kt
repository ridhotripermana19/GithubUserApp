package com.example.githubapps.adapter.following

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapps.R
import com.example.githubapps.model.user.UserResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_following.view.*

class FollowingFragmentAdapter : RecyclerView.Adapter<FollowingFragmentAdapter.ViewHolder>() {

    private var listFollowing: List<UserResponse> = ArrayList()

    fun setFollowingList(listFollowing: List<UserResponse>) {
        this.listFollowing = listFollowing
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_following, parent, false)
        )
    }

    override fun getItemCount(): Int = listFollowing.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(listFollowing[position])
    }

    class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
        fun bind(item: UserResponse) {
            with(itemView) {
                user_name_following.text = item.login
                Picasso.get()
                    .load(item.avatarUrl)
                    .into(user_image_following)
            }
        }
    }
}