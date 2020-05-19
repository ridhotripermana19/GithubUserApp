package com.example.githubapps.view.detail

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.githubapps.R
import com.example.githubapps.adapter.pagerAdapter.SectionsPagerAdapter
import com.example.githubapps.db.DatabaseContract
import com.example.githubapps.db.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.example.githubapps.db.FavoriteHelper
import com.example.githubapps.model.user.UserItem
import com.example.githubapps.utils.SharedPreference
import com.example.githubapps.view.favorite.Favorite
import com.example.githubapps.view.reminder.Reminder
import com.example.githubapps.viewModel.details.DetailsActivityViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*

class Details : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewModel: DetailsActivityViewModel
    private lateinit var favoriteHelper: FavoriteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setTitle(R.string.detail_user)

        val detail = intent.getParcelableExtra<UserItem>(SharedPreference.EXTRA_DETAIL)

        viewModel = ViewModelProvider(this).get(DetailsActivityViewModel::class.java)

        if (detail != null)
            viewModel.getDetails(detail.login)

        viewModel.detailList.observe(this, Observer {
            user_name.text = it.login
            user_full_name.text = it.name
            user_company.text = it.company
            Picasso.get()
                .load(it.avatarUrl)
                .into(user_image)
        })

        fab_add.setOnClickListener(this)

        val data = detail?.login
        val bundle = Bundle()
        bundle.putString(SharedPreference.EXTRA_KEY, data)

        val sectionsPagerAdapter =
            SectionsPagerAdapter(
                this,
                supportFragmentManager,
                bundle
            )
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)
        supportActionBar?.elevation = 0f
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.change_language -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }
            R.id.alarmReminder -> {
                val intent = Intent(this@Details, Reminder::class.java)
                startActivity(intent)
            }
            R.id.favorite -> {
                val intent = Intent(this, Favorite::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(CL_detail, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
        favoriteHelper = FavoriteHelper.getInstance(this)
        viewModel.valueList.observe(this, Observer {
            val values = ContentValues()
            values.put(DatabaseContract.FavoriteColumns.NAME, it.login)
            values.put(DatabaseContract.FavoriteColumns.FULLNAME, it.name)
            values.put(DatabaseContract.FavoriteColumns.IMAGE, it.avatarUrl)
            values.put(DatabaseContract.FavoriteColumns.LOCATION, it.location)
            values.put(DatabaseContract.FavoriteColumns.COMPANY, it.company)

            val check = user_name.text.toString()

            if (favoriteHelper.checkUserName(check)) {
                contentResolver.insert(CONTENT_URI, values)
                val intent = Intent(this@Details, Favorite::class.java)
                startActivity(intent)
            } else {
                showSnackbarMessage(applicationContext.getString(R.string.userExists))
                Handler().postDelayed({
                    startActivity(Intent(this, Favorite::class.java))
                    finish()
                },1200)
            }
        })
    }
}

