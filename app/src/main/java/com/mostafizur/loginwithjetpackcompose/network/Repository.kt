package com.mostafizur.loginwithjetpackcompose.network

import android.app.ActionBar.LayoutParams.*
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.gson.JsonObject
import com.mostafizur.loginwithjetpackcompose.R
import com.mostafizur.loginwithjetpackcompose.ui.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response

/**
 * Creadted BY tanvir3488 on 11/21/2021.
 */
class Repository @Inject constructor(val context: Context) {


    private val retService = RetrofitInstance.getRetrofitInstance().create(ApiServices::class.java)

    private lateinit var pDialog: Dialog

    fun isInternetAvailable(context: Context): Boolean {

        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }


        return result
    }

    private fun loading(context: Context) {
        if (context !is Activity || context.isFinishing) {
            // Do not show dialog if the activity is not valid
            Log.e("DialogError", "Activity is not valid to show dialog")
            return
        }
        //final AlertDialog.Builder builder = new AlertDialog.Builder(this,android.R.style.Theme_DeviceDefault_Dialog);
        if (::pDialog.isInitialized)
            if (pDialog.isShowing) {
                pDialog.dismiss()
            }

        if (!isInternetAvailable(context)) {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show()
            return
        }
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View =
            inflater.inflate(R.layout.global_loading, null)
        pDialog = Dialog(context)

        pDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        pDialog.requestWindowFeature(DialogFragment.STYLE_NO_TITLE)
        pDialog.setContentView(view)
        val window: Window = pDialog.getWindow()!!
        pDialog.setCancelable(false)
        window.setLayout(WRAP_CONTENT, WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
        Log.e("pdailog", pDialog.isShowing.toString() + " " + context.applicationContext)
        pDialog.show()

    }

    // Function to call login API
    suspend fun login(context: Context,jsonObject: JsonObject): Response<User> {
        try {
            // Show the loading dialog on the Main Thread
            withContext(Dispatchers.Main) {
                loading(context)
            }

            Log.e("postData", jsonObject.toString())

            // Perform the network call
            val response = retService.login(jsonObject)

            // Dismiss the dialog on the Main Thread
            withContext(Dispatchers.Main) {
                if (::pDialog.isInitialized && pDialog.isShowing) {
                    pDialog.dismiss()
                }
            }

            return response
        } catch (e: Exception) {
            // Ensure dialog dismissal even in case of error
            withContext(Dispatchers.Main) {
                if (::pDialog.isInitialized && pDialog.isShowing) {
                    pDialog.dismiss()
                }
            }
            throw e // Re-throw exception
        }
    }





    private fun makeRequestBody( jsonObject: JSONObject): RequestBody {
        var jobject =JSONObject(jsonObject.toString().replace("\\",""))

        return RequestBody.create(
            "application/json; charset=utf-8".toMediaType(),
            jobject.apply {
                put("user_id", jsonObject.getString("user_id"))
            }.toString()
        )

    }


}