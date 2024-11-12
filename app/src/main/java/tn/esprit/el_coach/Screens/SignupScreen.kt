package tn.esprit.el_coach.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tn.esprit.el_coach.R
@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    onToggle: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "SIGN UP",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 32.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            var fullName by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var confirmPassword by remember { mutableStateOf("") }
            var passwordVisible by remember { mutableStateOf(false) }

            var passwordStrengthLevel by remember { mutableIntStateOf(0) }
            var showStrengthIndicator by remember { mutableStateOf(false) }

            var fullNameError by remember { mutableStateOf<String?>(null) }
            var emailError by remember { mutableStateOf<String?>(null) }
            var passwordError by remember { mutableStateOf<String?>(null) }
            var confirmPasswordError by remember { mutableStateOf<String?>(null) }

            TextField(
                value = fullName,
                onValueChange = {
                    fullName = it
                    fullNameError = if (it.isBlank()) "Full Name cannot be empty" else null
                },
                label = { Text("Full Name", color = Color.Black) },
                isError = fullNameError != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp)),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_name),
                        contentDescription = "Name Icon",
                        tint = Color.Black
                    )
                },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFFFFFFF),
                    unfocusedContainerColor = Color(0xFFB1BAC7),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            fullNameError?.let { Text(it, color = Color.Red, style = MaterialTheme.typography.bodySmall) }

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = if (!android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()) "Invalid email address" else null
                },
                label = { Text("Email", color = Color.Black) },
                isError = emailError != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp)),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_email),
                        contentDescription = "Email Icon",
                        tint = Color.Black
                    )
                },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFFFFFFF),
                    unfocusedContainerColor = Color(0xFFB1BAC7),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            emailError?.let { Text(it, color = Color.Red, style = MaterialTheme.typography.bodySmall) }

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordStrengthLevel = calculatePasswordStrength(password)
                    showStrengthIndicator = password.isNotEmpty()
                    passwordError = if (password.length < 8) "Password too short" else null
                },
                label = { Text("Password", color = Color.Black) },
                isError = passwordError != null,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(id = if (passwordVisible) R.drawable.ic_visibility else R.drawable.ic_visibility_off),
                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                            tint = Color.Black
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp)),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_lock),
                        contentDescription = "Password Icon",
                        tint = Color.Black
                    )
                },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFFFFFFF),
                    unfocusedContainerColor = Color(0xFFB1BAC7),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            passwordError?.let { Text(it, color = Color.Red, style = MaterialTheme.typography.bodySmall) }

            if (showStrengthIndicator) {
                Text(
                    text = "Use at least 8 characters, including a mix of letters, numbers, and special symbols.",
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
                PasswordStrengthIndicator(strengthLevel = passwordStrengthLevel)
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    confirmPasswordError = if (it != password) "Passwords do not match" else null
                },
                label = { Text("Confirm Password", color = Color.Black) },
                isError = confirmPasswordError != null,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp)),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_lock),
                        contentDescription = "Confirm Password Icon",
                        tint = Color.Black
                    )
                },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFFFFFFF),
                    unfocusedContainerColor = Color(0xFFB1BAC7),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            confirmPasswordError?.let { Text(it, color = Color.Red, style = MaterialTheme.typography.bodySmall) }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (fullNameError == null && emailError == null && passwordError == null && confirmPasswordError == null) {
                        // Proceed with signup logic
                    } else {
                        // Handle errors
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sign Up")
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onToggle) {
                Text("Already have an account? Login", color = Color.White)
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun PasswordStrengthIndicator(strengthLevel: Int) {
    val strengthColors = listOf(Color.Red, Color.Yellow, Color.Green)
    val strengthLabels = listOf("Weak", "Medium", "Strong")

    Text(
        text = strengthLabels[strengthLevel],
        color = strengthColors[strengthLevel],
        modifier = Modifier.padding(top = 4.dp)
    )
}

fun calculatePasswordStrength(password: String): Int {
    return when {
        password.length < 6 -> 0 // Weak
        password.length < 10 -> 1 // Medium
        password.matches(Regex(".*[A-Z].*")) && password.matches(Regex(".*[0-9].*")) && password.matches(Regex(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) -> 2 // Strong
        else -> 1 // Medium if it doesn't meet strong criteria but is long enough
    }
}

@Preview(showBackground = true)
@Composable
fun SignupScreenPreview() {
    SignupScreen(
        onToggle = { /* Preview onToggle action */ }
    )
}