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
import com.example.githubapps.adapter.follower.FollowerFragmentAdapter
import com.example.githubapps.viewModel.follower.FollowerViewModel
import kotlinx.android.synthetic.main.fragment_follower.*

class FollowerFragment : Fragment() {

    private lateinit var viewModel : FollowerViewModel
    private lateinit var adapter : FollowerFragmentAdapter

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        viewModel = ViewModelProvider(this).get(FollowerViewModel::class.java)
        val args = arguments
        val side = args?.getString(SharedPreference.EXTRA_KEY)

        if (side != null) {
            viewModel.getfollowers(side)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(FollowerViewModel::class.java)

        viewModel.showProgress.observe(viewLifecycleOwner, Observer {
            if(it)
                follower_progress.visibility = View.VISIBLE
            else
                follower_progress.visibility = View.GONE
        })

        viewModel.followerList.observe(viewLifecycleOwner, Observer {
            adapter.setFollowerList(it)
        })

        adapter =
            FollowerFragmentAdapter(
                requireContext()
            )
        rv_follower.adapter = adapter
    }
}
