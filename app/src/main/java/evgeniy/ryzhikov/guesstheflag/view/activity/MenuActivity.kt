package evgeniy.ryzhikov.guesstheflag.view.activity

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import evgeniy.ryzhikov.guesstheflag.R
import evgeniy.ryzhikov.guesstheflag.databinding.ActivityMenuMainBinding
import evgeniy.ryzhikov.guesstheflag.view.fragments.PREFERENCE_SETTINGS
import evgeniy.ryzhikov.guesstheflag.view.fragments.PREFERENCE_SETTINGS_MUSIC_VOLUME
import evgeniy.ryzhikov.guesstheflag.settings.ENERGY_MAX
import evgeniy.ryzhikov.guesstheflag.domain.Energy
import evgeniy.ryzhikov.guesstheflag.utils.HideNavigationBars


class MenuActivity : AppCompatActivity() {
    private lateinit var bindingMainMenuActivity : ActivityMenuMainBinding
    lateinit var navController: NavController

    private lateinit var energy: Energy
    private lateinit var mediaPlayer: MediaPlayer
    private var backPressed = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMainMenuActivity = ActivityMenuMainBinding.inflate(layoutInflater)
        setContentView(bindingMainMenuActivity.root)

        energy = Energy(this)
        navController = Navigation.findNavController(this, R.id.navHostMenuFragment)
        setEnergy()
        setMusic()
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        HideNavigationBars.hide(window, bindingMainMenuActivity.root)
    }


    private fun setEnergy() {
        val countEnergy = " ${energy.get()} / $ENERGY_MAX "
        bindingMainMenuActivity.tvEnergyCounter.text = countEnergy
        if (!energy.isFull()) {
            bindingMainMenuActivity.ivTimer.visibility = View.VISIBLE
            bindingMainMenuActivity.tvTimer.visibility = View.VISIBLE
            startTimer()
        } else {
            bindingMainMenuActivity.ivTimer.visibility = View.GONE
            bindingMainMenuActivity.tvTimer.visibility = View.GONE
        }
    }

    private fun startTimer() {
        //Добавляем 1 секунду для синхронизации
        val time = energy.getTimeToIncrease() + 1000

        object : CountDownTimer(time, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                val minute = millisUntilFinished / (60 * 1000)
                val second = (millisUntilFinished - minute * (60 * 1000)) / 1000

                val secondStr = if (second < 10) "0$second" else second.toString()

                val timeStr = " $minute:$secondStr "
                bindingMainMenuActivity.tvTimer.text = timeStr
            }

            override fun onFinish() {
                setEnergy()
            }
        }.start()

    }

    private fun setMusic() {
        mediaPlayer = MediaPlayer.create(this, R.raw.music_menu)
        mediaPlayer.isLooping = true
        val sharedPreferences = getSharedPreferences(
            PREFERENCE_SETTINGS,
            MODE_PRIVATE
        )
        val volume = sharedPreferences.getFloat(PREFERENCE_SETTINGS_MUSIC_VOLUME, 0.5f)
        setVolumeMusic(volume)
    }

    fun setVolumeMusic(volume: Float) {
        mediaPlayer.setVolume(volume, volume)
    }

    override fun onResume() {
        super.onResume()
        mediaPlayer.start()
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
    }

    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                exitDoubleTap()
            }
        }

    private fun exitDoubleTap() {
        if (backPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            exit()
        } else {
            Toast.makeText(this, R.string.alert_double_tap_exit, Toast.LENGTH_SHORT).show()
        }
        backPressed = System.currentTimeMillis()
    }

    fun exit() {
        finishAndRemoveTask()
    }

    companion object {
        const val TIME_INTERVAL = 2000
    }


}