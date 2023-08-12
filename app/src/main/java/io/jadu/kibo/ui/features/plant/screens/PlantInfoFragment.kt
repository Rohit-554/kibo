package io.jadu.kibo.ui.features.plant.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import io.jadu.kibo.R
import io.jadu.kibo.databinding.FragmentPlantInfoBinding
import io.jadu.kibo.ui.features.plant.adapter.PlantAdapter
import io.jadu.kibo.ui.features.plant.viewModel.PlantsInfoViewModel

@AndroidEntryPoint
class PlantInfoFragment : Fragment() {
    private lateinit var binding: FragmentPlantInfoBinding
    private lateinit var mRecyclerView: RecyclerView
    private val plantsInfoViewModel: PlantsInfoViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlantInfoBinding.inflate(inflater,container,false)
        mRecyclerView = binding.plantInfoRecyclerView
        mRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        plantsInfoViewModel.plantsLiveData.observe(viewLifecycleOwner){plants->
            Log.d("plants", "onCreateView: $plants")
            val plantAdapter = PlantAdapter()
            plantAdapter.plantList = plants
            mRecyclerView.adapter = plantAdapter
            plantAdapter.notifyDataSetChanged()
        }

        return binding.root
    }

}