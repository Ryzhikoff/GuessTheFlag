package evgeniy.ryzhikov.guesstheflag.statistic

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import evgeniy.ryzhikov.guesstheflag.settings.NUMBER_OF_QUESTION_PER_ROUND
import evgeniy.ryzhikov.guesstheflag.settings.POINTS_FOR_WRONG_FLAG_COUNTRY
import evgeniy.ryzhikov.guesstheflag.settings.POINTS_FOR_WRONG_FLAG_REGION
import evgeniy.ryzhikov.guesstheflag.settings.POINTS_FOR_WRONG_MAP_COUNTRY
import evgeniy.ryzhikov.guesstheflag.settings.POINTS_FOR_WRONG_MAP_REGION
import evgeniy.ryzhikov.guesstheflag.settings.STATISTIC_MULTIPLIER_FLAG_COUNTRY
import evgeniy.ryzhikov.guesstheflag.settings.STATISTIC_MULTIPLIER_FLAG_REGION
import evgeniy.ryzhikov.guesstheflag.settings.STATISTIC_MULTIPLIER_MAP_COUNTRY
import evgeniy.ryzhikov.guesstheflag.settings.STATISTIC_MULTIPLIER_MAP_REGION
import evgeniy.ryzhikov.guesstheflag.utils.GameMode
import evgeniy.ryzhikov.guesstheflag.utils.Mode

const val PREFERENCES_STATISTIC = "statistic"
const val PREFERENCES_STATISTIC_TOTAL_ANSWERS = "total_answers"
const val PREFERENCES_STATISTIC_CORRECT_ANSWERS = "correct_answers"
const val PREFERENCES_STATISTIC_WRONG_ANSWERS = "wrong_answers"
const val PREFERENCES_STATISTIC_TOTAL_POINTS = "total_points"

class Statistic(private val context: Context) {
    var counterCorrectAnswers = 0
        private set
    var counterWrongAnswers = 0
        private set

    private var points = 0

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

    fun getRoundStatistic(): RoundStatistic {
        return RoundStatistic(counterCorrectAnswers, counterWrongAnswers, points)
    }

    fun save() {
        val sharedPreferences = context.getSharedPreferences(PREFERENCES_STATISTIC, AppCompatActivity.MODE_PRIVATE)

        val totalAnswers = sharedPreferences.getInt(PREFERENCES_STATISTIC_TOTAL_ANSWERS, 0) + NUMBER_OF_QUESTION_PER_ROUND
        val totalCorrectAnswer = sharedPreferences.getInt(PREFERENCES_STATISTIC_CORRECT_ANSWERS, 0) + counterCorrectAnswers
        val totalWrongAnswer = sharedPreferences.getInt(PREFERENCES_STATISTIC_WRONG_ANSWERS, 0) + counterWrongAnswers
        val totalPoint = sharedPreferences.getInt(PREFERENCES_STATISTIC_TOTAL_POINTS, 0) + points

        val editor = sharedPreferences.edit()
        editor.putInt(PREFERENCES_STATISTIC_TOTAL_ANSWERS, totalAnswers)
        editor.putInt(PREFERENCES_STATISTIC_CORRECT_ANSWERS, totalCorrectAnswer)
        editor.putInt(PREFERENCES_STATISTIC_WRONG_ANSWERS, totalWrongAnswer)
        editor.putInt(PREFERENCES_STATISTIC_TOTAL_POINTS, totalPoint)
        editor.apply()
    }
}