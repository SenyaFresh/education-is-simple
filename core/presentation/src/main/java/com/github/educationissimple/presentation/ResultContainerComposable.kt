package com.github.educationissimple.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.educationissimple.common.Core
import com.github.educationissimple.common.ResultContainer
import kotlinx.coroutines.delay


/**
 * Represents a container that can:
 *
 * show progress bar when [container] is [ResultContainer.Loading];
 *
 * show error when [container] is [ResultContainer.Error] and button to handle error;
 *
 * show [onSuccess] composable when [container] is [ResultContainer.Done].
 */
@Composable
fun ResultContainerComposable(
    container: ResultContainer<*>,
    onTryAgain: () -> Unit,
    modifier: Modifier = Modifier,
    onLoading: @Composable () -> Unit = { OnLoadingEffect() },
    onSuccess: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        when (container) {
            is ResultContainer.Done -> {
                onSuccess()
            }

            is ResultContainer.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = Core.errorHandler.getUserFriendlyMessage(container.exception)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(onClick = { onTryAgain() }) {
                        Text(stringResource(R.string.core_presentation_try_again))
                    }

                }
            }

            // todo: build waiting feature in result container
            is ResultContainer.Loading -> {
                var showLoading by remember { mutableStateOf(false) }

                LaunchedEffect(Unit) {
                    delay(100)
                    showLoading = true
                }

                AnimatedVisibility(
                    visible = showLoading,
                    enter = fadeIn(tween(100)),
                    exit = fadeOut(tween(100))
                ) {
                    onLoading()
                }
            }
        }
    }
}

@Composable
fun OnLoadingEffect() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = Color.Black)
    }
}