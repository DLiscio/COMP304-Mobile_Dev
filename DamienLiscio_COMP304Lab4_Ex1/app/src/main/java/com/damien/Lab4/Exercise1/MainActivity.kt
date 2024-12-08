package com.damien.Lab4.Exercise1

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingEvent
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.*


class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient  // Fetch users current location
    private val defaultLocation = LatLng(43.7777, -79.2332) // Scarborough as default fallback location
    private lateinit var geofencingClient: GeofencingClient // Geofencing client

    // Shared state for user's location
    private var userLocationState = mutableStateOf(defaultLocation)

    // Launcher for requesting fine_location permission
    @RequiresApi(Build.VERSION_CODES.Q)
    private val fineLocationPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            fetchCurrentLocation { location ->
                userLocationState.value = location
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    requestBackgroundLocationPermission()
                } else {
                    createGeofence(location)
                }
            }
        } else {
            Log.e("PermissionDenied", "ACCESS_FINE_LOCATION permission is not granted")
            Toast.makeText(this, "Location Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    // Launcher for requesting background_location permission
    @RequiresApi(Build.VERSION_CODES.Q)
    private val backgroundLocationPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            createGeofence(userLocationState.value)
        } else {
            Log.e("PermissionDenied", "ACCESS_BACKGROUND_LOCATION permission is not granted")
            Toast.makeText(this, "Background Location Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)  //
        geofencingClient = LocationServices.getGeofencingClient(this)

        setContent {
            var locationPermissionGranted by remember { mutableStateOf(false) }  // Variable for if location permissions granted

            LaunchedEffect(Unit) {
                // Check For Location Permission, if granted fetch location, if not request permission
                if (ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true
                    fetchCurrentLocation { location ->
                        userLocationState.value = location
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
                            ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            requestBackgroundLocationPermission()
                        } else {
                            createGeofence(location)
                        }
                    }
                } else {
                    fineLocationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }
            // Display Google Map With Current Location
            MapScreen(userLocation = userLocationState.value, isLocationEnabled = locationPermissionGranted) { newLocation ->
                // When the marker is dragged, update the user's location
                userLocationState.value = newLocation
            }
        }
    }

    // Request background location permission
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun requestBackgroundLocationPermission() {
        backgroundLocationPermissionLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
    }

    // Fetch current location using FusedLocationProviderClient
    private fun fetchCurrentLocation(onLocationFetched: (LatLng) -> Unit) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val latLng = LatLng(location.latitude, location.longitude)
                    onLocationFetched(latLng)
                } else {
                    Toast.makeText(this, "Unable To Get Current Location", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Function to create geofence
    private fun createGeofence(location: LatLng) {
        Log.d("GeofenceDebug", "Creating geofence at location: Lat=${location.latitude}, Lng=${location.longitude}")
        val geofence = Geofence.Builder()
            .setRequestId("Test Geofence")
            .setCircularRegion(location.latitude, location.longitude, 1000f)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(
                Geofence.GEOFENCE_TRANSITION_DWELL or Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT
            )
            .setLoiteringDelay(10000)
            .build()

        // Create geofencing request
        val geofencingRequest = GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofence(geofence)
            .build()

        // Create geofence pending intent
        val geofencePendingIntent: PendingIntent by lazy {
            val intent = Intent(this, GeofenceBroadcastReceiver::class.java).apply{
                action = "com.damien.Lab4.Exercise1.ACTION_GEOFENCE_EVENT"
            }
            PendingIntent.getBroadcast(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }

        // Add geofence to client
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            geofencingClient.addGeofences(geofencingRequest, geofencePendingIntent)
                .addOnSuccessListener {
                    Toast.makeText(this, "Geofence Added Successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { d ->
                    Log.d("GeofenceError", "Failed to add geofence: ${d.message}")
                    Toast.makeText(this, "Geofence Failed To Be Added", Toast.LENGTH_SHORT).show()
                }
        }
    }
}

// Map screen display function
@Composable
fun MapScreen(userLocation: LatLng, isLocationEnabled: Boolean, onLocationChanged: (LatLng) -> Unit) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(userLocation, 14f)
    }

    var markerPosition by remember { mutableStateOf(userLocation) }
    val googleMap = remember { mutableStateOf<GoogleMap?>(null) }

    // Set up Google map
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(
            isMyLocationEnabled = isLocationEnabled,
            isIndoorEnabled = true,
            mapType = MapType.HYBRID
        ),
        uiSettings = MapUiSettings(
            zoomControlsEnabled = true,
            compassEnabled = true,
            mapToolbarEnabled = true,
            myLocationButtonEnabled = true,
        ),
        onMapLoaded = {
            googleMap.value?.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
                override fun onMarkerDragStart(marker: Marker) {}
                override fun onMarkerDrag(marker: Marker) {}
                override fun onMarkerDragEnd(marker: Marker) {
                    val newPosition = marker.position
                    markerPosition = newPosition
                    onLocationChanged(newPosition)
                }
            })
        }
    ) {
        // Add a marker at the user's location
        Marker(
            state = MarkerState(position = userLocation),
            title = "You are here!",
            snippet = "Current Location",
            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE),
            draggable = true
        )

        // Add circle to represent geofence
        Circle(
            center = userLocation,
            radius = 1000.0,
            fillColor = androidx.compose.ui.graphics.Color.Transparent,
            strokeColor = androidx.compose.ui.graphics.Color.Blue,
            strokeWidth = 5f
        )
    }
}

// Geofence broadcast receiver for geofence events/actions
class GeofenceBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("GeofenceReceiver", "Received intent: $intent")
        val geofencingEvent = GeofencingEvent.fromIntent(intent)

        if (geofencingEvent?.hasError() == true) {
            val errorMessage = geofencingEvent.errorCode
            Log.e("GeofenceError", "Geofence Error: $errorMessage")
            Toast.makeText(context, "Geofence Error: $errorMessage", Toast.LENGTH_SHORT).show()
            return
        }

        if (geofencingEvent != null) {
            val geofenceTransition = geofencingEvent.geofenceTransition
            Log.d("GeofenceDebug", "Received Geofence Event: $geofenceTransition")

            when (geofenceTransition) {
                Geofence.GEOFENCE_TRANSITION_ENTER -> {
                    Log.d("GeofenceTransition", "Entered the Geofence")
                    Toast.makeText(context, "You are in the Geofence!", Toast.LENGTH_SHORT).show()
                }
                Geofence.GEOFENCE_TRANSITION_EXIT -> {
                    Log.d("GeofenceTransition", "Exited the Geofence")
                    Toast.makeText(context, "You have left the geofence!", Toast.LENGTH_SHORT).show()
                }
                Geofence.GEOFENCE_TRANSITION_DWELL -> {
                    Log.d("GeofenceTransition", "Dwelling in the Geofence")
                    Toast.makeText(context, "You are dwelling in the Geofence!", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Log.d("GeofenceTransition", "Unknown Transition: $geofenceTransition")
                }
            }
        } else {
            Log.e("GeofenceError", "GeofencingEvent is null")
        }
    }
}


