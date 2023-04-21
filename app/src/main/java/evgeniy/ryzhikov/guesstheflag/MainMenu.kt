package evgeniy.ryzhikov.guesstheflag

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import evgeniy.ryzhikov.guesstheflag.databinding.MenuChooseGameBinding
import evgeniy.ryzhikov.guesstheflag.databinding.MenuMainBinding
import evgeniy.ryzhikov.guesstheflag.utils.GameMode
import evgeniy.ryzhikov.guesstheflag.utils.Mode


class MainMenu : AppCompatActivity() {
    private lateinit var mainMenu : MenuMainBinding
    private lateinit var menuChooseGame : MenuChooseGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        displayMainMain()
    }

    private fun displayMainMain() {
        mainMenu = MenuMainBinding.inflate(layoutInflater)
        setContentView(mainMenu.root)
        addListenerMainMenu()
    }

    private fun addListenerMainMenu() {
        mainMenu.btnStartGame.setOnClickListener { onClickChooseGame(it as AppCompatButton) }
        mainMenu.btnStatistic.setOnClickListener { onClickStatistic() }
        mainMenu.btnSetting.setOnClickListener { onClickSetting() }
        mainMenu.btnExit.setOnClickListener { onClickExit() }
    }

    private fun onClickChooseGame(button: AppCompatButton) {
        //button.
        displayChooseGameMenu()
    }


    private fun onClickStatistic() {

    }

    private fun onClickSetting() {

    }

    private fun onClickExit() {

    }

    private fun displayChooseGameMenu() {
        menuChooseGame = MenuChooseGameBinding.inflate(layoutInflater)
        setContentView(menuChooseGame.root)
        addListenerChooseGameMenu()
    }

    private fun addListenerChooseGameMenu() {
        menuChooseGame.btnStartCountryFlag.setOnClickListener { onClickStartGame(it as AppCompatButton) }
        menuChooseGame.btnStartRegionFlag.setOnClickListener { onClickStartGame(it as AppCompatButton) }
        menuChooseGame.btnStartCountryMap.setOnClickListener { onClickStartGame(it as AppCompatButton) }
        menuChooseGame.btnStartRegionMap.setOnClickListener { onClickStartGame(it as AppCompatButton) }
        menuChooseGame.btnBack.setOnClickListener { onClickBack() }
    }

    private fun onClickStartGame(button : AppCompatButton) {
        //TODO: checkEnergy
        startGame(button)
    }

    private fun onClickBack() {
        displayMainMain()
    }

    private fun startGame(button : AppCompatButton) {
        GameMode.mode = when (button.id) {
            R.id.btnStartCountryFlag -> Mode.COUNTRY_FLAG
            R.id.btnStartRegionFlag -> Mode.REGION_FLAG
            R.id.btnStartCountryMap -> Mode.COUNTRY_MAP
            R.id.btnStartRegionMap -> Mode.REGION_MAP
            else -> Mode.COUNTRY_FLAG
        }
        val intent = Intent(this@MainMenu, GameMain::class.java)
        startActivity(intent)
    }
}