package evgeniy.ryzhikov.guesstheflag.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import evgeniy.ryzhikov.guesstheflag.databinding.ActivityGameMainBinding
import evgeniy.ryzhikov.guesstheflag.domain.questions.QuestionManager
import android.content.Intent
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.lb.auto_fit_textview.AutoResizeTextView
import evgeniy.ryzhikov.guesstheflag.App
import evgeniy.ryzhikov.guesstheflag.R
import evgeniy.ryzhikov.guesstheflag.data.yandex_ads.YandexInterstitialAd
import evgeniy.ryzhikov.guesstheflag.data.yandex_ads.YandexAdCallback
import evgeniy.ryzhikov.guesstheflag.settings.ROUND_TIME_IN_MILLIS
import evgeniy.ryzhikov.guesstheflag.utils.HideNavigationBars
import evgeniy.ryzhikov.guesstheflag.utils.MediaPlayerController
import evgeniy.ryzhikov.guesstheflag.viewmodel.GameMainViewModel
import javax.inject.Inject

class GameMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameMainBinding
    private lateinit var questionManager: QuestionManager
    private var timerCount = (ROUND_TIME_IN_MILLIS / 1000).toInt()
    private var backPressed = 0L
    private lateinit var yandexInterstitialAd: YandexInterstitialAd

    private val viewModel: GameMainViewModel by viewModels()
    private lateinit var listOfButtons: List<AutoResizeTextView>
    private val colorChangeButtons = ArrayList<AutoResizeTextView>()

    @Inject
    lateinit var media: MediaPlayerController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listOfButtons = listOf(binding.answer1, binding.answer2, binding.answer3, binding.answer4)

        App.instance.dagger.inject(this)

        if (viewModel.isLaunched()) {
            startStatisticActivity()
        } else {
            viewModel.saveState(true)
        }

        questionManager = QuestionManager(this)

        lifecycle.addObserver(viewModel)

        setListenerButtons()

        yandexInterstitialAd = YandexInterstitialAd(this)
        yandexInterstitialAd.loadAd()

        updateCountersAnswers()
        newRound()

        viewModel.timerLiveData.observe(this) { timeLeft ->
            timerCount = timeLeft
            if (timeLeft > 0) {
                setTextTimer(timeLeft.toString())
            } else {
                processingAnswer(null, "")
            }
        }

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
        HideNavigationBars.hide(window, binding.root)
    }

    private fun setListenerButtons() {
        listOfButtons.forEach { view ->
            view.setOnClickListener { processingAnswer(view, view.text.toString()) }
        }
        binding.clickNextQuestion.setOnClickListener {
            clickNextQuestion()
        }
    }

    private fun updateCountersAnswers() {
        binding.tvCounterCorrect.text = viewModel.counterCorrectAnswers.toString()
        binding.tvCounterWrong.text = viewModel.counterWrongAnswers.toString()
    }

    private fun newRound() {
        setQuestionAndAnswerOnUi()
        viewModel.newRound()
        setTextTimer((ROUND_TIME_IN_MILLIS / 1000).toString())
    }

    private fun setQuestionAndAnswerOnUi() {
        binding.ivQuestion.setImageDrawable(questionManager.drawableQuestion)

        listOfButtons.forEachIndexed { index, view ->
            view.text = questionManager.answers[index]
            view.isClickable = true
        }
    }

    private fun setTextTimer(timer: String) {
        binding.tvTimer.text = timer
    }

    private fun processingAnswer(clickedView: AutoResizeTextView?, answer: String) {
        viewModel.stopTimer()
        listOfButtons.forEach {
            it.isClickable = false
        }

        val isCorrectAnswer = questionManager.isCorrectAnswer(answer)
        playSoundAnswer(isCorrectAnswer)
        viewModel.scoring(isCorrectAnswer, timerCount)
        updateCountersAnswers()

        //Если ответ не дан ни какой - просто загружаем след вопрос
        if (clickedView != null) {
            colorChangeButtons.add(clickedView)
            if (isCorrectAnswer) {
                changeBackgroundView(clickedView, R.drawable.button_background_correct_answer)
            } else {
                listOfButtons.forEach { button ->
                    if (questionManager.isCorrectAnswer(button.text.toString())) {
                        colorChangeButtons.add(button)
                        changeBackgroundView(button, R.drawable.button_background_correct_answer)
                    }
                }
                changeBackgroundView(clickedView, R.drawable.button_background_wrong_answer)
            }
            binding.clickNextQuestion.visibility = View.VISIBLE
        } else {
            prepareNewRound()
        }
    }

    private fun playSoundAnswer(isCorrectAnswer: Boolean) {
        media.playSound(
            if (isCorrectAnswer)
                MediaPlayerController.SoundEvent.CORRECT_ANSWER
            else
                MediaPlayerController.SoundEvent.WRONG_ANSWER
        )
    }

    private fun changeBackgroundView(view: AutoResizeTextView, @DrawableRes res: Int) {
        val text = view.text.toString()
        view.apply {
            background = ContextCompat.getDrawable(this@GameMainActivity, res)
            this.text = text
            invalidate()
        }
    }

    private fun prepareNewRound() {
        if (questionManager.isHaveNextQuestion()) {
            newRound()
        } else {
            endGame()
        }
    }

    private fun clickNextQuestion() {
        colorChangeButtons.forEach {
            changeBackgroundView(it, R.drawable.button_background_answers)
        }
        binding.clickNextQuestion.visibility = View.GONE
        colorChangeButtons.clear()
        prepareNewRound()
    }

    private fun endGame() {
        viewModel.saveStatistic()
        media.stopMusic = false

        yandexInterstitialAd.showAds {
            startStatisticActivity()
        }
    }

    private fun startStatisticActivity() {

        val roundResult = viewModel.getRoundResult()
        val bundle = Bundle()
        bundle.putParcelable(KEY_PARCELABLE_ROUNDRESULT, roundResult)

        viewModel.saveState(false)

        val intent = Intent(this@GameMainActivity, StatisticActivity::class.java)
        intent.putExtra(KEY_PARCELABLE_ROUNDRESULT, bundle)
        startActivity(intent)
        this@GameMainActivity.finish()
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
            finish()
        } else {
            Toast.makeText(this, R.string.alert_double_tap_exit, Toast.LENGTH_SHORT).show()
        }
        backPressed = System.currentTimeMillis()
    }

    override fun onDestroy() {
        yandexInterstitialAd.clear()
        super.onDestroy()
    }

    companion object {
        const val TIME_INTERVAL = 2000
    }
}