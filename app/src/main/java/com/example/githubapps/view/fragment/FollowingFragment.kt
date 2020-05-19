package com.example.githubapps.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.githubapps.R
import com.example.githubapps.utils.SharedPreference
import com.example.githubapps.adapter.following.FollowingFragmentAdapter
import com.example.githubapps.viewModel.following.FollowingViewModel
import kotlinx.android.synthetic.main.fragment_following.*

class FollowingFragment : Fragment() {

    private lateinit var viewModel : FollowingViewModel
    private lateinit var adapter : FollowingFragmentAdapter

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        viewModel = ViewModelProvider(this).get(FollowingViewModel::class.java)
        val args = arguments
        val side = args?.getString(SharedPreference.EXTRA_KEY)
        if (side != null) {
            viewModel.getFollowing(side)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(FollowingViewModel::class.java)

        viewModel.showProgress.observe(viewLifecycleOwner, Observer {
            if(it)
                following_progress.visibility = View.VISIBLE
            else
                following_progress.visibility = View.GONE
        })

        viewModel.followerList.observe(viewLifecycleOwner, Observer {
            adapter.setFollowingList(it)
        })

        adapter = FollowingFragmentAdapter()
        rv_following.adapter = adapter
    }
}