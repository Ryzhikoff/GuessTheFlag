package evgeniy.ryzhikov.guesstheflag.data.preferences

import androidx.annotation.StringDef

class Preferences() {


    @StringDef(
        value = [
            PreferenceName.SETTINGS,
            PreferenceName.ENERGY
        ]
    )
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class PreferencesName

    @StringDef(
        value = [
            PreferenceKey.MUSIC_VOLUME,
            PreferenceKey.SOUND_VOLUME,
            PreferenceKey.ENERGY_COUNT,
            PreferenceKey.ENERGY_TIME_LAST_USED,
            PreferenceKey.UID,
            PreferenceKey.NAME
        ]
    )
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class PreferencesKey

    class PreferenceName {
        companion object {
            const val SETTINGS = "settings"
            const val ENERGY = "energy"
        }
    }

    class PreferenceKey {
        companion object {
            const val MUSIC_VOLUME = "music_volume"
            const val SOUND_VOLUME = "sound_volume"

            const val ENERGY_COUNT = "energy_count"
            const val ENERGY_TIME_LAST_USED = "energy_last_used"

            const val UID = "uid"
            const val NAME = "name"
        }
    }
}