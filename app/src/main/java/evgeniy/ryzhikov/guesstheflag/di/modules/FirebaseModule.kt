package evgeniy.ryzhikov.guesstheflag.di.modules

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import evgeniy.ryzhikov.guesstheflag.data.FirebaseStorageAdapter
import evgeniy.ryzhikov.guesstheflag.data.FirebaseUserUid
import evgeniy.ryzhikov.guesstheflag.data.preferences.PreferenceProvider
import javax.inject.Singleton

@Module
class FirebaseModule {
    @Provides
    fun provideFirebaseFirestore() = Firebase.firestore

    @Provides
    fun provideFirebaseUid(preference: PreferenceProvider) = FirebaseUserUid(preference)

    @Singleton
    @Provides
    fun provideFirebaseStoreAdapter(firebaseUserUid: FirebaseUserUid, db: FirebaseFirestore) = FirebaseStorageAdapter(firebaseUserUid, db)
}