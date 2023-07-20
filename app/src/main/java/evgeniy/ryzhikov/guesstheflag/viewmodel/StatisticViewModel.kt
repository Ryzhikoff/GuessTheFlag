package evgeniy.ryzhikov.guesstheflag.viewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import evgeniy.ryzhikov.guesstheflag.data.FirebaseStorageAdapter
import evgeniy.ryzhikov.guesstheflag.data.FirebaseUserUid
import evgeniy.ryzhikov.guesstheflag.data.GetStatisticCallback
import evgeniy.ryzhikov.guesstheflag.domain.RoundResult
import evgeniy.ryzhikov.guesstheflag.domain.statistic.StatisticData

class StatisticViewModel: ViewModel(), LifecycleObserver {
    private val fsa = FirebaseStorageAdapter()
    var statisticLiveData = MutableLiveData<StatisticData>()
    var roundResult = RoundResult()



    fun getStatisticData() {
        val uid = FirebaseUserUid.get()
        println("!!! StatisticViewModel .getStatisticData")
        fsa.get(uid, object : GetStatisticCallback {
            override fun onSuccess(statisticData: StatisticData) {
                println("!!! StatisticViewModel .getStatisticData .get onSuccess")
                statisticLiveData.postValue(statisticData)
            }

            override fun onFailure() {
            }

        })
    }

    //Обнуляем результаты раунда, что бы они были активны только после окончания раунда
    fun resetRoundResult() {
        roundResult = RoundResult()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun cleanData() {
        statisticLiveData = MutableLiveData<StatisticData>()
    }

}