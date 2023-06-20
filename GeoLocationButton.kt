package UI.NavGraph.Components.Form

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationRequest
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import java.security.Permissions

private const val LOCATION_PERMISSION_REQUEST_CODE = 1001

@Composable
fun LocationButton(){

}

 fun requestLocationPermission(context: Context, onPermissionResult: (Boolean) -> Unit) {
    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        onPermissionResult(true)
    } else {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }
}


@SuppressLint("MissingPermission")
fun getLocationNew(onLocationReceived: (String) -> Unit, cont: Context) {
    val fusedLocationProviderClient =  cont.let { context ->
        LocationServices.getFusedLocationProviderClient(context)
    }

    fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
        if (location != null) {
            val latitude = location.latitude
            val longitude = location.longitude
            val address = getAddressFromLocation(latitude, longitude, cont)
            onLocationReceived("Latitude: $latitude\nLongitude: $longitude\nAddress: $address")
        } else {
            onLocationReceived("Location not available")
        }
    }.addOnFailureListener { exception ->
        onLocationReceived("Failed to get location: ${exception.localizedMessage}")
    }
}

 fun getAddressFromLocation(latitude: Double, longitude: Double,cont: Context): String {
    // Use Geocoder or any other service to convert latitude and longitude to address
    // Example: Reverse geocoding with Geocoder
    val geocoder = Geocoder(cont)
    val addresses = geocoder.getFromLocation(latitude, longitude, 1)
    return if (addresses!!.isNotEmpty()) {
        val address = addresses[0]
        "${address.getAddressLine(0)}, ${address.locality}, ${address.adminArea}, ${address.countryName}"
    } else {
        "Unknown address"
    }
}






