package evgeniy.ryzhikov.guesstheflag.settings

/**
 * Количество вопросов в раунде
 */
const val NUMBER_OF_QUESTION_PER_ROUND = 10

/**
 * Время раунда в милисекундах
 */
const val ROUND_TIME_IN_MILLIS = 10000L

/**
 * Очки за верные ответы
 */
const val STATISTIC_MULTIPLIER_FLAG_COUNTRY = 3
const val STATISTIC_MULTIPLIER_FLAG_REGION = 5
const val STATISTIC_MULTIPLIER_MAP_COUNTRY = 4
const val STATISTIC_MULTIPLIER_MAP_REGION = 6

/**
 * Очки за НЕ верные ответы
 */
const val POINTS_FOR_WRONG_FLAG_COUNTRY = -15
const val POINTS_FOR_WRONG_FLAG_REGION = -10
const val POINTS_FOR_WRONG_MAP_COUNTRY = -12
const val POINTS_FOR_WRONG_MAP_REGION = -8

/**
 * Время востановления 1 энергии в секундах
 */
const val TIME_IN_SECONDS_ENERGY_RECOVERY = 30*60
const val ENERGY_START = 10
const val ENERGY_MAX = 10

/**
 * Количество энергии за просмотр видео-рекламы
 */
const val ENERGY_ADD_FOR_ADS = 10

/**
 * Громкость музыки и звуки при старте, от 0 до 1
 */
const val DEFAULT_MUSIC_VOLUME = 0.2f
const val DEFAULT_SOUND_VOLUME = 0.4f

/**
 * Коэфицент для скорости прокрутки фактов о флагах
 */
const val COEF_FOR_SPEED_SCROLLING_FACTS = 150

//Tag for Lod.d
const val TAG = "===> "

