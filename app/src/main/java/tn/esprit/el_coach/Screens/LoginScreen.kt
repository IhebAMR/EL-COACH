package tn.esprit.el_coach.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import tn.esprit.el_coach.R
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onToggle: () -> Unit,
    onForgotPassword: (() -> Unit)? = null,
    onFacebookLogin: () -> Unit, // Change to accept no parameters
    onGoogleLogin: () -> Unit,
    onOutlookLogin: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var emailErrorMessage by remember { mutableStateOf("") }
    var passwordErrorMessage by remember { mutableStateOf("") }

    // Facebook login callback manager
    val callbackManager = remember { CallbackManager.Factory.create() }

    // Register Facebook login callback
    LaunchedEffect(Unit) {
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    onFacebookLogin() // Pass the result to the callback
                }

                override fun onCancel() {
                    // Handle cancel
                }

                override fun onError(error: FacebookException) {
                    // Handle error
                }
            })
    }


    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Background",
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = 0.9f },
            contentScale = ContentScale.Crop
        )

        // Overlay content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Login Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .border(2.dp, Color.White, RoundedCornerShape(8.dp))
                    .background(Color.Transparent)
                    .padding(0.dp)
            ) {
                Text(
                    text = "Login",
                    fontSize = 32.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            // Logo
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(200.dp)
                    .padding(top = 32.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Email TextField with Icon
            TextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = false // Reset error on change
                },
                label = { Text("Email", color = Color(0xFF001C2F)) },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_email),
                        contentDescription = "Email Icon",
                        tint = Color(0xFF001C2F)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, if (emailError) Color.Red else Color(0xFF001C2F), RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFFFFFFF),
                    unfocusedContainerColor = Color(0xFFB1BAC7),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            if (emailError) {
                Text(
                    text = emailErrorMessage,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start).padding(start = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Password TextField
            TextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = false // Reset error on change
                },
                label = { Text("Password", color = Color(0xFF001C2F)) },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_lock),
                        contentDescription = "Password Icon",
                        tint = Color(0xFF001C2F)
                    )
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(id = if (passwordVisible) R.drawable.ic_visibility else R.drawable.ic_visibility_off),
                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                            tint = Color(0xFF001C2F)
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, if (passwordError) Color.Red else Color(0xFF001C2F), RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFFFFFFF),
                    unfocusedContainerColor = Color(0xFFB1BAC7),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            if (passwordError) {
                Text(
                    text = passwordErrorMessage,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start).padding(start = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Forgot Password Button
            TextButton(onClick = { onForgotPassword?.invoke() }) {
                Text("Forgot Password?", color = Color(0xFFFFFFFF))
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Login Button
            Button(
                onClick = {
                    // Simple validation logic
                    emailError = email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
                    passwordError = password.isEmpty() || password.length < 6

                    emailErrorMessage = if (emailError) "Enter a valid email address" else ""
                    passwordErrorMessage = if (passwordError) "Password must be at least 6 characters" else ""

                    if (!emailError && !passwordError) {
                        // Handle login logic
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF001C2F)),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text("Login", color = Color.White, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Social Media Login Icons
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = onFacebookLogin) {
                    Icon(
                        painter = painterResource(id = R.drawable.facebook),
                        contentDescription = "Login with Facebook",
                        tint = Color.Unspecified
                    )
                }
                IconButton(onClick = onGoogleLogin) {
                    Icon(
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = "Login with Google",
                        tint = Color.Unspecified
                    )
                }
                IconButton(onClick = onOutlookLogin) {
                    Icon(
                        painter = painterResource(id = R.drawable.outlook),
                        contentDescription = "Login with Outlook",
                        tint = Color.Unspecified
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Sign Up Button
            TextButton(onClick = onToggle) {
                Text("Don't have an account? Sign up", color = Color.White)
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        onToggle = { /* Preview onToggle action */ },
        onForgotPassword = { /* Preview onForgotPassword action */ },
        onFacebookLogin = { /* Preview onFacebookLogin action */ },
        onGoogleLogin = { /* Preview onGoogleLogin action */ },
        onOutlookLogin = { /* Preview onOutlookLogin action */ }
    )
}