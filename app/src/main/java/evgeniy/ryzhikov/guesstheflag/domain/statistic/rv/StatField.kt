package evgeniy.ryzhikov.guesstheflag.domain.statistic.rv

import androidx.annotation.DrawableRes

data class StatField(@DrawableRes val iconId: Int, val header: String, val quantity: String) :
    StatItem
