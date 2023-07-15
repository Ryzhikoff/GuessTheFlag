package evgeniy.ryzhikov.guesstheflag.data

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import evgeniy.ryzhikov.guesstheflag.domain.statistic.StatisticData

const val FB_COLLECTION_NAME = "statistic"

class FirebaseStorageAdapter {
    private val db = Firebase.firestore

    fun put (statisticData: StatisticData) {
        db.collection(FB_COLLECTION_NAME)
            .document(statisticData.id)
            .set(statisticData)
    }

    @Synchronized fun get(uid: String, callback: GetStatisticCallback) {
        val docRef = db.collection(FB_COLLECTION_NAME)
            .document(uid)
        docRef.get()
            .addOnSuccessListener { documentSnapshot ->

            //Возвращаем статистику по игроку, если еще нет статистики - новый объект
            val statisticData =
                if (documentSnapshot.exists()) {
                    documentSnapshot.toObject<StatisticData>()!!
                } else {
                    StatisticData(id = uid)
                }

            callback.onSuccess(statisticData)
            }
            .addOnFailureListener {
                callback.onFailure()
            }
    }

    fun getTop(count: Int) {

    }
}