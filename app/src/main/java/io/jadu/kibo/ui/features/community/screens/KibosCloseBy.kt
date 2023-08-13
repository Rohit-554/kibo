package io.jadu.kibo.ui.features.community.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.jadu.kibo.R
import io.jadu.kibo.databinding.FragmentKibosCloseByBinding


class KibosCloseBy : Fragment() {
    private lateinit var binding: FragmentKibosCloseByBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKibosCloseByBinding.inflate(inflater,container,false)
        return binding.root
    }

}