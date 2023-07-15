package evgeniy.ryzhikov.guesstheflag

import android.app.Application
import evgeniy.ryzhikov.guesstheflag.viewmodel.MainGameViewModel
import evgeniy.ryzhikov.guesstheflag.viewmodel.StatisticViewModel

class App : Application() {
    lateinit var statisticViewModel: StatisticViewModel
    lateinit var mainGameViewModel: MainGameViewModel

    override fun onCreate() {
        super.onCreate()
        instance = this
        statisticViewModel = StatisticViewModel()
        mainGameViewModel = MainGameViewModel(this)
    }

    companion object {
        lateinit var instance : App
            private set
    }
}