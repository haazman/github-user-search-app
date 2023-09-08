package com.haidar.githubusersearch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.haidar.githubusersearch.data.response.ResponseFollow
import com.haidar.githubusersearch.databinding.FragmentFollowersBinding
import com.haidar.githubusersearch.model.DetailViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [FollowersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowersFragment() : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentFollowersBinding? = null;
    private val binding get() = _binding!!;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowersBinding.inflate(inflater, container, false);
        val view = binding.root
        showLoading(false)
        val detailViewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)
        detailViewModel.findFollower(arguments?.getString("username")!!)
        detailViewModel.follower.observe(viewLifecycleOwner) { github ->
            setGithubData(github)
        }
        detailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        val layoutManager = LinearLayoutManager(context)
        binding.rvFollowers.layoutManager = layoutManager
        return view;

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.rvFollowers.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.rvFollowers.visibility = View.VISIBLE
        }
    }

    private fun setGithubData(response: List<ResponseFollow>?) {
        val adapter = FollowListAdapter()
        adapter.submitList(response)
        binding.rvFollowers.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FollowersFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FollowersFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}