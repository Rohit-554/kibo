package io.jadu.kibo.ui.features.addTree.viewModel

import android.app.Application
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AddTreeViewModel @Inject constructor(
    private val application: Application,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
) : ViewModel() {
    var locality = ""
    private val mainEventChannel = Channel<MainEvent>()
    val mainEvent = mainEventChannel.receiveAsFlow()
    fun checkLocationPermission(): Boolean {
        return (ActivityCompat.checkSelfPermission(
            application,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
                == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    application,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
                == PackageManager.PERMISSION_GRANTED)
    }

    fun getLastLocation() {
        if (checkLocationPermission()) {
            val task = fusedLocationProviderClient.lastLocation
            task.addOnSuccessListener {
                if (it != null) {
                    getFarmLocationFromCoordinates(it.latitude, it.longitude)
                }
            }
        }
    }

    private fun getFarmLocationFromCoordinates(latitude: Double, longitude: Double) {
        val geocoder = Geocoder(application, Locale.getDefault())
        try {
            val addresses: List<Address> =
                geocoder.getFromLocation(latitude, longitude, 1) as List<Address>
            locality = addresses[0].getAddressLine(0)
        }catch (e:Exception){
            viewModelScope.launch {
                mainEventChannel.send(MainEvent.Error(e.toString()))
            }
        }

    }

    fun generateRandomSHA256(): String {
        val secureRandom = SecureRandom()
        val randomBytes = ByteArray(32) // SHA-256 produces a 32-byte hash
        secureRandom.nextBytes(randomBytes)

        val messageDigest = MessageDigest.getInstance("SHA-256")
        val hashBytes = messageDigest.digest(randomBytes)

        return hashBytes.joinToString("") { "%02x".format(it) }
    }

    sealed class MainEvent {
        data class Error(val error: String) : MainEvent()
        data class Success(val message: String) : MainEvent()
    }
}