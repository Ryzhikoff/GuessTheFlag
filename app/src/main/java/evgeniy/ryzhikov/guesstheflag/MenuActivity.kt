package evgeniy.ryzhikov.guesstheflag

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import evgeniy.ryzhikov.guesstheflag.databinding.ActivityMenuMainBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var bindingMainMenuActivity : ActivityMenuMainBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMainMenuActivity = ActivityMenuMainBinding.inflate(layoutInflater)
        setContentView(bindingMainMenuActivity.root)

        navController = Navigation.findNavController(this, R.id.navHostMenuFragment)
    }

}