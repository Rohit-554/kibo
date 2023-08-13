package io.jadu.kibo.ui.features.community.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import io.jadu.kibo.R
import io.jadu.kibo.databinding.FragmentKiboCommunityBinding


class KiboCommunity : Fragment() {
    private lateinit var binding: FragmentKiboCommunityBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKiboCommunityBinding.inflate(inflater,container,false)
        binding.kiboCommunityCard.setOnClickListener {
            findNavController().navigate(R.id.action_kiboCommunity_to_topKibosCommunity)
        }
        binding.kiboscloseby.setOnClickListener {
            findNavController().navigate(R.id.action_kiboCommunity_to_kibosCloseBy)
        }
        return binding.root
    }

}