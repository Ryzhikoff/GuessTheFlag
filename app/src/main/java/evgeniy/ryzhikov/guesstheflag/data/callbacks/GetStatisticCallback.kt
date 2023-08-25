package evgeniy.ryzhikov.guesstheflag.data.callbacks

import evgeniy.ryzhikov.guesstheflag.domain.statistic.StatisticData

interface GetStatisticCallback {
    fun onSuccess(statisticData: StatisticData)
    fun onFailure(e: Exception)
}