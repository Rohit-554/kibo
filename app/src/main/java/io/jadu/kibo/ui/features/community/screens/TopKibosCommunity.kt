package io.jadu.kibo.ui.features.community.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.jadu.kibo.R
import io.jadu.kibo.data.remote.models.topkiboscommunity.TopKibbosCommunity
import io.jadu.kibo.databinding.FragmentTopKibosCommunityBinding
import io.jadu.kibo.ui.features.community.adapters.TopKibbosCommunityAdapter

class TopKibosCommunity : Fragment() {
    private lateinit var binding: FragmentTopKibosCommunityBinding
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var topKibosAdapter: TopKibbosCommunityAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding  = FragmentTopKibosCommunityBinding.inflate(inflater,container,false)

        val KibosList:List<TopKibbosCommunity> = listOf(TopKibbosCommunity("Ronak Shaw",getString(R.string.seeds_of_smiles)),
            TopKibbosCommunity("Sai Kishan", getString(R.string.green_reverie)),
            TopKibbosCommunity("John Williams", getString(R.string.earthly_delight)),

        )


        mRecyclerView = binding.topKibosCommunityRecyclerView
        topKibosAdapter = TopKibbosCommunityAdapter()
        mRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        topKibosAdapter.topKibosList = KibosList
        mRecyclerView.adapter = topKibosAdapter
        topKibosAdapter.notifyDataSetChanged()
        return binding.root
    }


}