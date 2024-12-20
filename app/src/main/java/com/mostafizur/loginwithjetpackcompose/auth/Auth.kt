package com.mostafizur.loginwithjetpackcompose.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.mostafizur.loginwithjetpackcompose.ui.model.User
import com.mostafizur.loginwithjetpackcompose.utils.AuthUser

@Composable
fun Auth (checkAuth: MutableState<Boolean>, user: User){
    AuthUser.saveBoolean("isLogging",checkAuth.value)
    AuthUser.saveUser(user)
}