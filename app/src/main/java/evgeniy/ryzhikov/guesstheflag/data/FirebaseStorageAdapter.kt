package evgeniy.ryzhikov.guesstheflag.data

import android.util.Log
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import evgeniy.ryzhikov.guesstheflag.data.callbacks.GetPlayerEnvironmentCallback
import evgeniy.ryzhikov.guesstheflag.data.callbacks.GetRatingCallback
import evgeniy.ryzhikov.guesstheflag.data.callbacks.GetStatisticCallback
import evgeniy.ryzhikov.guesstheflag.domain.statistic.StatisticData
import evgeniy.ryzhikov.guesstheflag.settings.TAG
import evgeniy.ryzhikov.guesstheflag.utils.SingleLiveEvent
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

const val FB_COLLECTION_NAME = "statistic"

class FirebaseStorageAdapter @Inject constructor(
    val firebaseUserUid: FirebaseUserUid,
    val db: FirebaseFirestore
) {

    private var cashedStatisticData: StatisticData? = null
    private var cashedRatingList: ArrayList<StatisticData>? = null
    private val playerPositionLiveData = SingleLiveEvent<Int>()

    fun put(statisticData: StatisticData) {
        cashedStatisticData = statisticData
        db.collection(FB_COLLECTION_NAME)
            .document(statisticData.id)
            .set(statisticData)
    }

    @Synchronized
    fun getPlayerStatisticData(uid: String, callback: GetStatisticCallback) {
        if (cashedStatisticData != null) {
            callback.onSuccess(cashedStatisticData!!)
        } else {

            val docRef = db.collection(FB_COLLECTION_NAME)
                .document(uid)
            docRef.get()
                .addOnSuccessListener { documentSnapshot ->

                    //Возвращаем статистику по игроку, если еще нет статистики - новый объект
                    val statisticData =
                        if (documentSnapshot.exists()) {
                            documentSnapshot.toObject<StatisticData>()!!
                        } else {
                            StatisticData(id = uid, name = firebaseUserUid.getName())
                        }
                    callback.onSuccess(statisticData)
                }
                .addOnFailureListener {
                    callback.onFailure(it)
                }
        }
    }

    fun getRatingList(callback: GetRatingCallback) {
        if (cashedRatingList != null) {
            callback.onSuccess(cashedRatingList!!)
        } else {
            val list = ArrayList<StatisticData>()
            db.collection(FB_COLLECTION_NAME)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        list.add(document.toObject<StatisticData>())
                    }
                    cashedRatingList = list
                    callback.onSuccess(list)
                }
                .addOnFailureListener {
                    callback.onFailure(it)
                }
        }
    }

    fun getTop10(callback: GetRatingCallback) {
        val list = ArrayList<StatisticData>()
        db.collection(FB_COLLECTION_NAME)
            .orderBy("totalPoints", Query.Direction.DESCENDING)
            .limit(10)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    list.add(document.toObject<StatisticData>())
                }
                callback.onSuccess(list)
            }
    }

    fun getPlayerEnvironment(callback: GetPlayerEnvironmentCallback) {
        var playerPoints: Int
        db.collection(FB_COLLECTION_NAME)
            .whereEqualTo("id", firebaseUserUid.getUid())
            .get()
            .addOnSuccessListener {
                val statisticData = it.documents[0].toObject<StatisticData>()
                playerPoints = statisticData!!.totalPoints
                val playersList = getPlayerEnvironment(playerPoints, statisticData)

                callback.onSuccess(playersList, playerPositionLiveData)
            }
    }

    private fun getPlayerEnvironment(
        playerPoints: Int,
        playerStatisticData: StatisticData
    ): ArrayList<StatisticData> = runBlocking {

        val onTopPlayer = getOnTop(playerPoints)
        Log.d(TAG, "onTopPlayer: $onTopPlayer")
        val onBellowPlayer = getFromBellow(playerPoints)
        Log.d(TAG, "onBellowPlayer: $onBellowPlayer")

        getPlayerPosition(playerPoints)

        return@runBlocking arrayListOf<StatisticData>(playerStatisticData, onTopPlayer, onBellowPlayer)
    }

    private suspend fun getOnTop(playerPoints: Int): StatisticData {
        val snap = db.collection(FB_COLLECTION_NAME)
            .whereGreaterThan("totalPoints", playerPoints)
            .orderBy("totalPoints")
            .limit(1)
            .get()
            .await()

        return if (snap.documents.size != 0) {
            snap.documents[0].toObject<StatisticData>()!!
        } else {
            StatisticData()
        }
    }

    private suspend fun getFromBellow(playerPoints: Int): StatisticData {
        val snap = db.collection(FB_COLLECTION_NAME)
            .whereLessThan("totalPoints", playerPoints)
            .orderBy("totalPoints", Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .await()

        return if (snap.documents.size != 0) {
            snap.documents[0].toObject<StatisticData>()!!
        } else {
            StatisticData()
        }
    }

    private suspend fun getPlayerPosition(playerPoints: Int) {
        val query = db.collection(FB_COLLECTION_NAME)
            .whereGreaterThan("totalPoints", playerPoints)
            .orderBy("totalPoints")
        val countQuery = query.count()
        countQuery.get(AggregateSource.SERVER)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val snapshot = task.result
                    playerPositionLiveData.postValue(snapshot.count.toInt() + 1)
                    Log.d(TAG, "Count: ${snapshot.count}")
                } else {
                    Log.d(TAG, "Count failed: ${task.toString()}")
                }
            }
    }

    fun clearRatingList() {
        cashedRatingList = null
    }

    fun clearPlayerStatisticData() {
        cashedStatisticData = null
    }
}