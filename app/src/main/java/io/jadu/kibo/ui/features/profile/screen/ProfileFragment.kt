package io.jadu.kibo.ui.features.profile.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import io.jadu.kibo.R
import io.jadu.kibo.data.remote.firestoreUpload.UserData
import io.jadu.kibo.databinding.FragmentProfileBinding
import io.jadu.kibo.ui.features.profile.adapter.UserProfileAdapter


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var userProfileAdapter: UserProfileAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        recyclerView = binding.profileTreesRecycler
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        val db = FirebaseFirestore.getInstance()
        var totalTrees = 0
        var totalPoints = 0
        val email = "abc.com"
        val entriesCollection = db.collection("treesDatabase").document(email).collection("entries")
        entriesCollection.get()
            .addOnSuccessListener { querySnapshot ->
                val userDataList = mutableListOf<UserData>()

                for (document in querySnapshot.documents) {
                    Log.d("datax", "${document.id} => ${document.data}")
                    val userData = document.toObject(UserData::class.java)
                    userData?.let {
                        userDataList.add(it)
                        totalPoints+= it.points
                    }
                    totalTrees = userDataList.size
                    binding.userId.text = email
                    binding.treesText.text = totalTrees.toString()
                    binding.points.text = totalPoints.toString()
                }

                userProfileAdapter = UserProfileAdapter()
                binding.linearLayoutCompat.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                userProfileAdapter.dataList = userDataList
                recyclerView.adapter = userProfileAdapter
                userProfileAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                // Handle failure
            }



        return binding.root
    }
}