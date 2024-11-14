package tn.esprit.el_coach.Screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import tn.esprit.el_coach.DataClasse.RetrofitClient
import tn.esprit.el_coach.DataClasse.SignUpRequest
import tn.esprit.el_coach.DataClasse.SignUpResponse

class SignUpViewModel : ViewModel() {
    val signUpResult = mutableStateOf<String?>(null)

    // Fonction d'inscription
    fun signUp(fullName: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                // Utilisation du client Retrofit pour appeler l'API
                val response: Response<SignUpResponse> = RetrofitClient.instance.signUp(

                    SignUpRequest(fullName, email, password)
                )

                // Vérification de la réponse
                if (response.isSuccessful) {
                    signUpResult.value = "Inscription réussie! Bienvenue!"
                } else {
                    signUpResult.value = "Erreur lors de l'inscription: ${response.message()}"
                }
            } catch (e: Exception) {
                signUpResult.value = "Erreur réseau: ${e.message}"
            }
        }
    }
}
