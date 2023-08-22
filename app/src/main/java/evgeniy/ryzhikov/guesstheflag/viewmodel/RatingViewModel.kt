package evgeniy.ryzhikov.guesstheflag.viewmodel

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import evgeniy.ryzhikov.guesstheflag.App
import evgeniy.ryzhikov.guesstheflag.data.FirebaseStorageAdapter
import evgeniy.ryzhikov.guesstheflag.data.FirebaseUserUid
import evgeniy.ryzhikov.guesstheflag.data.GetRatingCallback
import evgeniy.ryzhikov.guesstheflag.domain.statistic.StatisticData
import javax.inject.Inject

class RatingViewModel(): ViewModel(), DefaultLifecycleObserver {
    var ratingListLiveData = MutableLiveData<ArrayList<StatisticData>>()
    var playerPositionInRatingLiveData = MutableLiveData<Int>()

    @Inject
    lateinit var fsa: FirebaseStorageAdapter
    @Inject
    lateinit var firebaseUserUid: FirebaseUserUid

    init {
        App.instance.dagger.inject(this)
    }

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
        val uid = firebaseUserUid.getUid()
        ratingList.forEachIndexed{ index, statisticData ->
            if(statisticData.id.equals(uid)) {
                return index
            }
        }
        return -1
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        ratingListLiveData = MutableLiveData<ArrayList<StatisticData>>()
        playerPositionInRatingLiveData = MutableLiveData<Int>()
    }

}