package evgeniy.ryzhikov.guesstheflag.data

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import evgeniy.ryzhikov.guesstheflag.domain.statistic.StatisticData

const val FB_COLLECTION_NAME = "statistic"

class FirebaseStorageAdapter private constructor(){
    private val db = Firebase.firestore
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
                            StatisticData(id = uid, name = FirebaseUserUid.getName())
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
            println("!!! возвращаем сохраненный список")
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
                .addOnFailureListener{
                    callback.onFailure(it)
                }
        }
    }

    fun clearRatingList() {
        cashedRatingList = null
    }
    fun clearPlayerStatisticData() {
        cashedStatisticData = null
    }

    companion object {
        private var instance: FirebaseStorageAdapter? = null
        fun getInstance(): FirebaseStorageAdapter {
            return instance ?: FirebaseStorageAdapter().also {
                instance = it
            }
        }

    }
}