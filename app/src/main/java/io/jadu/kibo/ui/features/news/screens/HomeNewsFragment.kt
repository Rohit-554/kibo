package io.jadu.kibo.ui.features.news.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import io.jadu.kibo.R
import io.jadu.kibo.databinding.FragmentHomeNewsBinding
import io.jadu.kibo.ui.features.news.viewModel.NewsViewModel

class HomeNewsFragment : Fragment() {
    private lateinit var binding: FragmentHomeNewsBinding
    private val newsViewModel: NewsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeNewsBinding.inflate(inflater,container,false)
        return binding.root
    }

}