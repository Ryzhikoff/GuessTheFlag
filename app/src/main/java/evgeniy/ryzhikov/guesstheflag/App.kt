package evgeniy.ryzhikov.guesstheflag

import android.app.Application
import evgeniy.ryzhikov.guesstheflag.viewmodel.GameMainViewModel
import evgeniy.ryzhikov.guesstheflag.viewmodel.RatingViewModel
import evgeniy.ryzhikov.guesstheflag.viewmodel.StatisticViewModel

class App : Application() {
    lateinit var statisticViewModel: StatisticViewModel
    lateinit var mainGameViewModel: GameMainViewModel
    lateinit var ratingViewModel: RatingViewModel

    override fun onCreate() {
        super.onCreate()
        instance = this
        statisticViewModel = StatisticViewModel()
        mainGameViewModel = GameMainViewModel(this)
        ratingViewModel = RatingViewModel()
    }

    companion object {
        lateinit var instance : App
            private set
    }
}