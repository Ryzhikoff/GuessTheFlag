package evgeniy.ryzhikov.guesstheflag.data.callbacks

import evgeniy.ryzhikov.guesstheflag.domain.statistic.StatisticData

interface GetRatingCallback {
    fun onSuccess(ratingList: ArrayList<StatisticData>)
    fun onFailure(e: Exception)

}