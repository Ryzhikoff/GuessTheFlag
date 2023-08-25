package evgeniy.ryzhikov.guesstheflag.viewmodel

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import evgeniy.ryzhikov.guesstheflag.App
import evgeniy.ryzhikov.guesstheflag.data.FirebaseStorageAdapter
import evgeniy.ryzhikov.guesstheflag.data.FirebaseUserUid
import evgeniy.ryzhikov.guesstheflag.data.callbacks.GetStatisticCallback
import evgeniy.ryzhikov.guesstheflag.domain.RoundResult
import evgeniy.ryzhikov.guesstheflag.domain.statistic.StatisticData
import javax.inject.Inject

class StatisticViewModel : ViewModel(), DefaultLifecycleObserver {
    var statisticLiveData = MutableLiveData<StatisticData>()
    var roundResult = RoundResult()

    @Inject
    lateinit var fsa: FirebaseStorageAdapter
    @Inject
    lateinit var firebaseUserUid: FirebaseUserUid

    init {
        App.instance.dagger.inject(this)
    }

    fun getStatisticData() {
        val uid = firebaseUserUid.getUid()
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