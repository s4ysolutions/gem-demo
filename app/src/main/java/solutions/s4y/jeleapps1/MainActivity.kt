package solutions.s4y.jeleapps1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import solutions.s4y.jeleapps1.interactions.gcm.GCMService
import solutions.s4y.jeleapps1.ui.composables.gcm.PushNotificationPermitted
import solutions.s4y.jeleapps1.ui.composables.screens.MainScreen
import solutions.s4y.jeleapps1.ui.theme.Jeleapps1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Jeleapps1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PushNotificationPermitted {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .padding(8.dp)
                        ) {
                            MainScreen()
                        }
                    }
                }
            }
        }
        checkGooglePlayServices()
        GCMService.requestNewToken()
        GCMService.subscribeToTopic("test")
    }

    override fun onResume() {
        super.onResume()
        checkGooglePlayServices() // Check in onResume
    }

    private fun checkGooglePlayServices() {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this)

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(
                    this,
                    resultCode,
                    PLAY_SERVICES_RESOLUTION_REQUEST
                )?.show()
            } else {
                Toast.makeText(
                    this,
                    "Google Play Services are required for this app to function.",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }
    }

    companion object {
        private const val PLAY_SERVICES_RESOLUTION_REQUEST = 9000
    }
}