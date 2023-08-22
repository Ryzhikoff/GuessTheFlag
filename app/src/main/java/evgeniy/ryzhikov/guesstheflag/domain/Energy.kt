package evgeniy.ryzhikov.guesstheflag.domain

import evgeniy.ryzhikov.guesstheflag.App
import evgeniy.ryzhikov.guesstheflag.data.preferences.PreferenceProvider
import evgeniy.ryzhikov.guesstheflag.settings.ENERGY_ADD_FOR_ADS
import evgeniy.ryzhikov.guesstheflag.settings.ENERGY_MAX
import evgeniy.ryzhikov.guesstheflag.settings.TIME_IN_SECONDS_ENERGY_RECOVERY
import evgeniy.ryzhikov.guesstheflag.data.preferences.Preferences.*
import javax.inject.Inject


class Energy () {

    @Inject
    lateinit var preference : PreferenceProvider

    init {
        App.instance.dagger.inject(this)
    }

    fun isHave() : Boolean {
        if (!isFull())
            update()

        return preference.getInt(PreferenceName.ENERGY, PreferenceKey.ENERGY_COUNT) > 0
    }

    fun get() : Int {
        if (!isFull()) {
            update()
        }
        return preference.getInt(PreferenceName.ENERGY, PreferenceKey.ENERGY_COUNT)
    }

    fun isFull() : Boolean = preference.getInt(PreferenceName.ENERGY, PreferenceKey.ENERGY_COUNT) >= ENERGY_MAX

    fun use() {
        if (isFull()) {
            preference.putLong(PreferenceName.ENERGY, PreferenceKey.ENERGY_TIME_LAST_USED, System.currentTimeMillis())
        }

        preference.putInt(
            PreferenceName.ENERGY,
            PreferenceKey.ENERGY_COUNT,
            preference.getInt(PreferenceName.ENERGY, PreferenceKey.ENERGY_COUNT) -1
        )
    }

    fun getTimeToIncrease() : Long {
        val timeOfLastUsed = preference.getLong(PreferenceName.ENERGY, PreferenceKey.ENERGY_TIME_LAST_USED)
        val deltaTime = timeOfLastUsed + TIME_IN_SECONDS_ENERGY_RECOVERY * 1000 - System.currentTimeMillis()
        return deltaTime
    }

    private fun update() {
        val energy = preference.getInt(PreferenceName.ENERGY, PreferenceKey.ENERGY_COUNT)
        val deltaEnergy = ENERGY_MAX - energy
        val timeOfLastUse = preference.getLong(PreferenceName.ENERGY, PreferenceKey.ENERGY_TIME_LAST_USED)

        val deltaTime = System.currentTimeMillis() - timeOfLastUse

        var countAddTime = 0
        for (i in 1..deltaEnergy) {
            //Если разница во времени больше чем чем шаг увеличение энергии - добавляем энергию
            if (deltaTime / 1000 > i * TIME_IN_SECONDS_ENERGY_RECOVERY) {
                countAddTime++
            } else {
                break
            }
        }

        if (countAddTime != 0) {
            //Добавляем Энергию
            preference.putInt(PreferenceName.ENERGY, PreferenceKey.ENERGY_COUNT, energy + countAddTime)
            //Устанавливаем новое время использования, увеличие знаение на количество добавлленной энергии
            val newLastUsed = timeOfLastUse + countAddTime * TIME_IN_SECONDS_ENERGY_RECOVERY * 1000
            preference.putLong(PreferenceName.ENERGY, PreferenceKey.ENERGY_TIME_LAST_USED, newLastUsed)
        }
    }

    fun addForViewingAds() {
        preference.putInt(
            PreferenceName.ENERGY,
            PreferenceKey.ENERGY_COUNT,
            preference.getInt(PreferenceName.ENERGY, PreferenceKey.ENERGY_COUNT) + ENERGY_ADD_FOR_ADS)
    }
}