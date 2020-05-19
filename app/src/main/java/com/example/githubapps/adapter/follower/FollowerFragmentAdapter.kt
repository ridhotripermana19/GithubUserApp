package com.example.githubapps.adapter.follower

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapps.model.user.UserResponse
import com.example.githubapps.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_follower.view.*

class FollowerFragmentAdapter(private val context: Context) : RecyclerView.Adapter<FollowerFragmentAdapter.ViewHolder>() {

    private var listFollower: List<UserResponse> = ArrayList()

    fun setFollowerList(listFollower: List<UserResponse>) {
        this.listFollower = listFollower
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_follower, parent, false)
        )
    }

    override fun getItemCount(): Int = listFollower.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listFollower[position])
        holder.itemView.setOnClickListener {
            val data = listFollower[position]
            Toast.makeText(context, "Kamu Mengklik ${data.login}", Toast.LENGTH_SHORT).show()
        }
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun bind(userResponse: UserResponse) {
            with(itemView) {
                user_name_follower.text = userResponse.login
                Picasso.get()
                    .load(userResponse.avatarUrl)
                    .into(user_image_follower)
            }
        }
    }
}