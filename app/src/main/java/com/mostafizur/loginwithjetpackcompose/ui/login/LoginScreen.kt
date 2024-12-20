package com.mostafizur.loginwithjetpackcompose.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.mostafizur.loginwithjetpackcompose.MainActivity
import com.mostafizur.loginwithjetpackcompose.ui.theme.LoginWithJetpackComposeTheme
import com.mostafizur.loginwithjetpackcompose.utils.AuthUser
import com.mostafizur.loginwithjetpackcompose.viewModel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginScreen : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val loginState by loginViewModel.loginResult.observeAsState(LoginViewModel.LoginState.Loading) // Using Loading as default state
            AuthUser.init(applicationContext)
            if(AuthUser.getBoolean("isLogging")){
                Intent(applicationContext, MainActivity::class.java).also {
                    startActivity(it)
                    finish()// Finish the splash activity so the user cannot go back to it
                }
            }
            LoginWithJetpackComposeTheme {
                Scaffold(
                    content = {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it),
                            verticalArrangement = Arrangement.Center,
                            //horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            LoginUI()
                        }
                    }
                )
            }
        }
    }
}