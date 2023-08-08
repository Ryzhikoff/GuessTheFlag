package evgeniy.ryzhikov.guesstheflag.viewmodel

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import evgeniy.ryzhikov.guesstheflag.data.FirebaseStorageAdapter
import evgeniy.ryzhikov.guesstheflag.data.FirebaseUserUid
import evgeniy.ryzhikov.guesstheflag.data.GetStatisticCallback
import evgeniy.ryzhikov.guesstheflag.domain.RoundResult
import evgeniy.ryzhikov.guesstheflag.domain.statistic.StatisticData

class StatisticViewModel : ViewModel(), DefaultLifecycleObserver {
    private val fsa = FirebaseStorageAdapter.getInstance()
    var statisticLiveData = MutableLiveData<StatisticData>()
    var roundResult = RoundResult()

    fun getStatisticData() {
        val uid = FirebaseUserUid.getUid()
        fsa.getPlayerStatisticData(uid, object : GetStatisticCallback {
            override fun onSuccess(statisticData: StatisticData) {
                statisticLiveData.postValue(statisticData)
            }

            override fun onFailure(e: Exception) {
            }
        })
    }

    //Обнуляем результаты раунда, что бы они были активны только после окончания раунда
    fun resetRoundResult() {
        roundResult = RoundResult()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        statisticLiveData = MutableLiveData<StatisticData>()
    }

}