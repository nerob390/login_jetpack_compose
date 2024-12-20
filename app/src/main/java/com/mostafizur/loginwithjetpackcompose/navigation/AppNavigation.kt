package com.mostafizur.loginwithjetpackcompose.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mostafizur.loginwithjetpackcompose.MainActivity
import com.mostafizur.loginwithjetpackcompose.ui.login.LoginUI

@Composable
fun AppNavigation() {
    // Initialize NavController
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = "login",  // Define the start screen
                modifier = Modifier.padding(paddingValues)  // Apply the padding to the NavHost
            ) {
                composable("login") {
                   // LoginUI(navController)  // Pass NavController to LoginUI
                }
                composable("home") {
                    MainActivity()  // Navigate to Home screen after login
                }
            }
        }
    )
}
