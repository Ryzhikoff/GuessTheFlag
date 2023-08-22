package evgeniy.ryzhikov.guesstheflag.di

import dagger.Component
import evgeniy.ryzhikov.guesstheflag.data.FirebaseStorageAdapter
import evgeniy.ryzhikov.guesstheflag.data.FirebaseUserUid
import evgeniy.ryzhikov.guesstheflag.data.yandex_ads.YandexInterstitialAd
import evgeniy.ryzhikov.guesstheflag.data.yandex_ads.YandexRewardedAd
import evgeniy.ryzhikov.guesstheflag.di.modules.DomainModule
import evgeniy.ryzhikov.guesstheflag.di.modules.FirebaseModule
import evgeniy.ryzhikov.guesstheflag.domain.Energy
import evgeniy.ryzhikov.guesstheflag.utils.MediaPlayerController
import evgeniy.ryzhikov.guesstheflag.view.activity.GameMainActivity
import evgeniy.ryzhikov.guesstheflag.view.activity.GreetingActivity
import evgeniy.ryzhikov.guesstheflag.view.activity.MenuActivity
import evgeniy.ryzhikov.guesstheflag.view.activity.RatingActivity
import evgeniy.ryzhikov.guesstheflag.view.activity.StatisticActivity
import evgeniy.ryzhikov.guesstheflag.view.customview.YandexBanner
import evgeniy.ryzhikov.guesstheflag.view.fragments.ChangeNameFragment
import evgeniy.ryzhikov.guesstheflag.view.fragments.ChooseGameFragment
import evgeniy.ryzhikov.guesstheflag.view.fragments.MainMenuFragment
import evgeniy.ryzhikov.guesstheflag.view.fragments.NotEnoughEnergyFragment
import evgeniy.ryzhikov.guesstheflag.view.fragments.SettingsFragment
import evgeniy.ryzhikov.guesstheflag.viewmodel.GameMainViewModel
import evgeniy.ryzhikov.guesstheflag.viewmodel.RatingViewModel
import evgeniy.ryzhikov.guesstheflag.viewmodel.StatisticViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [
    DomainModule::class,
    FirebaseModule::class
])
interface AppComponent {

    fun inject (ratingActivity: RatingActivity)
    fun inject (greetingActivity: GreetingActivity)
    fun inject (statisticActivity: StatisticActivity)
    fun inject (gameMainActivity: GameMainActivity)
    fun inject (menuActivity: MenuActivity)

    fun inject (settingsFragment: SettingsFragment)
    fun inject (chooseGameFragment: ChooseGameFragment)
    fun inject (menMenuFragment: MainMenuFragment)
    fun inject (notEnoughEnergyFragment: NotEnoughEnergyFragment)
    fun inject (changeNameFragment: ChangeNameFragment)

    fun inject (gameMainViewModel: GameMainViewModel)
    fun inject (ratingViewModel: RatingViewModel)
    fun inject (statisticViewModel: StatisticViewModel)

    fun inject (firebaseUserUid: FirebaseUserUid)
    fun inject (firebaseStorageAdapter: FirebaseStorageAdapter)

    fun inject (energy: Energy)
    fun inject (mediaPlayerController: MediaPlayerController)

    fun inject (yandexRewardedAd: YandexRewardedAd)
    fun inject (yandexInterstitialAd: YandexInterstitialAd)
    fun inject (yandexBanner: YandexBanner)
}