package evgeniy.ryzhikov.guesstheflag.data.preferences


import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit
import evgeniy.ryzhikov.guesstheflag.data.preferences.Preferences.PreferenceName
import javax.inject.Inject


class PreferenceProvider @Inject constructor(val context: Context) {

    private var preferenceSettings: SharedPreferences? = null
    private var preferenceEnergy: SharedPreferences? = null

    private fun getPreferenceSettings(): SharedPreferences {
        return preferenceSettings ?: context.getSharedPreferences(PreferenceName.SETTINGS, MODE_PRIVATE).also {
            preferenceSettings = it
        }
    }

    private fun getPreferenceEnergy(): SharedPreferences {
        return preferenceEnergy ?: context.getSharedPreferences(PreferenceName.ENERGY, MODE_PRIVATE).also {
            preferenceEnergy = it
        }
    }

    private fun getSharedPreference(preferenceName: String): SharedPreferences {
        return when (preferenceName) {
            PreferenceName.SETTINGS -> getPreferenceSettings()
            PreferenceName.ENERGY -> getPreferenceEnergy()
            else -> context.getSharedPreferences(preferenceName, MODE_PRIVATE)
        }
    }

    fun getString(
        @Preferences.PreferencesName preferenceName: String,
        @Preferences.PreferencesKey preferencesKey: String
    ) =
        getSharedPreference(preferenceName).getString(preferencesKey, "")!!

    fun getLong(
        @Preferences.PreferencesName preferenceName: String,
        @Preferences.PreferencesKey preferencesKey: String
    ) =
        getSharedPreference(preferenceName).getLong(preferencesKey, 0)

    fun getInt(
        @Preferences.PreferencesName preferenceName: String,
        @Preferences.PreferencesKey preferencesKey: String
    ) =
        getSharedPreference(preferenceName).getInt(preferencesKey, 0)

    fun getFloat(
        @Preferences.PreferencesName preferenceName: String,
        @Preferences.PreferencesKey preferencesKey: String
    ) =
        getSharedPreference(preferenceName).getFloat(preferencesKey, 0f)

    fun putString(
        @Preferences.PreferencesName preferenceName: String,
        @Preferences.PreferencesKey preferencesKey: String,
        value: String
    ) {
        getSharedPreference(preferenceName).edit {
            putString(preferencesKey, value)
        }
    }

    fun putLong(
        @Preferences.PreferencesName preferenceName: String,
        @Preferences.PreferencesKey preferencesKey: String,
        value: Long
    ) {
        getSharedPreference(preferenceName).edit {
            putLong(preferencesKey, value)
        }
    }

    fun putInt(
        @Preferences.PreferencesName preferenceName: String,
        @Preferences.PreferencesKey preferencesKey: String,
        value: Int
    ) {
        getSharedPreference(preferenceName).edit {
            putInt(preferencesKey, value)
        }
    }

    fun putFloat(
        @Preferences.PreferencesName preferenceName: String,
        @Preferences.PreferencesKey preferencesKey: String,
        value: Float
    ) {
        getSharedPreference(preferenceName).edit {
            putFloat(preferencesKey, value)
        }
    }

    fun contains(
        @Preferences.PreferencesName preferenceName: String,
        @Preferences.PreferencesKey preferencesKey: String
    ) =
        getSharedPreference(preferenceName).contains(preferencesKey)

}