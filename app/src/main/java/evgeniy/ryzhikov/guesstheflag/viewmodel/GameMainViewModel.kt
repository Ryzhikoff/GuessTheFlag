package evgeniy.ryzhikov.guesstheflag.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import evgeniy.ryzhikov.guesstheflag.App
import evgeniy.ryzhikov.guesstheflag.data.FirebaseStorageAdapter
import evgeniy.ryzhikov.guesstheflag.data.FirebaseUserUid
import evgeniy.ryzhikov.guesstheflag.data.callbacks.GetStatisticCallback
import evgeniy.ryzhikov.guesstheflag.domain.GameMode
import evgeniy.ryzhikov.guesstheflag.domain.Mode
import evgeniy.ryzhikov.guesstheflag.domain.RoundResult
import evgeniy.ryzhikov.guesstheflag.domain.statistic.StatisticData
import evgeniy.ryzhikov.guesstheflag.settings.NUMBER_OF_QUESTION_PER_ROUND
import evgeniy.ryzhikov.guesstheflag.settings.POINTS_FOR_WRONG_FLAG_COUNTRY
import evgeniy.ryzhikov.guesstheflag.settings.POINTS_FOR_WRONG_FLAG_REGION
import evgeniy.ryzhikov.guesstheflag.settings.POINTS_FOR_WRONG_MAP_COUNTRY
import evgeniy.ryzhikov.guesstheflag.settings.POINTS_FOR_WRONG_MAP_REGION
import evgeniy.ryzhikov.guesstheflag.settings.STATISTIC_MULTIPLIER_FLAG_COUNTRY
import evgeniy.ryzhikov.guesstheflag.settings.STATISTIC_MULTIPLIER_FLAG_REGION
import evgeniy.ryzhikov.guesstheflag.settings.STATISTIC_MULTIPLIER_MAP_COUNTRY
import evgeniy.ryzhikov.guesstheflag.settings.STATISTIC_MULTIPLIER_MAP_REGION
import javax.inject.Inject
import kotlin.math.roundToInt

class GameMainViewModel(application: Application) : AndroidViewModel(application), DefaultLifecycleObserver {

    var counterCorrectAnswers = 0
        private set
    var counterWrongAnswers = 0
        private set

    private var points = 0

    @Inject
    lateinit var fsa: FirebaseStorageAdapter
    @Inject
    lateinit var firebaseUserUid: FirebaseUserUid
    init {
        App.instance.dagger.inject(this)
    }
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        //questionManager = QuestionManager(getApplication())
        counterCorrectAnswers = 0
        counterWrongAnswers = 0
        points = 0
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        fsa.clearRatingList()
        fsa.clearPlayerStatisticData()
    }

    fun scoring(isCorrectAnswer: Boolean, timerCount: Int = 0) {
        if (isCorrectAnswer) {
            counterCorrectAnswers++
            points += when (GameMode.mode) {
                Mode.COUNTRY_FLAG -> timerCount * STATISTIC_MULTIPLIER_FLAG_COUNTRY
                Mode.REGION_FLAG -> timerCount * STATISTIC_MULTIPLIER_FLAG_REGION
                Mode.COUNTRY_MAP -> timerCount * STATISTIC_MULTIPLIER_MAP_COUNTRY
                Mode.REGION_MAP -> timerCount * STATISTIC_MULTIPLIER_MAP_REGION
                else -> 0
            }
        } else {
            counterWrongAnswers++
            points += when (GameMode.mode) {
                Mode.COUNTRY_FLAG -> POINTS_FOR_WRONG_FLAG_COUNTRY
                Mode.REGION_FLAG -> POINTS_FOR_WRONG_FLAG_REGION
                Mode.COUNTRY_MAP -> POINTS_FOR_WRONG_MAP_COUNTRY
                Mode.REGION_MAP -> POINTS_FOR_WRONG_MAP_REGION
                else -> 0
            }
        }
    }


    fun saveStatistic() {
        val roundResult = getRoundResult()
        fsa.getPlayerStatisticData(firebaseUserUid.getUid(), object : GetStatisticCallback {
            override fun onSuccess(statisticData: StatisticData) {
                val newStatisticData = when (GameMode.mode) {
                    Mode.COUNTRY_FLAG -> addCountryFlagStatistic(statisticData, roundResult)
                    Mode.REGION_FLAG -> addRegionFlagStatistic(statisticData, roundResult)
                    Mode.COUNTRY_MAP -> addCountryMapStatistic(statisticData, roundResult)
                    Mode.REGION_MAP -> addRegionMapStatistic(statisticData, roundResult)
                }.apply {
                    name = firebaseUserUid.getName()
                    totalGame += 1
                    totalQuestions += roundResult.countQuestions
                    totalCorrect += roundResult.countCorrectAnswers
                    totalWrong += roundResult.countWrongAnswers
                    totalPoints += roundResult.points
                    totalPercentCorrect = getFormattedPercentString(totalCorrect, totalQuestions)
                }
                fsa.put(newStatisticData)
            }

            override fun onFailure(e: Exception) {
            }
        })

    }

    fun getRoundResult() : RoundResult {
        return RoundResult(
            countQuestions = NUMBER_OF_QUESTION_PER_ROUND,
            countCorrectAnswers = counterCorrectAnswers,
            countWrongAnswers = counterWrongAnswers,
            points = points
        )
    }

    private fun addCountryFlagStatistic(statisticData: StatisticData, roundResult: RoundResult) : StatisticData {
        return statisticData.apply {
            countryFlagQuestions += roundResult.countQuestions
            countryFlagCorrect += roundResult.countCorrectAnswers
            countryFlagWrong += roundResult.countWrongAnswers
            countryFlagPoints += roundResult.points
            countryFlagPercentCorrect = getFormattedPercentString(countryFlagCorrect, countryFlagQuestions)
        }
    }

    private fun addRegionFlagStatistic(statisticData: StatisticData, roundResult: RoundResult) : StatisticData {
        return statisticData.apply {
            regionFlagQuestions += roundResult.countQuestions
            regionFlagCorrect += roundResult.countCorrectAnswers
            regionFlagWrong += roundResult.countWrongAnswers
            regionFlagPoints += roundResult.points
            regionFlagPercentCorrect = getFormattedPercentString(regionFlagCorrect, regionFlagQuestions)
        }
    }

    private fun addCountryMapStatistic(statisticData: StatisticData, roundResult: RoundResult) : StatisticData {
        return statisticData.apply {
            countryMapQuestions += roundResult.countQuestions
            countryMapCorrect += roundResult.countCorrectAnswers
            countryMapWrong += roundResult.countWrongAnswers
            countryMapPoints += roundResult.points
            countryMapPercentCorrect = getFormattedPercentString(countryMapCorrect, countryMapQuestions)
        }
    }

    private fun addRegionMapStatistic(statisticData: StatisticData, roundResult: RoundResult) : StatisticData {
        return statisticData.apply {
            regionMapQuestions += roundResult.countQuestions
            regionMapCorrect += roundResult.countCorrectAnswers
            regionMapWrong += roundResult.countWrongAnswers
            regionMapPoints += roundResult.points
            regionMapPercentCorrect = getFormattedPercentString(regionMapCorrect, regionMapQuestions)
        }
    }

    private fun getFormattedPercentString(correctAnswers: Int, totalQuestions: Int) : String {
        var percent = correctAnswers.toDouble() / totalQuestions * 100.0
        percent = (percent * 100).roundToInt() / 100.0
        return "$percent%"
    }

}