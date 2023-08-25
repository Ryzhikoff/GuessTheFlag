package evgeniy.ryzhikov.guesstheflag.data.callbacks

import androidx.lifecycle.MutableLiveData
import evgeniy.ryzhikov.guesstheflag.domain.statistic.StatisticData

interface GetPlayerEnvironmentCallback {
    fun onSuccess(playerList: ArrayList<StatisticData>, playerPositionLiveData: MutableLiveData<Int>)
    fun onFailure(e: Exception)
}