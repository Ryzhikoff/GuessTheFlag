package evgeniy.ryzhikov.guesstheflag

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Process
import android.util.Log
import com.yandex.mobile.ads.common.InitializationListener
import com.yandex.mobile.ads.common.MobileAds
import evgeniy.ryzhikov.guesstheflag.di.AppComponent
import evgeniy.ryzhikov.guesstheflag.di.DaggerAppComponent
import evgeniy.ryzhikov.guesstheflag.di.modules.DomainModule
import evgeniy.ryzhikov.guesstheflag.di.modules.FirebaseModule
import evgeniy.ryzhikov.guesstheflag.viewmodel.GameMainViewModel
import evgeniy.ryzhikov.guesstheflag.viewmodel.MenuViewModel
import evgeniy.ryzhikov.guesstheflag.viewmodel.RatingViewModel
import evgeniy.ryzhikov.guesstheflag.viewmodel.StatisticViewModel


class App : Application() {
    lateinit var statisticViewModel: StatisticViewModel
    lateinit var mainGameViewModel: GameMainViewModel
    lateinit var ratingViewModel: RatingViewModel
    lateinit var menuViewModel: MenuViewModel

    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()

        if (isMainProcess()) {
            instance = this

            dagger = DaggerAppComponent.builder()
                .domainModule(DomainModule(this))
                .firebaseModule(FirebaseModule())
                .build()

            statisticViewModel = StatisticViewModel()
            mainGameViewModel = GameMainViewModel(instance)
            ratingViewModel = RatingViewModel()
            menuViewModel = MenuViewModel()

            initializeYandexAds()
        }

    }

    private fun initializeYandexAds() {
        MobileAds.initialize(this, object : InitializationListener {
            override fun onInitializationCompleted() {
                Log.d(YANDEX_MOBILE_ADS_TAG, "Yandex ADS SDK initialized")
            }
        })
    }

    private fun isMainProcess(): Boolean {
        return packageName == getCurrentProcessName()
    }

    private fun getCurrentProcessName(): String {
        val mypid = Process.myPid()
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val infos = manager.runningAppProcesses
        for (info in infos) {
            if (info.pid == mypid) {
                return info.processName
            }
        }
        // may never return null
        return ""
    }

    companion object {
        lateinit var instance: App
            private set
        const val YANDEX_MOBILE_ADS_TAG = "YandexMobileAds"
    }
}