package com.mostafizur.loginwithjetpackcompose

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.mostafizur.loginwithjetpackcompose.ui.home.Home
import com.mostafizur.loginwithjetpackcompose.ui.login.LoginScreen
import com.mostafizur.loginwithjetpackcompose.ui.theme.LoginWithJetpackComposeTheme
import com.mostafizur.loginwithjetpackcompose.utils.ExitDialog
import com.mostafizur.loginwithjetpackcompose.utils.AuthUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginWithJetpackComposeTheme {
                val showExitDialog = remember { mutableStateOf(false) }
                Scaffold(
                    Modifier.statusBarsPadding(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "Top App Bar")
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    showExitDialog.value = true
                                }) {
                                    Icon(Icons.Filled.ExitToApp, "backIcon")
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = colorResource(id = R.color.themeColor),
                                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                                actionIconContentColor = MaterialTheme.colorScheme.onSecondary
                            ),
                        )
                    },
                    content = {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues = it),
                            //verticalArrangement = Arrangement.Center,
                            //horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Home()
                        }
                    }
                )
                // Show ExitDialog if showExitDialog is true
                if (showExitDialog.value) {
                    ExitDialog(
                        onDismiss = { showExitDialog.value = false },
                        onExit = {
                            // Handle exit logic (e.g., close the app or finish the activity)
                            AuthUser.clear()
                            Toast.makeText(this@MainActivity, "Log out success...", Toast.LENGTH_SHORT).show()
                            Intent(applicationContext, LoginScreen::class.java).also {
                                startActivity(it)
                                finish() // Finish the splash activity so the user cannot go back to it
                            }
                        }
                    )
                }
            }
        }
    }
}
