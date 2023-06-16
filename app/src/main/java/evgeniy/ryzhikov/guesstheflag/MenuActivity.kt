package evgeniy.ryzhikov.guesstheflag

import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import evgeniy.ryzhikov.guesstheflag.databinding.ActivityMenuMainBinding
import evgeniy.ryzhikov.guesstheflag.fragments.PREFERENCE_SETTINGS
import evgeniy.ryzhikov.guesstheflag.fragments.PREFERENCE_SETTINGS_MUSIC_VOLUME
import evgeniy.ryzhikov.guesstheflag.settings.ENERGY_MAX
import evgeniy.ryzhikov.guesstheflag.statistic.PREFERENCES_STATISTIC
import evgeniy.ryzhikov.guesstheflag.utils.Energy
import evgeniy.ryzhikov.guesstheflag.utils.RoundTimer
import java.util.Calendar
import java.util.Date

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
    }

    private fun setEnergy() {
        val countEnergy = "${energy.get()} / $ENERGY_MAX"
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
                val second = millisUntilFinished - minute
                val timeStr = "${minute/(60*1000)}:${second/1000}"
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
            finish()
        } else {
            Toast.makeText(this, R.string.alert_double_tap_exit, Toast.LENGTH_SHORT).show()
        }
        backPressed = System.currentTimeMillis()
    }

    companion object {
        const val TIME_INTERVAL = 2000
    }
}