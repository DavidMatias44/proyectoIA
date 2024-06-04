package com.example.carritoscompartidos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.example.carritoscompartidos.ui.theme.CarritosCompartidosTheme

import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView

class MapActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Recuperar el tipo de usuario del intent
        val userType = intent.getStringExtra("USER_TYPE") ?: "2" // Valor predeterminado: Usuario
        val userTypeString = convertUserTypeToString(userType)

        setContent {
            CarritosCompartidosTheme {
                MapScreen(userTypeString)
            }
        }
    }

    private fun convertUserTypeToString(userType: String): String {
        return when (userType) {
            "0" -> "Administrador"
            "1" -> "Conductor"
            else -> "Usuario"
        }
    }
}

@Composable
fun MapScreen(
    userType: String,
) {
    // Aquí puedes personalizar la pantalla del mapa según el tipo de usuario
    // Text(text = "Bienvenido al mapa, $userType")

    AndroidView(
        factory = { context ->
            Mapbox.getInstance(context)
            val mapView = MapView(context)
            val styleUrl = "https://api.maptiler.com/maps/basic-v2/style.json?key=ycQI7gpNSyNGmbawpWYh"
            mapView.onCreate(null)
            mapView.getMapAsync { map ->
                map.setStyle(styleUrl) {
                    map.uiSettings.setAttributionMargins(15, 0, 0, 15)
                    map.cameraPosition = CameraPosition.Builder()
                        .target(LatLng(28.679079, 77.069710))
                        .zoom(10.0)
                        .bearing(2.0)
                        .build()
                }
            }
            mapView
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MapScreenPreview() {
    CarritosCompartidosTheme {
        MapScreen(userType = "Usuario")
    }
}
