package com.example.githubapps.view.search

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.githubapps.R
import com.example.githubapps.adapter.user.UserAdapter
import com.example.githubapps.view.favorite.Favorite
import com.example.githubapps.view.reminder.Reminder
import com.example.githubapps.viewModel.search.SearchActivityViewModel
import kotlinx.android.synthetic.main.activity_search.*

class SearchUser : AppCompatActivity() {

    private lateinit var viewModel : SearchActivityViewModel
    private lateinit var adapter: UserAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setTitle(R.string.app_name)

        viewModel = ViewModelProvider(this).get(SearchActivityViewModel::class.java)

        sv_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.searchUser(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        viewModel.showProgress.observe(this, Observer {
            if(it)
                search_progress.visibility = View.VISIBLE
            else
                search_progress.visibility = View.GONE
        })

        viewModel.userList.observe(this, Observer {
            adapter.setUserList(it)
        })
        adapter = UserAdapter(this)
        rv_search.adapter = adapter
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
                val intent = Intent(this, Reminder::class.java)
                startActivity(intent)
            }
            R.id.favorite -> {
                val intent = Intent(this, Favorite::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}