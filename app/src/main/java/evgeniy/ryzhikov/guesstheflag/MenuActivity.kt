package evgeniy.ryzhikov.guesstheflag

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import evgeniy.ryzhikov.guesstheflag.databinding.ActivityMenuMainBinding
import evgeniy.ryzhikov.guesstheflag.settings.ENERGY_MAX
import evgeniy.ryzhikov.guesstheflag.utils.Energy
import evgeniy.ryzhikov.guesstheflag.utils.RoundTimer
import java.util.Calendar
import java.util.Date

class MenuActivity : AppCompatActivity() {
    private lateinit var bindingMainMenuActivity : ActivityMenuMainBinding
    lateinit var navController: NavController

    private lateinit var energy: Energy

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMainMenuActivity = ActivityMenuMainBinding.inflate(layoutInflater)
        setContentView(bindingMainMenuActivity.root)

        energy = Energy(this)
        navController = Navigation.findNavController(this, R.id.navHostMenuFragment)
        setEnergy()
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
}