package evgeniy.ryzhikov.guesstheflag.viewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import evgeniy.ryzhikov.guesstheflag.data.FirebaseStorageAdapter
import evgeniy.ryzhikov.guesstheflag.data.FirebaseUserUid
import evgeniy.ryzhikov.guesstheflag.data.GetRatingCallback
import evgeniy.ryzhikov.guesstheflag.domain.statistic.StatisticData

class RatingViewModel(): ViewModel(), LifecycleObserver {
    private val fsa = FirebaseStorageAdapter.getInstance()
    var ratingListLiveData = MutableLiveData<ArrayList<StatisticData>>()
    var playerPositionInRatingLiveData = MutableLiveData<Int>()

    fun getRating() {
        fsa.getRatingList(object : GetRatingCallback {
            override fun onSuccess(ratingList: ArrayList<StatisticData>) {
                ratingList.sortByDescending {statisticData ->
                    statisticData.totalPoints
                }
                playerPositionInRatingLiveData.postValue(getPlayerPositionInRating(ratingList))
                ratingListLiveData.postValue(ratingList)
            }

            override fun onFailure(e: Exception) {
            }
        })
    }

    private fun getPlayerPositionInRating(ratingList: ArrayList<StatisticData>) : Int {
        val uid = FirebaseUserUid.getUid()
        ratingList.forEachIndexed{ index, statisticData ->
            if(statisticData.id.equals(uid)) {
                return index
            }
        }
        return -1
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun cleanData() {
        ratingListLiveData = MutableLiveData<ArrayList<StatisticData>>()
        playerPositionInRatingLiveData = MutableLiveData<Int>()
    }

}