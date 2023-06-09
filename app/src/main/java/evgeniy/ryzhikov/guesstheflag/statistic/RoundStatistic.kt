package evgeniy.ryzhikov.guesstheflag.statistic

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RoundStatistic(
    val countCorrectAnswer: Int,
    val countWrongAnswer: Int,
    val points: Int) : Parcelable
