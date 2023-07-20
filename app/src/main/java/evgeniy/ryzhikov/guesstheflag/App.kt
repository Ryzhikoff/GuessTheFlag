package evgeniy.ryzhikov.guesstheflag

import android.app.Application
import evgeniy.ryzhikov.guesstheflag.viewmodel.GameMainViewModel
import evgeniy.ryzhikov.guesstheflag.viewmodel.StatisticViewModel

class App : Application() {
    lateinit var statisticViewModel: StatisticViewModel
    lateinit var mainGameViewModel: GameMainViewModel

    override fun onCreate() {
        super.onCreate()
        instance = this
        statisticViewModel = StatisticViewModel()
        mainGameViewModel = GameMainViewModel(this)
    }

    companion object {
        lateinit var instance : App
            private set
    }
}