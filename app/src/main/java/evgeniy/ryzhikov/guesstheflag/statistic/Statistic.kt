package evgeniy.ryzhikov.guesstheflag.statistic

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

class Statistic {
    var counterCorrectAnswers = 0
        private set
    var counterWrongAnswers = 0
        private set

    private var points = 0

    fun scoring(isCorrectAnswer: Boolean, timerCount: Int = 0) {
        if(isCorrectAnswer) {
            counterCorrectAnswers++
            points += when (GameMode.mode)  {
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

    fun getRoundStatistic() : RoundStatistic {
        return RoundStatistic(counterCorrectAnswers, counterWrongAnswers, points)
    }

    fun save() {
        //TODO сохранение статистики
    }
}