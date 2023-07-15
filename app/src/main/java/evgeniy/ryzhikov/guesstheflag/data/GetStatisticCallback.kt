package evgeniy.ryzhikov.guesstheflag.data

import evgeniy.ryzhikov.guesstheflag.domain.statistic.StatisticData

interface GetStatisticCallback {
    fun onSuccess(statisticData: StatisticData)
    fun onFailure()
}