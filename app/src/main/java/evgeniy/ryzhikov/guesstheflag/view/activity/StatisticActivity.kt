package evgeniy.ryzhikov.guesstheflag.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.RecyclerView
import evgeniy.ryzhikov.guesstheflag.App
import evgeniy.ryzhikov.guesstheflag.R
import evgeniy.ryzhikov.guesstheflag.data.FirebaseUserUid
import evgeniy.ryzhikov.guesstheflag.databinding.ActivityStatisticBinding
import evgeniy.ryzhikov.guesstheflag.view.fragments.NotEnoughEnergyFragment
import evgeniy.ryzhikov.guesstheflag.view.fragments.TAG_NOT_ENOUGH_ENERGY
import evgeniy.ryzhikov.guesstheflag.domain.statistic.PREFERENCES_STATISTIC
import evgeniy.ryzhikov.guesstheflag.domain.statistic.PREFERENCES_STATISTIC_CORRECT_ANSWERS
import evgeniy.ryzhikov.guesstheflag.domain.statistic.PREFERENCES_STATISTIC_TOTAL_ANSWERS
import evgeniy.ryzhikov.guesstheflag.domain.statistic.PREFERENCES_STATISTIC_TOTAL_POINTS
import evgeniy.ryzhikov.guesstheflag.domain.statistic.PREFERENCES_STATISTIC_WRONG_ANSWERS
import evgeniy.ryzhikov.guesstheflag.domain.statistic.RoundStatistic
import evgeniy.ryzhikov.guesstheflag.domain.statistic.rv.StatField
import evgeniy.ryzhikov.guesstheflag.domain.statistic.rv.StatHeader
import evgeniy.ryzhikov.guesstheflag.domain.statistic.rv.StatisticAdapter
import evgeniy.ryzhikov.guesstheflag.domain.Energy
import evgeniy.ryzhikov.guesstheflag.domain.statistic.StatisticData

class StatisticActivity : AppCompatActivity() {
    lateinit var binding: ActivityStatisticBinding
    private var backPressed = 0L
    private lateinit var recyclerView: RecyclerView
    private var adapter = StatisticAdapter()

    private val viewModel = App.instance.statisticViewModel

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

        setButtonOnClickListener()
        initRV()
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        viewModel.statisticLiveData.observe(this) { statisticData ->
            addTotalStatistic(statisticData)
        }

        getStatistic()
    }


    private fun getStatistic() {
        viewModel.getStatistic()
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

    private fun addTotalStatistic(statisticData: StatisticData) {
        adapter.addItem(StatHeader(getString(R.string.statistic_total_title)))
        adapter.addItem(StatField(R.drawable.question, getString(R.string.statistic_total_game), statisticData.totalGame.toString()))
        adapter.addItem(StatField(R.drawable.question, getString(R.string.statistic_total_answers), statisticData.totalQuestions.toString()))
        adapter.addItem(StatField(R.drawable.answer_correct, getString(R.string.statistic_correct_answer), statisticData.totalCorrect.toString()))
        adapter.addItem(StatField(R.drawable.answer_wrong, getString(R.string.statistic_wrong_answer), statisticData.totalWrong.toString()))
        adapter.addItem(StatField(R.drawable.percent, getString(R.string.statistic_percent_correct_answers),statisticData.totalPercentCorrect))
        adapter.addItem(StatField(R.drawable.points, getString(R.string.statistic_all_points), statisticData.totalPoints.toString()))
    }

    /*private fun addTotalStatistic() {
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
    }*/

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
            val intent = Intent(this, GameMainActivity::class.java)
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