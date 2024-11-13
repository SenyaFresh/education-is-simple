package com.github.educationissimple.audio.presentation.components.dialogs

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
import com.github.educationissimple.audio.R
import com.github.educationissimple.components.composables.buttons.DefaultPrimaryButton
import com.github.educationissimple.components.composables.buttons.DefaultSecondaryButton
import com.github.educationissimple.components.composables.dialogs.DefaultDialog
import com.github.educationissimple.presentation.locals.LocalSpacing
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DeniedAudioPermissionDialog(audioPermissionState: PermissionState, onDismiss: () -> Unit) {
    if (audioPermissionState.status !is PermissionStatus.Denied) return
    DefaultDialog(
        onDismiss = onDismiss,
        title = stringResource(R.string.audio_permission_title)
    ) {
        if ((audioPermissionState.status as PermissionStatus.Denied).shouldShowRationale) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.small)
            ) {
                Text(stringResource(R.string.audio_permission_rationale))
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
                Text(stringResource(R.string.audio_permission_denied))
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
                            audioPermissionState.launchPermissionRequest()
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

