package evgeniy.ryzhikov.guesstheflag.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RoundResult(
    val countQuestions: Int = 0,
    val countCorrectAnswers: Int = 0,
    val countWrongAnswers: Int = 0,
    val points: Int = 0
) : Parcelable
