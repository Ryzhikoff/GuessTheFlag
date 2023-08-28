package evgeniy.ryzhikov.guesstheflag.data

import android.util.Log
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import evgeniy.ryzhikov.guesstheflag.data.callbacks.GetPlayerEnvironmentCallback
import evgeniy.ryzhikov.guesstheflag.data.callbacks.GetRatingCallback
import evgeniy.ryzhikov.guesstheflag.data.callbacks.GetStatisticCallback
import evgeniy.ryzhikov.guesstheflag.domain.PlayerEnvironment
import evgeniy.ryzhikov.guesstheflag.domain.statistic.StatisticData
import evgeniy.ryzhikov.guesstheflag.settings.TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

const val FB_COLLECTION_NAME = "statistic"

class FirebaseStorageAdapter @Inject constructor(
    val firebaseUserUid: FirebaseUserUid,
    val db: FirebaseFirestore
) {

    private var cashedStatisticData: StatisticData? = null
    private var cashedRatingList: ArrayList<StatisticData>? = null

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

                CoroutineScope(Job()).launch {
                    val playerEnvironment = getPlayerEnvironment(playerPoints, statisticData)
                    callback.onSuccess(playerEnvironment)
                }

            }
    }

    private suspend fun getPlayerEnvironment(
        playerPoints: Int,
        playerStatisticData: StatisticData
    ): PlayerEnvironment = runBlocking {
        val onTopPlayer = getOnTop(playerPoints)
        val onBellowPlayer = getFromBellow(playerPoints)
        val playerPosition = getPlayerPosition(playerPoints)
        return@runBlocking PlayerEnvironment(playerPosition, playerStatisticData, onTopPlayer, onBellowPlayer)
    }

//    private fun getPlayerEnvironment(
//        playerPoints: Int,
//        playerStatisticData: StatisticData
//    ): PlayerEnvironment = runBlocking {
//        getPlayerPosition(playerPoints) { position ->
//            val onTopPlayer = getOnTop(playerPoints)
//            val onBellowPlayer = getFromBellow(playerPoints)
//            val playerPosition = position
//            return@runBlocking PlayerEnvironment(
//                playerPosition = playerPosition,
//                playerStatisticData = playerStatisticData,
//                onTopPlayerStatisticData = onTopPlayer,
//                onBellowPlayerStatisticData = onBellowPlayer
//            )
//        }
//    }


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

    private suspend fun getPlayerPosition(playerPoints: Int): Int = suspendCoroutine { continuation ->
        val query = db.collection(FB_COLLECTION_NAME)
            .whereGreaterThan("totalPoints", playerPoints)
            .orderBy("totalPoints")
        val countQuery = query.count()

        countQuery.get(AggregateSource.SERVER)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val snapshot = task.result
                    val result = snapshot.count.toInt()
                    Log.d(TAG, "Count: ${snapshot.count}")
                    continuation.resume(result)
                } else {
                    Log.d(TAG, "Count failed: ${task.toString()}")
                    continuation.resumeWithException(Exception("Failed to get player position"))
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