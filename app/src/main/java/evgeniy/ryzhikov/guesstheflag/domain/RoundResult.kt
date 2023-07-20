package evgeniy.ryzhikov.guesstheflag.domain

data class RoundResult(
    val countQuestions: Int = 0,
    val countCorrectAnswers: Int = 0,
    val countWrongAnswers: Int = 0,
    val points: Int = 0
)
