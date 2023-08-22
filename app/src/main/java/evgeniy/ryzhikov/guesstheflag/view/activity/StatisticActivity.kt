package evgeniy.ryzhikov.guesstheflag.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import evgeniy.ryzhikov.guesstheflag.App
import evgeniy.ryzhikov.guesstheflag.R
import evgeniy.ryzhikov.guesstheflag.databinding.ActivityStatisticBinding
import evgeniy.ryzhikov.guesstheflag.view.fragments.NotEnoughEnergyFragment
import evgeniy.ryzhikov.guesstheflag.view.fragments.TAG_NOT_ENOUGH_ENERGY
import evgeniy.ryzhikov.guesstheflag.domain.statistic.rv.StatField
import evgeniy.ryzhikov.guesstheflag.domain.statistic.rv.StatHeader
import evgeniy.ryzhikov.guesstheflag.domain.statistic.rv.StatisticAdapter
import evgeniy.ryzhikov.guesstheflag.domain.Energy
import evgeniy.ryzhikov.guesstheflag.domain.RoundResult
import evgeniy.ryzhikov.guesstheflag.domain.statistic.StatisticData
import evgeniy.ryzhikov.guesstheflag.utils.HideNavigationBars
import evgeniy.ryzhikov.guesstheflag.utils.MediaPlayerController
import evgeniy.ryzhikov.guesstheflag.utils.StartingLoadingAnimation
import evgeniy.ryzhikov.guesstheflag.viewmodel.StatisticViewModel
import javax.inject.Inject

const val KEY_PARCELABLE_ROUNDRESULT = "RoundResult"
class StatisticActivity : AppCompatActivity(), StartingLoadingAnimation {
    private lateinit var binding: ActivityStatisticBinding
    private var backPressed = 0L
    private lateinit var recyclerView: RecyclerView
    private var adapter = StatisticAdapter()

    private val viewModel: StatisticViewModel by viewModels()

    @Inject
    lateinit var energy: Energy
    @Inject
    lateinit var media: MediaPlayerController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticBinding.inflate(layoutInflater)
        setContentView(binding.root)
        App.instance.dagger.inject(this)
        startLoadingAnimation()
        lifecycle.addObserver(viewModel)
        initRV()

        val roundResult = intent.extras?.getBundle(KEY_PARCELABLE_ROUNDRESULT)
        if (roundResult != null) {
            addRoundStatistic(roundResult.get(KEY_PARCELABLE_ROUNDRESULT) as RoundResult)
            displayButtonPlayAgain()
        }

//        val roundResult = viewModel.roundResult
//        if (roundResult.countQuestions != 0) {
//            addRoundStatistic(roundResult)
//            displayButtonPlayAgain()
//        }
        setButtonOnClickListener()
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        viewModel.statisticLiveData.observe(this) { statisticData ->
            addTotalStatistic(statisticData)
            stopLoadingAnimation()
        }

        HideNavigationBars.hide(window, binding.root)
        getStatisticData()

        startLoadingAnimation()
    }


    private fun getStatisticData() {
        viewModel.getStatisticData()
    }


    private fun addRoundStatistic(roundResult: RoundResult) {
        adapter.addItem(StatHeader(getString(R.string.statistic_round_title)))
        adapter.addItem(StatField(R.drawable.ic_answer_correct, getString(R.string.statistic_correct_answer), roundResult.countCorrectAnswers.toString()))
        adapter.addItem(StatField(R.drawable.ic_answer_wrong, getString(R.string.statistic_wrong_answer), roundResult.countWrongAnswers.toString()))
        adapter.addItem(StatField(R.drawable.ic_coin, getString(R.string.statistic_round_points), roundResult.points.toString()))
        adapter.update()
    }

    private fun displayButtonPlayAgain() {
        binding.btnAgain.visibility = View.VISIBLE
    }

    private fun addTotalStatistic(statisticData: StatisticData) {
        adapter.addItem(StatHeader(getString(R.string.statistic_total_title)))
        adapter.addItem(StatField(R.drawable.ic_star, getString(R.string.statistic_total_game), statisticData.totalGame.toString()))
        adapter.addItem(StatField(R.drawable.ic_question, getString(R.string.statistic_total_answers), statisticData.totalQuestions.toString()))
        adapter.addItem(StatField(R.drawable.ic_answer_correct, getString(R.string.statistic_correct_answer), statisticData.totalCorrect.toString()))
        adapter.addItem(StatField(R.drawable.ic_answer_wrong, getString(R.string.statistic_wrong_answer), statisticData.totalWrong.toString()))
        adapter.addItem(StatField(R.drawable.ic_percent, getString(R.string.statistic_percent_correct_answers),statisticData.totalPercentCorrect))
        adapter.addItem(StatField(R.drawable.ic_coin, getString(R.string.statistic_all_points), statisticData.totalPoints.toString()))
        adapter.update()
    }

    override fun startLoadingAnimation() {
        binding.loadingAnimation.playAnimation()
        binding.loadingAnimation.visibility = View.VISIBLE
        binding.containerForLoadAnim.visibility = View.VISIBLE
    }
    override fun stopLoadingAnimation() {
        binding.loadingAnimation.cancelAnimation()
        binding.loadingAnimation.visibility = View.GONE
        binding.containerForLoadAnim.visibility = View.GONE
    }
    private fun initRV() {
        recyclerView = binding.statRecycler
        recyclerView.adapter = adapter
    }

    private fun setButtonOnClickListener() {
        binding.btnBack.setOnClickListener {
            media.playSound(MediaPlayerController.SoundEvent.CLICK_BUTTON)
            startMainMenu()
        }

        binding.btnAgain.setOnClickListener {
            media.playSound(MediaPlayerController.SoundEvent.CLICK_BUTTON)
            checkEnergyAndStartGame()
        }
    }

    private fun startMainMenu() {
        media.stopMusic = false
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun checkEnergyAndStartGame() {
        if (energy.isHave()) {
            energy.use()
            media.stopMusic = false
            val intent = Intent(this, GameMainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            NotEnoughEnergyFragment(this).show(supportFragmentManager, TAG_NOT_ENOUGH_ENERGY)
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

    override fun onDestroy() {
        super.onDestroy()
        adapter.clear()
        viewModel.resetRoundResult()
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