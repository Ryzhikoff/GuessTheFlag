package evgeniy.ryzhikov.guesstheflag.data

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import evgeniy.ryzhikov.guesstheflag.App
import evgeniy.ryzhikov.guesstheflag.view.fragments.PREFERENCE_SETTINGS

const val PREFERENCE_SETTING_UID = "uid"
const val PREFERENCE_SETTING_NAME = "name"

object FirebaseUserUid {
    private val sharedPreferences =
        App.instance.getSharedPreferences(PREFERENCE_SETTINGS, AppCompatActivity.MODE_PRIVATE)

    fun get() : String {
        val uid: String
        if (sharedPreferences.contains(PREFERENCE_SETTING_UID)) {
            uid = sharedPreferences.getString(PREFERENCE_SETTING_UID, "")!!
        } else {
            uid = Firebase.auth.uid!!
            writeUidToSharedPreference(uid)
        }
        return uid
    }

    fun getName() : String {
        val name : String
        if (sharedPreferences.contains(PREFERENCE_SETTING_NAME)) {
            name = sharedPreferences.getString(PREFERENCE_SETTING_NAME, "")!!
        } else {
            name = Firebase.auth.currentUser?.displayName!!
            writeNameToSharedPreference(name)
        }
        return name
    }

    fun setName(name: String)  {
        writeNameToSharedPreference(name)
    }

    private fun writeNameToSharedPreference(name: String) {
        val editor = sharedPreferences.edit()
        editor.putString(PREFERENCE_SETTING_NAME, name)
        editor.apply()
    }

    private fun writeUidToSharedPreference(uid: String) {
        val editor = sharedPreferences.edit()
        editor.putString(PREFERENCE_SETTING_UID, uid)
        editor.apply()
    }
}