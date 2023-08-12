package io.jadu.kibo.ui.features.news.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import io.jadu.kibo.R
import io.jadu.kibo.databinding.FragmentHomeNewsBinding
import io.jadu.kibo.ui.features.news.adapter.NewsAdapter
import io.jadu.kibo.ui.features.news.viewModel.NewsViewModel

@AndroidEntryPoint
class HomeNewsFragment : Fragment() {
    private lateinit var binding: FragmentHomeNewsBinding
    private val newsViewModel: NewsViewModel by viewModels()
    private lateinit var mRecyclerView:RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeNewsBinding.inflate(inflater,container,false)
        mRecyclerView = binding.newsRecyclerView
        mRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        newsViewModel.newsLiveData.observe(viewLifecycleOwner){news->
            Log.d("newsc", "onCreateView: $news")
            val newsAdapter = NewsAdapter()
            newsAdapter.newsList = news
            mRecyclerView.adapter = newsAdapter
            newsAdapter.notifyDataSetChanged()
        }
        return binding.root
    }

}