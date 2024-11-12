package tn.esprit.el_coach

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import tn.esprit.el_coach.Screens.LoginScreen
import tn.esprit.el_coach.Screens.SignupScreen
import tn.esprit.el_coach.ui.theme.ELCOACHTheme

class MainActivity : ComponentActivity() {
    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Facebook SDK
        FacebookSdk.setAutoLogAppEventsEnabled(true)
        AppEventsLogger.activateApp(application) // Changed to this context
        enableEdgeToEdge()

        // Initialize CallbackManager
        callbackManager = CallbackManager.Factory.create()

        setContent {
            ELCOACHTheme {
                var showLogin by remember { mutableStateOf(true) }
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    if (showLogin) {
                        LoginScreen(
                            modifier = Modifier.padding(innerPadding),
                            onToggle = { showLogin = false },
                            onForgotPassword = { /* Handle forgot password */ },
                            onFacebookLogin = { initiateFacebookLogin() }, // Call the login function
                            onGoogleLogin = { /* Handle Google login */ },
                            onOutlookLogin = { /* Handle Outlook login */ }
                        )
                    } else {
                        SignupScreen(
                            modifier = Modifier.padding(innerPadding),
                            onToggle = { showLogin = true }
                        )
                    }
                }
            }
        }
    }

    private fun initiateFacebookLogin() {
        // Register a callback to handle the login response
        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                handleFacebookLogin(result) // Handle successful login
            }

            override fun onCancel() {
                Log.d("FacebookLogin", "Login canceled.")
            }

            override fun onError(error: FacebookException) {
                Log.e("FacebookLogin", "Login error: ${error.message}")
            }
        })

        // Trigger the login dialog
        LoginManager.getInstance().logInWithReadPermissions(this, listOf("email", "public_profile"))
    }

    private fun handleFacebookLogin(result: LoginResult) {
        val accessToken = result.accessToken
        // Use the access token for authentication or other purposes
        Log.d("FacebookLogin", "Access Token: ${accessToken.token}")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}