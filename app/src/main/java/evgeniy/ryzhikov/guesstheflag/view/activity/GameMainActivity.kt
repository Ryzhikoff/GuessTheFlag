package evgeniy.ryzhikov.guesstheflag.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import evgeniy.ryzhikov.guesstheflag.databinding.ActivityGameMainBinding
import evgeniy.ryzhikov.guesstheflag.domain.questions.QuestionManager
import evgeniy.ryzhikov.guesstheflag.utils.RoundTimer
import android.content.Intent
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.AppCompatTextView
import evgeniy.ryzhikov.guesstheflag.App
import evgeniy.ryzhikov.guesstheflag.R
import evgeniy.ryzhikov.guesstheflag.settings.*

class GameMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameMainBinding
    private lateinit var questionManager: QuestionManager
    private lateinit var roundTimer: RoundTimer
    private var timerCount = 10
    private var backPressed = 0L

    private val viewModel = App.instance.mainGameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        questionManager = QuestionManager(this)

        lifecycle.addObserver(viewModel)

        setListenerButtons()

        setInfoPanel()
        newRound()
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private fun setListenerButtons() {
        binding.answer1.setOnClickListener { processingAnswer((it as AppCompatTextView).text.toString()) }
        binding.answer2.setOnClickListener { processingAnswer((it as AppCompatTextView).text.toString()) }
        binding.answer3.setOnClickListener { processingAnswer((it as AppCompatTextView).text.toString()) }
        binding.answer4.setOnClickListener { processingAnswer((it as AppCompatTextView).text.toString()) }
    }

    private fun setInfoPanel() {
        binding.tvCounterCorrect.text = "0"
        binding.tvCounterWrong.text = "0"
    }
    private fun newRound() {
        setQuestionAndAnswerOnUi()
        startTimer()
    }

    private fun setQuestionAndAnswerOnUi() {
        binding.ivQuestion.setImageDrawable(questionManager.drawableQuestion)
        binding.answer1.text = questionManager.answers[0]
        binding.answer2.text = questionManager.answers[1]
        binding.answer3.text = questionManager.answers[2]
        binding.answer4.text = questionManager.answers[3]
    }

    private fun startTimer() {
        binding.tvTimer.text = (ROUND_TIME_IN_MILLIS / 1000).toString()
        timerCount = ROUND_TIME_IN_MILLIS / 1000
        roundTimer = RoundTimer(ROUND_TIME_IN_MILLIS.toLong())
        roundTimer.setOnTickListener {
            setTextTimer((--timerCount).toString())
        }
        roundTimer.setOnFinishListener {
            roundTimer.stop()
            runOnUiThread {
                processingAnswer("")
            }
        }
        roundTimer.start()
    }

    private fun setTextTimer(timer: String) {
        runOnUiThread {
            binding.tvTimer.text = timer
        }
    }

    private fun processingAnswer(answer: String) {
        roundTimer.stop()

        viewModel.scoring(questionManager.isCorrectAnswer(answer), timerCount)

        binding.tvCounterCorrect.text = viewModel.counterCorrectAnswers.toString()
        binding.tvCounterWrong.text = viewModel.counterWrongAnswers.toString()

        prepareNewRound()
    }

    private fun prepareNewRound() {
        if (questionManager.isHaveNextQuestion()) {
            newRound()
        } else {
            endGame()
        }
    }

    private fun endGame() {
        viewModel.saveStatistic()
        //statistic.save()
        //val bundle = Bundle()
        //bundle.putParcelable("statistic", statistic.getRoundStatistic())
        val intent = Intent(this, StatisticActivity::class.java)
        //intent.putExtra("stat", bundle)
        startActivity(intent)
        finish()
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