package evgeniy.ryzhikov.guesstheflag.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import evgeniy.ryzhikov.guesstheflag.R
import evgeniy.ryzhikov.guesstheflag.data.preferences.PreferenceProvider
import evgeniy.ryzhikov.guesstheflag.data.preferences.Preferences
import evgeniy.ryzhikov.guesstheflag.databinding.ActivityGreetingBinding
import evgeniy.ryzhikov.guesstheflag.settings.DEFAULT_MUSIC_VOLUME
import evgeniy.ryzhikov.guesstheflag.settings.DEFAULT_SOUND_VOLUME
import evgeniy.ryzhikov.guesstheflag.utils.HideNavigationBars
import evgeniy.ryzhikov.guesstheflag.utils.MediaPlayerController


class GreetingActivity : AppCompatActivity() {
    lateinit var binding: ActivityGreetingBinding
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private lateinit var auth: FirebaseAuth
    private val media = MediaPlayerController.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGreetingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    firebaseAuthWithGoogle(account.idToken!!)
                }
            } catch (e: ApiException) {
                e.printStackTrace()
            }
        }

        val user = auth.currentUser

        if (user != null) {
            startMenuActivity()
        } else {
            setDefaultSettings()
        }
        addButtonsListener()
        HideNavigationBars.hide(window, binding.root)
    }

    private fun addButtonsListener() {
        binding.signIn.setOnClickListener {
            signInWithGoogle()
        }

        binding.btnTryAgain.setOnClickListener {
            val intent = Intent(this, GreetingActivity::class.java)
            startActivity(intent)
            onDestroy()
        }
    }

    private fun getClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(this, gso)
    }

    private fun signInWithGoogle() {
        val signInClient = getClient()
        launcher.launch(signInClient.signInIntent)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                startMenuActivity()
            } else {
                errorSignInWithGoogle()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        media.resumeMusic()
    }

    override fun onPause() {
        super.onPause()
        media.pauseMusic()
    }

    private fun startMenuActivity() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun errorSignInWithGoogle() {
        binding.signIn.visibility = View.GONE
        binding.error.visibility = View.VISIBLE
    }

    private fun setDefaultSettings() {
        val preferences = PreferenceProvider.getInstance()
        preferences.putFloat(Preferences.PreferenceName.SETTINGS, Preferences.PreferenceKey.MUSIC_VOLUME, DEFAULT_MUSIC_VOLUME)
        preferences.putFloat(Preferences.PreferenceName.SETTINGS, Preferences.PreferenceKey.SOUND_VOLUME, DEFAULT_SOUND_VOLUME)
    }
}