package com.github.educationissimple.tasks.presentation.components.dialogs

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.educationissimple.components.composables.buttons.DefaultPrimaryButton
import com.github.educationissimple.components.composables.buttons.DefaultSecondaryButton
import com.github.educationissimple.components.composables.dialogs.DefaultDialog
import com.github.educationissimple.presentation.locals.LocalSpacing
import com.github.educationissimple.tasks.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus

/**
 * Displays a dialog informing the user that the notifications permission has been denied.
 *
 * This composable function is shown when the app requests notifications permission, but the user has denied it.
 * It provides the user with the following options:
 * - **Cancel**: Dismiss the dialog without taking any action.
 * - **Go to Settings** (if rationale is shown): Directs the user to the app's settings page to manually enable the notifications permission.
 * - **Grant Permission** (if rationale is not shown): Requests the notifications permission again by launching the permission request flow.
 *
 * The dialog is displayed only when the notifications permission has been denied. It also checks whether the rationale for the denial should be shown (i.e., whether the user should be informed why the permission is needed).
 *
 * @param notificationsPermissionState The current permission state of the notifications permission.
 * The state is used to determine if the permission is denied and whether a rationale should be shown.
 * @param onDismiss A callback function to dismiss the dialog when the user presses cancel or closes it.
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DeniedNotificationsPermissionDialog(notificationsPermissionState: PermissionState, onDismiss: () -> Unit) {
    if (notificationsPermissionState.status !is PermissionStatus.Denied) return
    DefaultDialog(
        onDismiss = onDismiss,
        title = stringResource(R.string.notifications_permission_title)
    ) {
        if ((notificationsPermissionState.status as PermissionStatus.Denied).shouldShowRationale) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.small)
            ) {
                Text(stringResource(R.string.notifications_permission_rationale))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(LocalSpacing.current.small)
                ) {
                    DefaultSecondaryButton(
                        label = stringResource(R.string.cancel),
                        onClick = onDismiss,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    val context = LocalContext.current
                    DefaultPrimaryButton(
                        label = stringResource(R.string.go_to_settings),
                        onClick = {
                            val intent = Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts(
                                    "package",
                                    context.packageName,
                                    null
                                )
                            )
                            context.startActivity(intent)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.small)
            ) {
                Text(stringResource(R.string.notifications_permission_denied))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(LocalSpacing.current.small)
                ) {
                    DefaultSecondaryButton(
                        label = stringResource(R.string.cancel),
                        onClick = onDismiss,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    DefaultPrimaryButton(
                        label = stringResource(R.string.grant),
                        onClick = {
                            notificationsPermissionState.launchPermissionRequest()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }
            }
        }
    }
}