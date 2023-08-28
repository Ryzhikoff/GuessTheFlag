package evgeniy.ryzhikov.guesstheflag.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import evgeniy.ryzhikov.guesstheflag.App
import evgeniy.ryzhikov.guesstheflag.data.FirebaseStorageAdapter
import evgeniy.ryzhikov.guesstheflag.data.FirebaseUserUid
import evgeniy.ryzhikov.guesstheflag.data.callbacks.GetPlayerEnvironmentCallback
import evgeniy.ryzhikov.guesstheflag.data.callbacks.GetRatingCallback
import evgeniy.ryzhikov.guesstheflag.domain.PlayerEnvironment
import evgeniy.ryzhikov.guesstheflag.domain.statistic.StatisticData
import javax.inject.Inject

class RatingViewModel(): ViewModel() {
    val ratingListLiveData = MutableLiveData<ArrayList<StatisticData>>()
    val playerEnvironmentLiveData = MutableLiveData<PlayerEnvironment>()

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
            override fun onSuccess(playerEnvironment: PlayerEnvironment) {
                playerEnvironmentLiveData.postValue(playerEnvironment)
            }

            override fun onFailure(e: Exception) {
            }

        })
    }
}