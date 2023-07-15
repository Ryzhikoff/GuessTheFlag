package evgeniy.ryzhikov.guesstheflag.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import evgeniy.ryzhikov.guesstheflag.data.FirebaseStorageAdapter
import evgeniy.ryzhikov.guesstheflag.data.FirebaseUserUid
import evgeniy.ryzhikov.guesstheflag.data.GetStatisticCallback
import evgeniy.ryzhikov.guesstheflag.domain.statistic.StatisticData

class StatisticViewModel: ViewModel() {
    val fsa = FirebaseStorageAdapter()
    val statisticLiveData = MutableLiveData<StatisticData>()

    fun getStatistic() {
        val uid = FirebaseUserUid.get()
        fsa.get(uid, object : GetStatisticCallback {
            override fun onSuccess(statisticData: StatisticData) {
                statisticLiveData.postValue(statisticData)
            }

            override fun onFailure() {
            }

        })
    }

}