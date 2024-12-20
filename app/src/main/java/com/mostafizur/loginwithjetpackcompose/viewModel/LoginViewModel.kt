package com.mostafizur.loginwithjetpackcompose.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.mostafizur.loginwithjetpackcompose.network.Repository
import com.mostafizur.loginwithjetpackcompose.ui.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _loginResult = MutableLiveData<LoginState>()
    val loginResult: LiveData<LoginState> = _loginResult

    fun login(context: Context,email: String, password: String) {
        viewModelScope.launch {
            _loginResult.value = LoginState.Loading

            val jsonObject = JsonObject().apply {
                addProperty("username", "emilys")
                addProperty("password", "emilyspass")
            }

            try {
                val response = repository.login(context,jsonObject)
                if (response.isSuccessful) {
                    _loginResult.value = LoginState.Success(response.body())
                } else {
                    Log.e("Error", "Login failed: ${response.errorBody()?.string()}")
                    _loginResult.value = LoginState.Error("Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _loginResult.value = LoginState.Error("Login failed: ${e.message}")
            }
        }
    }

    sealed class LoginState {
        object Loading : LoginState()
        data class Success(val data: User?) : LoginState()
        data class Error(val message: String) : LoginState()
    }
}
