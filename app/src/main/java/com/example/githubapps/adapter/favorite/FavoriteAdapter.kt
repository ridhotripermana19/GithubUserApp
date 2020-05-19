package com.example.githubapps.adapter.favorite

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapps.R
import com.example.githubapps.model.favorite.FavoriteModel
import com.example.githubapps.utils.CustomOnItemClickListener
import com.example.githubapps.view.favorite.FavoriteDetails
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cardlist_favorite.view.*

class FavoriteAdapter(private val activity: Activity) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    var listFavorites = ArrayList<FavoriteModel>()
        set(listFavorites) {
            this.listFavorites.clear()
            this.listFavorites.addAll(listFavorites)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cardlist_favorite, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorites[position])
    }

    override fun getItemCount(): Int {
        return this.listFavorites.size
    }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(favoriteModel: FavoriteModel) {
            with(itemView) {
                tv_item_name.text = favoriteModel.name
                Picasso.get().load(favoriteModel.image).into(civ_user_name)
                cv_item_note.setOnClickListener(
                    CustomOnItemClickListener(
                        absoluteAdapterPosition,
                        object :
                            CustomOnItemClickListener.OnItemClickCallback {
                            override fun onItemClicked(view: View, position: Int) {
                                val intent = Intent(activity, FavoriteDetails::class.java)
                                intent.putExtra(FavoriteDetails.EXTRA_POSITION, position)
                                intent.putExtra(FavoriteDetails.EXTRA_FAVORITE, favoriteModel)
                                activity.startActivityForResult(
                                    intent,
                                    FavoriteDetails.REQUEST_DETAIL
                                )
                            }
                        })
                )
            }
        }
    }
}