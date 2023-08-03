package evgeniy.ryzhikov.guesstheflag.domain.statistic.rv

data class RatingField(
    val position: String,
    val name: String,
    val points: Int,
    val games: Int,
    val percentWin: String
) : StatItem
