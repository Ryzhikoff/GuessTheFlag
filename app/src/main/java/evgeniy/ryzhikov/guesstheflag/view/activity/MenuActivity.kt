package evgeniy.ryzhikov.guesstheflag.view.activity

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
import evgeniy.ryzhikov.guesstheflag.settings.ENERGY_MAX
import evgeniy.ryzhikov.guesstheflag.domain.Energy
import evgeniy.ryzhikov.guesstheflag.settings.COEF_FOR_SPEED_SCROLLING_FACTS
import evgeniy.ryzhikov.guesstheflag.utils.HideNavigationBars
import evgeniy.ryzhikov.guesstheflag.utils.MediaPlayerController
import kotlin.random.Random


class MenuActivity : AppCompatActivity() {
    private lateinit var bindingMainMenuActivity : ActivityMenuMainBinding
    lateinit var navController: NavController

    private var energy: Energy = Energy()
    private var backPressed = 0L
    private val media = MediaPlayerController.getInstance()
    private var isRunningFacts = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMainMenuActivity = ActivityMenuMainBinding.inflate(layoutInflater)
        setContentView(bindingMainMenuActivity.root)

        navController = Navigation.findNavController(this, R.id.navHostMenuFragment)
        setEnergy()

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        HideNavigationBars.hide(window, bindingMainMenuActivity.root)
        isRunningFacts = false
    }

    private fun startFactsFlags() {
        val facts = resources.getStringArray(R.array.facts)
        val randomPos = Random.nextInt(0, facts.size - 1)
        val randomFact = facts[randomPos]
        val speed = randomFact.length * COEF_FOR_SPEED_SCROLLING_FACTS
        bindingMainMenuActivity.tvFacts.text = randomFact

        bindingMainMenuActivity.tvFacts.viewTreeObserver.addOnGlobalLayoutListener {
            if (!isRunningFacts) {
                isRunningFacts = true
                bindingMainMenuActivity.tvFacts.startScroll(speed)
            }
        }

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

    override fun onStart() {
        super.onStart()
        if (!isRunningFacts) {
            startFactsFlags()
        }
    }

    override fun onResume() {
        super.onResume()
        if (media.stopMusic) {
            media.resumeMusic()
        }
        media.stopMusic = true
    }

    override fun onPause() {
        super.onPause()
        if (media.stopMusic) {
            media.pauseMusic()
        }
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