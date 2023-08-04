package evgeniy.ryzhikov.guesstheflag.data

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import evgeniy.ryzhikov.guesstheflag.data.preferences.PreferenceProvider
import evgeniy.ryzhikov.guesstheflag.data.preferences.Preferences.*


object FirebaseUserUid {
    private val preference = PreferenceProvider.getInstance()

    fun getUid() : String {
        val uid: String
        if (preference.contains(PreferenceName.SETTINGS, PreferenceKey.UID)) {
            uid = preference.getString(PreferenceName.SETTINGS, PreferenceKey.UID)
        } else {
            uid = Firebase.auth.uid!!
            preference.putString(PreferenceName.SETTINGS, PreferenceKey.UID, uid)
        }
        return uid
    }

    fun getName() : String {
        val name : String
        if (preference.contains(PreferenceName.SETTINGS, PreferenceKey.NAME)) {
            name = preference.getString(PreferenceName.SETTINGS, PreferenceKey.NAME)
        } else {
            name = Firebase.auth.currentUser?.displayName!!
            preference.putString(PreferenceName.SETTINGS, PreferenceKey.NAME, name)
        }
        return name
    }

    fun setName(name: String)  {
        preference.putString(PreferenceName.SETTINGS, PreferenceKey.NAME, name)
    }

}