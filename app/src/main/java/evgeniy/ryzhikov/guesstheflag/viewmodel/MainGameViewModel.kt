package evgeniy.ryzhikov.guesstheflag.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import evgeniy.ryzhikov.guesstheflag.data.FirebaseStorageAdapter
import evgeniy.ryzhikov.guesstheflag.data.FirebaseUserUid
import evgeniy.ryzhikov.guesstheflag.data.GetStatisticCallback
import evgeniy.ryzhikov.guesstheflag.domain.GameMode
import evgeniy.ryzhikov.guesstheflag.domain.Mode
import evgeniy.ryzhikov.guesstheflag.domain.questions.QuestionManager
import evgeniy.ryzhikov.guesstheflag.domain.statistic.StatisticData
import kotlin.math.round
import kotlin.math.roundToInt

class MainGameViewModel(application: Application) : AndroidViewModel(application) {
    private val fsa = FirebaseStorageAdapter()


    private lateinit var questionManager : QuestionManager

    fun startGame() {
        questionManager = QuestionManager(getApplication())
    }

    fun saveStatistic(roundResult: RoundResult) {

        fsa.get(FirebaseUserUid.get(), object : GetStatisticCallback {
            override fun onSuccess(statisticData: StatisticData) {

                val newStatisticData = when (GameMode.mode) {
                    Mode.COUNTRY_FLAG -> addCountryFlagStatistic(statisticData, roundResult)
                    Mode.REGION_FLAG -> addRegionFlagStatistic(statisticData, roundResult)
                    Mode.COUNTRY_MAP -> addCountryMapStatistic(statisticData, roundResult)
                    Mode.REGION_MAP -> addRegionMapStatistic(statisticData, roundResult)
                }.apply {
                    name = FirebaseUserUid.getName()
                    totalGame += 1
                    totalQuestions += roundResult.countQuestions
                    totalCorrect += roundResult.countCorrectAnswers
                    totalWrong += roundResult.countWrongAnswers
                    totalPoints += roundResult.points
                    totalPercentCorrect = getFormattedPercentString(totalCorrect, totalQuestions)
                }

                fsa.put(newStatisticData)
            }

            override fun onFailure() {
            }
        })

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

    data class RoundResult(
        val countQuestions: Int,
        val countCorrectAnswers: Int,
        val countWrongAnswers: Int,
        val points: Int
    )

}