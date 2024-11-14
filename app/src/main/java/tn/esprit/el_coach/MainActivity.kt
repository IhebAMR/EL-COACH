// MainActivity.kt
package tn.esprit.el_coach

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import tn.esprit.el_coach.ui.theme.ELCOACHTheme
import tn.esprit.el_coach.Screens.LoginScreen
import tn.esprit.el_coach.Screens.SignupScreen

class MainActivity : ComponentActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configuration de Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("VOTRE_CLIENT_ID") // Remplacez par votre ID client
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Déclaration du ActivityResultLauncher pour la connexion Google
        val googleSignInLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                try {
                    val account = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                        .getResult(ApiException::class.java)
                    val displayName = account?.displayName
                    Toast.makeText(this, "Connecté avec Google: $displayName", Toast.LENGTH_SHORT).show()
                } catch (e: ApiException) {
                    Toast.makeText(this, "Échec de la connexion Google", Toast.LENGTH_SHORT).show()
                }
            }
        }

        setContent {
            ELCOACHTheme {
                // Initialiser la navigation
                val navController = rememberNavController()
                AppNavigation(
                    navController = navController,
                    onGoogleLogin = { signInWithGoogle(googleSignInLauncher) }
                )
            }
        }
    }

    // Fonction pour démarrer l'activité de connexion Google
    private fun signInWithGoogle(googleSignInLauncher: ActivityResultLauncher<Intent>) {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    onGoogleLogin: () -> Unit
) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                navController = navController,
                onGoogleLogin = onGoogleLogin,
                onToggle = { navController.navigate("signup") },
                onForgotPassword = { /* Autres actions */ },
                onFacebookLogin = { /* Autres actions */ },
                onOutlookLogin = { /* Autres actions */ }

            )
        }

        composable("signup") {
            SignupScreen(
                navController = navController,
                onToggle = { navController.navigate("login") } // Naviguer vers l'écran de connexion
            )
        }

    }
}