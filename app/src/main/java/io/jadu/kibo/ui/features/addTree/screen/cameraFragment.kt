package io.jadu.kibo.ui.features.addTree.screen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import io.jadu.kibo.R
import io.jadu.kibo.databinding.FragmentCameraBinding


class cameraFragment : Fragment() {
    private lateinit var binding:FragmentCameraBinding
    private lateinit var dropDownArrayAdapter: ArrayAdapter<String>
    val REQUEST_IMAGE_CAPTURE = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCameraBinding.inflate(inflater,container,false)
        return binding.root
    }

    private fun openCamera(){
        dispatchTakePictureIntent()
    }

    private fun setupAvailabilityFilters() {
        binding.availabilitySelector.onItemClickListener =
            AdapterView.OnItemClickListener() { _: AdapterView<*>, _: View, position: Int, _: Long ->
                val goalValues:List<String> = listOf("Available | Hey Let Us Connect", "Away | Stay Discreet ", "Not Available | Busy", "SOS| Need Help")
                val selectedValue = goalValues[position]
                binding.availabilitySelector.setText(selectedValue,false)
            }
    }

    private fun dispatchTakePictureIntent(){
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            resultLauncher.launch(takePictureIntent)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    var resultLauncher = requireActivity().registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            Log.d("camera", "dispatchTakePictureIntent: ${data?.extras?.get("data")}")
        }
    }

    override fun onResume() {
        super.onResume()
        dropDownArrayAdapter = ArrayAdapter(
            requireContext(),
            R.layout.availability_selector,
            listOf("Mango", "Banyan", "Guava", "Oak","Ashoka")
        )
        binding.availabilitySelector.setAdapter(dropDownArrayAdapter)

    }



}