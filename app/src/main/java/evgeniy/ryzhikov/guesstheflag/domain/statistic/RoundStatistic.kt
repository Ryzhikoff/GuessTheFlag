package evgeniy.ryzhikov.guesstheflag.domain.statistic

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RoundStatistic(
    val countCorrectAnswer: Int,
    val countWrongAnswer: Int,
    val points: Int) : Parcelable
