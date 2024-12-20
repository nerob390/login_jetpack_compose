package com.mostafizur.loginwithjetpackcompose.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mostafizur.loginwithjetpackcompose.R
import com.mostafizur.loginwithjetpackcompose.ui.login.LoginScreen
import com.mostafizur.loginwithjetpackcompose.ui.theme.LoginWithJetpackComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class SplashScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginWithJetpackComposeTheme {
                // Setting up the navController
                SplashScreenContent()
            }
        }
    }

    @Composable
    fun SplashScreenContent() {
        // Animatable for fade-in effect
        val alpha = remember { androidx.compose.animation.core.Animatable(0f) }

        // Launch effect to handle the delay and animation
        LaunchedEffect(Unit) {
            // Animate to alpha = 1f
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1000) // 3-second fade-in animation
            )

            // Wait for 3 seconds and navigate to MainActivity
            delay(1000)  // Additional delay after animation
            Intent(applicationContext, LoginScreen::class.java).also {
                startActivity(it)
                finish() // Finish the splash activity so the user cannot go back to it
            }
        }

        // Box for layout arrangement
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            // Animated image with fade-in effect
            Image(
                painter = painterResource(id = R.drawable.splash), // Your image resource
                contentDescription = "Splash Image",
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .graphicsLayer(alpha = alpha.value),  // Use alpha value from Animatable
                contentScale = ContentScale.Crop
            )
            Text(
                text = "Welcome To My App",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = colorResource(id = R.color.themeColor),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp))
        }
    }

}
