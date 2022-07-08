package com.greencom.android.composesandbox.ui.route

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.greencom.android.composesandbox.ui.castle.OneTimePasswordField

@Composable
fun OneTimePasswordRoute(
    modifier: Modifier = Modifier,
) {
    var value by remember { mutableStateOf("") }

    Box(modifier = modifier.fillMaxSize()) {
        OneTimePasswordField(
            modifier = Modifier.align(Alignment.Center),
            value = value,
            onValueChanged = { value = it },
            cellCount = 4,
        )
    }
}
