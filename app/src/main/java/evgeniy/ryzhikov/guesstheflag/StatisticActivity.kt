package evgeniy.ryzhikov.guesstheflag

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Visibility
import android.view.View
import android.widget.Adapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import evgeniy.ryzhikov.guesstheflag.databinding.ActivityStatisticBinding
import evgeniy.ryzhikov.guesstheflag.fragments.NotEnoughEnergyFragment
import evgeniy.ryzhikov.guesstheflag.fragments.RoundStatisticFragment
import evgeniy.ryzhikov.guesstheflag.fragments.TAG_NOT_ENOUGH_ENERGY
import evgeniy.ryzhikov.guesstheflag.fragments.TotalStatisticFragment
import evgeniy.ryzhikov.guesstheflag.statistic.PREFERENCES_STATISTIC
import evgeniy.ryzhikov.guesstheflag.statistic.PREFERENCES_STATISTIC_CORRECT_ANSWERS
import evgeniy.ryzhikov.guesstheflag.statistic.PREFERENCES_STATISTIC_TOTAL_ANSWERS
import evgeniy.ryzhikov.guesstheflag.statistic.PREFERENCES_STATISTIC_TOTAL_POINTS
import evgeniy.ryzhikov.guesstheflag.statistic.PREFERENCES_STATISTIC_WRONG_ANSWERS
import evgeniy.ryzhikov.guesstheflag.statistic.RoundStatistic
import evgeniy.ryzhikov.guesstheflag.statistic.rv.StatField
import evgeniy.ryzhikov.guesstheflag.statistic.rv.StatHeader
import evgeniy.ryzhikov.guesstheflag.statistic.rv.StatisticAdapter
import evgeniy.ryzhikov.guesstheflag.utils.Energy

class StatisticActivity : AppCompatActivity() {
    lateinit var binding: ActivityStatisticBinding
    private var backPressed = 0L
    private lateinit var recyclerView: RecyclerView
    private var adapter = StatisticAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.statRecycler

        val bundleRoundStat = intent.extras?.getBundle("stat")
        if (bundleRoundStat != null) {
            addRoundStatistic(bundleRoundStat)
            displayButtonPlayAgain()
        }

        addTotalStatistic()
        setButtonOnClickListener()
        initRV()
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private fun addRoundStatistic(bundleRoundStat: Bundle) {
        val roundStatistic = bundleRoundStat.get("statistic") as RoundStatistic

        adapter.addItem(StatHeader(getString(R.string.statistic_round_title)))
        adapter.addItem(StatField(R.drawable.answer_correct, getString(R.string.statistic_correct_answer), roundStatistic.countCorrectAnswer.toString()))
        adapter.addItem(StatField(R.drawable.answer_wrong, getString(R.string.statistic_wrong_answer), roundStatistic.countWrongAnswer.toString()))
        adapter.addItem(StatField(R.drawable.points, getString(R.string.statistic_round_points), roundStatistic.points.toString()))
    }

    private fun displayButtonPlayAgain() {
        binding.btnAgain.visibility = View.VISIBLE
    }

    private fun addTotalStatistic() {
        val sharedPreferences = getSharedPreferences(
            PREFERENCES_STATISTIC,
            MODE_PRIVATE
        )

        val correctAnswers = sharedPreferences.getInt(PREFERENCES_STATISTIC_CORRECT_ANSWERS, 0)
        val wrongAnswers = sharedPreferences.getInt(PREFERENCES_STATISTIC_WRONG_ANSWERS, 0)
        val totalAnswers = sharedPreferences.getInt(PREFERENCES_STATISTIC_TOTAL_ANSWERS, 0)
        val points = sharedPreferences.getInt(PREFERENCES_STATISTIC_TOTAL_POINTS, 0)
        var percent = 0f
        if (totalAnswers != 0) {
            percent = correctAnswers.toFloat() / totalAnswers * 100f
        }

        adapter.addItem(StatHeader(getString(R.string.statistic_total_title)))
        adapter.addItem(StatField(R.drawable.question, getString(R.string.statistic_total_answers), totalAnswers.toString()))
        adapter.addItem(StatField(R.drawable.answer_correct, getString(R.string.statistic_correct_answer), correctAnswers.toString()))
        adapter.addItem(StatField(R.drawable.answer_wrong, getString(R.string.statistic_wrong_answer), wrongAnswers.toString()))
        adapter.addItem(StatField(R.drawable.percent, getString(R.string.statistic_percent_correct_answers), String.format("%.2f%%", percent)))
        adapter.addItem(StatField(R.drawable.points, getString(R.string.statistic_all_points), points.toString()))
    }

    private fun initRV() {
        adapter.update()
        recyclerView.adapter = adapter
    }

    private fun setButtonOnClickListener() {
        binding.btnBack.setOnClickListener {
            startMainMenu()
        }

        binding.btnAgain.setOnClickListener {
            checkEnergyAndStartGame()
        }
    }

    private fun startMainMenu() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun checkEnergyAndStartGame() {
        val energy = Energy(this)
        if (energy.isHave()) {
            energy.use()
            val intent = Intent(this, GameMain::class.java)
            startActivity(intent)
            finish()
        } else {
            NotEnoughEnergyFragment().show(supportFragmentManager, TAG_NOT_ENOUGH_ENERGY)
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