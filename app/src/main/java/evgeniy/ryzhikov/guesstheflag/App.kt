package evgeniy.ryzhikov.guesstheflag

import android.app.Application
import evgeniy.ryzhikov.guesstheflag.data.preferences.PreferenceProvider
import evgeniy.ryzhikov.guesstheflag.utils.MediaPlayerController
import evgeniy.ryzhikov.guesstheflag.viewmodel.GameMainViewModel
import evgeniy.ryzhikov.guesstheflag.viewmodel.MenuViewModel
import evgeniy.ryzhikov.guesstheflag.viewmodel.RatingViewModel
import evgeniy.ryzhikov.guesstheflag.viewmodel.StatisticViewModel

class App : Application() {
    lateinit var statisticViewModel: StatisticViewModel
    lateinit var mainGameViewModel: GameMainViewModel
    lateinit var ratingViewModel: RatingViewModel
    lateinit var menuViewModel: MenuViewModel
    lateinit var mediaPlayerController: MediaPlayerController
    lateinit var preferenceProvider: PreferenceProvider

    override fun onCreate() {
        super.onCreate()
        instance = this
        statisticViewModel = StatisticViewModel()
        mainGameViewModel = GameMainViewModel(this)
        ratingViewModel = RatingViewModel()
        menuViewModel = MenuViewModel()
        preferenceProvider = PreferenceProvider.getInstance()
        mediaPlayerController = MediaPlayerController.getInstance()
    }


    companion object {
        lateinit var instance : App
            private set
    }
}