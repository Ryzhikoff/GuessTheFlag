package evgeniy.ryzhikov.guesstheflag.settings

//Количество вопросов в раунде
const val NUMBER_OF_QUESTION_PER_ROUND = 10
//Время райнду
const val ROUND_TIME_IN_MILLIS = 10000

//Очки за верные ответы
const val STATISTIC_MULTIPLIER_FLAG_COUNTRY = 3
const val STATISTIC_MULTIPLIER_FLAG_REGION = 5
const val STATISTIC_MULTIPLIER_MAP_COUNTRY = 4
const val STATISTIC_MULTIPLIER_MAP_REGION = 6

//Очки за неверные ответы
const val POINTS_FOR_WRONG_FLAG_COUNTRY = -15
const val POINTS_FOR_WRONG_FLAG_REGION = -10
const val POINTS_FOR_WRONG_MAP_COUNTRY = -12
const val POINTS_FOR_WRONG_MAP_REGION = -8

//Energy Controller
const val TIME_IN_SECONDS_ENERGY_RECOVERY = 30 * 60;
const val ENERGY_START = 3;
const val ENERGY_MAX = 3;
const val ENERGY_ADD_FOR_ADS = 3;

const val DEFAULT_MUSIC_VOLUME = 0.5f
const val DEFAULT_SOUND_VOLUME = 0.5f

