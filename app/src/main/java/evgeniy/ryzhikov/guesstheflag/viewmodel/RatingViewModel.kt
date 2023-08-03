package evgeniy.ryzhikov.guesstheflag.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import evgeniy.ryzhikov.guesstheflag.data.FirebaseStorageAdapter
import evgeniy.ryzhikov.guesstheflag.data.FirebaseUserUid
import evgeniy.ryzhikov.guesstheflag.data.GetRatingCallback
import evgeniy.ryzhikov.guesstheflag.domain.statistic.StatisticData

class RatingViewModel(): ViewModel() {
    private val fsa = FirebaseStorageAdapter.getInstance()
    var ratingListLiveData = MutableLiveData<ArrayList<StatisticData>>()
    val playerPositionInRatingLiveData = MutableLiveData<Int>()

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
        val uid = FirebaseUserUid.get()
        ratingList.forEachIndexed{ index, statisticData ->
            if(statisticData.id.equals(uid)) {
                return index
            }
        }
        return -1
    }
}