@file:OptIn(ExperimentalPermissionsApi::class)

package solutions.s4y.jeleapps1.ui.composables.gcm

import android.Manifest.permission.POST_NOTIFICATIONS
import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@Composable
fun PushNotificationPermitted(
    permissionRequestContent: @Composable (shouldShowRationale: Boolean, onRequestPermission: () -> Unit) -> Unit = { shouldShowRationale, onRequestPermission ->
        Column {
            val textToShow = if (shouldShowRationale) {
                "The receiving notifications is important for this app. Please grant the permission."
            } else {
                "The Permission to receive notifications required for this feature to be available. " +
                        "Please grant the permission"
            }
            Text(textToShow)
            Button(onClick = onRequestPermission) {
                Text("Request permission")
            }
        }
    },
    permittedContent: @Composable () -> Unit,
) {
    val pushNotificationsPermissionState =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            rememberPermissionState(permission = POST_NOTIFICATIONS)
        } else {
            null
        }

    if (pushNotificationsPermissionState?.status?.isGranted != false) {
        permittedContent()
    } else {
        permissionRequestContent(
            pushNotificationsPermissionState.status.shouldShowRationale,
            pushNotificationsPermissionState::launchPermissionRequest
        )
    }
}

@Preview(name = "Notification Permission - Pre-Tiramisu", showBackground = true)
@Composable
private fun ReceiverPreviewPreTiramisu() {
    PreviewWrapper(androidVersion = Build.VERSION_CODES.S) {
        PushNotificationPermitted(
            permittedContent = {
                Text("Notifications are automatically enabled (Pre-Android 13)")
            }
        )
    }
}

@Composable
private fun PreviewWrapper(
    androidVersion: Int,
    permissionStatus: PermissionStatus = PermissionStatus.Granted,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalInspectionMode provides true,
        LocalPermissionStatus provides permissionStatus,
        LocalAndroidVersion provides androidVersion
    ) {
        MaterialTheme {
            Surface {
                content()
            }
        }
    }
}

private val LocalPermissionStatus = staticCompositionLocalOf<PermissionStatus> {
    PermissionStatus.Granted
}

private val LocalAndroidVersion = staticCompositionLocalOf { Build.VERSION_CODES.TIRAMISU }
