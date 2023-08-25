package evgeniy.ryzhikov.guesstheflag.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import evgeniy.ryzhikov.guesstheflag.App
import evgeniy.ryzhikov.guesstheflag.data.FirebaseStorageAdapter
import evgeniy.ryzhikov.guesstheflag.data.FirebaseUserUid
import evgeniy.ryzhikov.guesstheflag.data.callbacks.GetPlayerEnvironmentCallback
import evgeniy.ryzhikov.guesstheflag.data.callbacks.GetRatingCallback
import evgeniy.ryzhikov.guesstheflag.domain.statistic.StatisticData
import javax.inject.Inject

class RatingViewModel(): ViewModel() {
    val ratingListLiveData = MutableLiveData<ArrayList<StatisticData>>()
    val playerPositionLiveData = MediatorLiveData<Int>()
    var playerList = ArrayList<StatisticData>()

    @Inject
    lateinit var fsa: FirebaseStorageAdapter
    @Inject
    lateinit var firebaseUserUid: FirebaseUserUid

    init {
        App.instance.dagger.inject(this)
    }

    fun getRating() {
        fsa.getTop10(object : GetRatingCallback {
            override fun onSuccess(ratingList: ArrayList<StatisticData>) {
                ratingList.sortByDescending {statisticData ->
                    statisticData.totalPoints
                }
                ratingListLiveData.postValue(ratingList)
            }

            override fun onFailure(e: Exception) {
            }
        })
        getPlayerEnvironment()
    }

    private fun getPlayerEnvironment() {
        fsa.getPlayerEnvironment(object : GetPlayerEnvironmentCallback{
            override fun onSuccess(
                ratingList: ArrayList<StatisticData>,
                playerPositionLiveData: MutableLiveData<Int>
            ) {
                playerList = ratingList
                this@RatingViewModel.playerPositionLiveData.addSource(playerPositionLiveData) {
                    this@RatingViewModel.playerPositionLiveData.value = it
                }
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

}