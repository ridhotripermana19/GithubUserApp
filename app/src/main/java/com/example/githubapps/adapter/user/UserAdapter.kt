package com.example.githubapps.adapter.user

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapps.R
import com.example.githubapps.utils.SharedPreference
import com.example.githubapps.model.user.UserItem
import com.example.githubapps.view.detail.Details
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cardlist_user.view.*

class UserAdapter(private val context: Context): RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private var list: List<UserItem> = ArrayList()

    fun setUserList(list: List<UserItem>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.cardlist_user, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            val data = list[position]
            val intent = Intent(context, Details::class.java)
            intent.putExtra(SharedPreference.EXTRA_DETAIL, data)
            context.startActivity(intent)
        }
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun bind(userItem : UserItem) {
            with(itemView) {
                txt_user_name.text = userItem.login
                Picasso.get()
                    .load(userItem.avatarUrl)
                    .into(user_image)
            }
        }
    }
}