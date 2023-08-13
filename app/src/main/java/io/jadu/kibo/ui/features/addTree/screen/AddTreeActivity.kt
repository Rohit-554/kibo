package io.jadu.kibo.ui.features.addTree.screen

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import dagger.hilt.android.AndroidEntryPoint
import io.jadu.kibo.R
import io.jadu.kibo.data.local.TreeInfo
import io.jadu.kibo.data.remote.firestoreUpload.UserData
import io.jadu.kibo.databinding.ActivityAddTreeBinding
import io.jadu.kibo.ui.features.addTree.viewModel.AddTreeViewModel
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class AddTreeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTreeBinding
    private lateinit var dropDownArrayAdapter: ArrayAdapter<String>
    private lateinit var treeInfo: List<TreeInfo>
    private lateinit var firebaseStorageRef:FirebaseStorage
    private val addTreeViewModel: AddTreeViewModel by viewModels()
    val REQUEST_IMAGE_CAPTURE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTreeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAvailabilityFilters()
        firebaseStorageRef = FirebaseStorage.getInstance()

        //show toolbar
        treeInfo = listOf(
            TreeInfo(
                "Banyan Tree",
                "The banyan tree flourishes in tropical climates with temperatures of 68-95°F (20-35°C), enduring humidity and adapting to diverse weather. It demands initial moisture for growth but becomes drought-tolerant as it matures, showcasing its iconic aerial roots and expansive canopy.",
                "https://thewire.in/wp-content/uploads/2017/06/idea_sized-thomas-17366900863_5eb31d360e_o.jpg"
            ),
            TreeInfo(
                "Ashoka Tree",
                "\n" +
                        "The Ashoka tree thrives in subtropical and tropical regions with temperatures between 50-104°F (10-40°C). Resilient in varied weather, it's known for its elegant foliage and fragrant flowers. While needing moderate water during growth, mature Ashoka trees can withstand drought conditions once established.",
                "https://m.media-amazon.com/images/I/6102T24WqqL._AC_UF1000,1000_QL80_.jpg"
            ),
            TreeInfo(
                "Mango Tree",
                "The mango tree flourishes in tropical climates with temperatures of 68-95°F (20-35°C), enduring humidity and adapting to diverse weather. It demands initial moisture for growth but becomes drought-tolerant as it matures, showcasing its iconic aerial roots and expansive canopy.",
                "https://bit.ly/3QFhKld"
            ),
            TreeInfo(
                "Guava Tree",
                "The guava tree thrives in subtropical and tropical regions with temperatures between 50-104°F (10-40°C). Resilient in varied weather, it's known for its elegant foliage and fragrant flowers. While needing moderate water during growth, mature guava trees can withstand drought conditions once established.",
                "https://bit.ly/47xddHu"
            ),
            TreeInfo(
                "Oak Tree",
                "The oak tree flourishes in tropical climates with temperatures of 68-95°F (20-35°C), enduring humidity and adapting to diverse weather. It demands initial moisture for growth but becomes drought-tolerant as it matures, showcasing its iconic aerial roots and expansive canopy.",
                "https://cdn.britannica.com/82/196882-050-18789B3B/oak-tree-Quercus-robur-fall-foliage.jpg"
            )

        )

        binding.myLocation.setOnClickListener {
            if (!addTreeViewModel.checkLocationPermission()) {
                requestPermissionLauncherForLocation.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            } else {
                addTreeViewModel.getLastLocation()
                binding.location.setText(addTreeViewModel.locality)
            }
        }

        binding.uploadImage.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            resultLauncher.launch(takePictureIntent)
        }

        binding.addTreeButton.setOnClickListener {
            val sha256Code = addTreeViewModel.generateRandomSHA256()
            val email = "abc.com"
            val selectedTree = binding.availabilitySelector.text.toString()
            val location = binding.location.text.toString()
            val imageBitmap: Bitmap? = binding.previewTreeImage.drawable?.toBitmap()
            val points = 10
            val currentDate = System.currentTimeMillis()
            if (email.isNotEmpty() && selectedTree.isNotEmpty() && location.isNotEmpty() && imageBitmap != null) {
                // All necessary data is available, proceed with upload
                uploadUserData(sha256Code,email, selectedTree, location, imageBitmap,points)
            } else {
                // Handle case when some data is missing
                Toast.makeText(this, "Please fill in all fields and capture an image", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun uploadUserData(sha256Code: String, email: String, selectedTree: String, location: String, imageBitmap: Bitmap, points: Int) {
        binding.progressBar.visibility = View.VISIBLE
        binding.addTreeButton.isEnabled = false
        binding.addTreeButton.text = ""
        binding.addTreeButton.background = ContextCompat.getDrawable(this@AddTreeActivity, R.color.lightGreyish)

        val storageRef = firebaseStorageRef.reference.child("images/${System.currentTimeMillis()}.jpg")
        val uploadTask = storageRef.putFile(getImageUri(imageBitmap))

        uploadTask.addOnSuccessListener { taskSnapshot ->
            storageRef.downloadUrl.addOnSuccessListener { uri ->
                val imageUrl = uri.toString()

                val userData = UserData(sha256Code, email, selectedTree, location, imageUrl, points, System.currentTimeMillis().toString())
                val db = FirebaseFirestore.getInstance()

                // Create a new subcollection named "entries" within the document with the email
                db.collection("treesDatabase").document(email).collection("entries")
                    .add(userData)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Tree data added successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Failed to add tree data", Toast.LENGTH_SHORT).show()
                    }
                    .addOnCompleteListener {
                        binding.progressBar.visibility = View.GONE
                        binding.addTreeButton.isEnabled = true
                        binding.addTreeButton.text = "Add Tree"
                        binding.addTreeButton.background = ContextCompat.getDrawable(this@AddTreeActivity, R.color.fab_color)
                    }
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to add tree", Toast.LENGTH_SHORT).show()
            binding.progressBar.visibility = View.GONE
            binding.addTreeButton.isEnabled = true
            binding.addTreeButton.text = "Add Tree"
            binding.addTreeButton.background = ContextCompat.getDrawable(this@AddTreeActivity, R.color.fab_color)
        }
    }



    private val requestPermissionLauncherForLocation = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            addTreeViewModel.getLastLocation()
            binding.location.setText(addTreeViewModel.locality)
        } else {
            Toast.makeText(this, getString(R.string.deny_perm_text), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun setupAvailabilityFilters() {
        binding.availabilitySelector.onItemClickListener =
            AdapterView.OnItemClickListener() { _: AdapterView<*>, _: View, position: Int, _: Long ->
                val goalValues: List<String> =
                    listOf("Mango Tree", "Banyan Tree", "Guava Tree", "Oak Tree", "Ashoka Tree")
                val selectedValue = goalValues[position]
                //compare with tree info
                val treeInfo = treeInfo.find { it.name == selectedValue }
                binding.treeDetailsLayout.visibility = View.VISIBLE
                binding.treeName.text = treeInfo?.name
                binding.treeDescription.text = treeInfo?.description
                Glide.with(this).load(treeInfo?.image).into(binding.treeImage)
                binding.availabilitySelector.setText(selectedValue, false)
            }
    }

    override fun onResume() {
        super.onResume()
        dropDownArrayAdapter = ArrayAdapter(
            this,
            R.layout.availability_selector,
            listOf("Mango", "Banyan", "Guava", "Oak", "Ashoka")
        )
        binding.availabilitySelector.setAdapter(dropDownArrayAdapter)

    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val imageBitmap = data?.extras?.get("data") as Bitmap

//                uploadImageFromBitmap(imageBitmap)
                binding.previewTreeImage.setImageBitmap(imageBitmap)
                binding.previewLayout.visibility = View.GONE
            }
        }
//    private fun uploadImageFromBitmap(imageBitmap: Bitmap) {
//        val storageRef = firebaseStorageRef.reference.child("images/${System.currentTimeMillis()}.jpg")
//        val uploadTask = storageRef.putFile(getImageUri(imageBitmap))
//        uploadImage(uploadTask, storageRef)
//    }

//    private fun uploadImage(uploadTask: UploadTask, storageRef: StorageReference) {
//        uploadTask.addOnSuccessListener {taskSnapShot->
//            storageRef.downloadUrl.addOnSuccessListener { uri->
//                val imageUri = uri.toString()
//                val data = UserData(
//                    "abc@gmail.com",
//                    binding.treeDescription.text.toString(),
//                    binding.location.text.toString(),
//                    imageUri
//                )
//                val db = FirebaseFirestore.getInstance()
//                db.collection("treesDatabase")
//                    .add(data)
//                    .addOnSuccessListener {
//                        Toast.makeText(this, "Tree added successfully", Toast.LENGTH_SHORT).show()
//                        finish()
//                    }
//                    .addOnFailureListener {
//                        Toast.makeText(this, "Failed to add tree", Toast.LENGTH_SHORT).show()
//                    }
//
//            }
//
//        }.addOnFailureListener(OnFailureListener {
//            Toast.makeText(this, "Failed to add tree", Toast.LENGTH_SHORT).show()
//        })
//    }

    private fun getImageUri(imageBitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            this.contentResolver,
            imageBitmap,
            "Title",
            null
        )
        return Uri.parse(path)

    }
}