package evgeniy.ryzhikov.guesstheflag.utils

import android.content.Context
import evgeniy.ryzhikov.guesstheflag.settings.ENERGY_ADD_FOR_ADS
import evgeniy.ryzhikov.guesstheflag.settings.ENERGY_MAX
import evgeniy.ryzhikov.guesstheflag.settings.TIME_IN_SECONDS_ENERGY_RECOVERY

const val PREFERENCE_ENERGY = "energy"
const val PREFERENCE_ENERGY_COUNT = "energy_count"
const val PREFERENCE_ENERGY_TIME_LAST_USED = "energy_last_used"

class Energy (context: Context) {

    private val sharedPreferences = context.getSharedPreferences(PREFERENCE_ENERGY, 0)

    fun isHave() : Boolean {
        if (!isFull())
            update()

        return sharedPreferences.getInt(PREFERENCE_ENERGY_COUNT, 0) > 0
    }

    fun get() : Int {
        if (!isFull()) {
            update()
        }
        return sharedPreferences.getInt(PREFERENCE_ENERGY_COUNT, 0)
    }

    fun isFull() : Boolean = sharedPreferences.getInt(PREFERENCE_ENERGY_COUNT, 0) >= ENERGY_MAX

    fun use() {
        val editor = sharedPreferences.edit()
        if (isFull()) {
            editor.putLong(PREFERENCE_ENERGY_TIME_LAST_USED, System.currentTimeMillis())
        }

        editor.putInt(PREFERENCE_ENERGY_COUNT,
            sharedPreferences.getInt(PREFERENCE_ENERGY_COUNT, 0) - 1
        )

        editor.apply()
    }

    fun getTimeToIncrease() : Long {
        val timeOfLastUsed = sharedPreferences.getLong(PREFERENCE_ENERGY_TIME_LAST_USED, 0)
        val deltaTime = timeOfLastUsed + TIME_IN_SECONDS_ENERGY_RECOVERY * 1000 - System.currentTimeMillis()
        return deltaTime
    }

    private fun update() {
        val energy = sharedPreferences.getInt(PREFERENCE_ENERGY_COUNT, 0)
        val deltaEnergy = ENERGY_MAX - energy
        val timeOfLastUse = sharedPreferences.getLong(PREFERENCE_ENERGY_TIME_LAST_USED, 0L)

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

            print("Добавили энергии: $countAddTime")
            val editor = sharedPreferences.edit()
            //Добавляем Энергию
            editor.putInt(PREFERENCE_ENERGY_COUNT, energy + countAddTime)
            //Устанавливаем новое время использования, увеличие знаение на количество добавлленной энергии
            val newLastUsed = timeOfLastUse + countAddTime * TIME_IN_SECONDS_ENERGY_RECOVERY * 1000
            editor.putLong(PREFERENCE_ENERGY_TIME_LAST_USED, newLastUsed)
            editor.apply()
        }
    }

    fun addForViewingAds() {
        val editor = sharedPreferences.edit()
        editor.putInt(PREFERENCE_ENERGY_COUNT,sharedPreferences.getInt(PREFERENCE_ENERGY_COUNT, 0) + ENERGY_ADD_FOR_ADS)
        editor.apply()
    }
}