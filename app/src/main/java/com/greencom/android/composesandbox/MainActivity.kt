package com.greencom.android.composesandbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.greencom.android.composesandbox.ui.route.OneTimePasswordRoute
import com.greencom.android.composesandbox.ui.theme.SandboxTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SandboxTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    OneTimePasswordRoute()
                }
            }
        }
    }
}
