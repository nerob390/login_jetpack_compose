package com.mostafizur.loginwithjetpackcompose.ui.login

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mostafizur.loginwithjetpackcompose.MainActivity
import com.mostafizur.loginwithjetpackcompose.R
import com.mostafizur.loginwithjetpackcompose.auth.Auth
import com.mostafizur.loginwithjetpackcompose.utils.AuthUser
import com.mostafizur.loginwithjetpackcompose.viewModel.LoginViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginUI (
    viewModel: LoginViewModel = hiltViewModel()

) {
    // Observing login result from the ViewModel
    val loginState by viewModel.loginResult.observeAsState(LoginViewModel.LoginState.Loading) // Using Loading as default state
    // Variables for user input
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val isChecked = remember { mutableStateOf(false) }
    val lightBlue = Color(0xffd8e6ff)
    // Get Local Context
    val context = LocalContext.current
    AuthUser.init(context)

    // Handle login button click inside composable function
    val onLoginClicked = {
        if (email.isNotBlank() && password.isNotBlank()) {
            viewModel.login(context,email, password)
        } else {
            // Show error or prompt to fill fields
            Toast.makeText(context, "Please enter both email and password.", Toast.LENGTH_SHORT).show()
        }
    }

    // UI content
    Box(
        modifier = Modifier.fillMaxSize(),
        //contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
           // horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(52.dp))
            Text(text = "Login", fontWeight = FontWeight.Bold, fontSize = 25.sp,color= colorResource(id = R.color.themeColor), style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(start = 15.dp))
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Welcome back to the app", fontSize = 18.sp, style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(start = 15.dp))

            Spacer(modifier = Modifier.height(100.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                placeholder = {
                    Text(text = "Enter your email") // This is the hint text
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = lightBlue,
                    cursorColor = Color.Black,
                    disabledLabelColor = lightBlue,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                onValueChange = {email = it},
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                trailingIcon = {
                    if (email.isNotEmpty()) {
                        IconButton(onClick = { email = "" }) {
                            Icon(
                                imageVector = Icons.Outlined.Close,
                                contentDescription = null
                            )
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                placeholder = {
                    Text(text = "Enter your password") // This is the hint text
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = lightBlue,
                  //  cursorColor = Color.Black,
                    disabledLabelColor = lightBlue,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                onValueChange = {password = it},
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(
                        onClick = { isPasswordVisible = !isPasswordVisible }
                    ) {
                        Icon(
                            painter = painterResource(
                                id = if (isPasswordVisible) R.drawable.lock_open else R.drawable.lock
                            ),
                            modifier = Modifier.padding(6.dp),
                            contentDescription = if (isPasswordVisible) "Hide password" else "Show password"
                        )
                    }
                       }
                   )
            Spacer(modifier = Modifier.height(16.dp))
            Row (
                verticalAlignment = Alignment.CenterVertically,  // Vertically align the checkbox and text
                horizontalArrangement = Arrangement.Start
            ){
                Checkbox(
                    checked = isChecked.value,
                    onCheckedChange = { isChecked.value = it },
                    modifier = Modifier.padding(8.dp),
                    enabled = true,
                    colors = CheckboxDefaults.colors(
                        checkedColor = colorResource(id = R.color.themeColor),
                        uncheckedColor = Color.Black,
                        checkmarkColor = Color.White
                    ),
                    interactionSource = remember { MutableInteractionSource() }
                )
                Text(text = "Remember me", textAlign = TextAlign.Center)
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Login Button
            Button(
                onClick = { onLoginClicked() },
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.themeColor)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Login")
            }

            Spacer(modifier = Modifier.height(16.dp))
            when (loginState) {
                is LoginViewModel.LoginState.Loading -> {
                    //  CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                is LoginViewModel.LoginState.Success -> {
                    // Handle success - e.g., show a success message or navigate to another screen
                    val successData = (loginState as LoginViewModel.LoginState.Success).data
                    Auth(checkAuth = isChecked, user = successData!!)
                    Text(text = "Login successful!", color = Color.Green)
                    Intent(context, MainActivity::class.java).also {
                        context.startActivity(it)
                        (context as? Activity)?.finish()// Finish the splash activity so the user cannot go back to it
                    }
                }
                is LoginViewModel.LoginState.Error -> {
                    // Show the error message from the state
                    Text(text = (loginState as LoginViewModel.LoginState.Error).message, color = Color.Red)
                }
            }
        }
    }
}
