package evgeniy.ryzhikov.guesstheflag.domain

import evgeniy.ryzhikov.guesstheflag.domain.statistic.StatisticData

data class PlayerEnvironment(
    val playerPosition: Int,
    val playerStatisticData: StatisticData,
    val onTopPlayerStatisticData: StatisticData,
    val onBellowPlayerStatisticData: StatisticData
) {
}