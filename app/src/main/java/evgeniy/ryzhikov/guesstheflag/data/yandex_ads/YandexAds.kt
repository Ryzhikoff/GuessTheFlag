package evgeniy.ryzhikov.guesstheflag.data.yandex_ads

import evgeniy.ryzhikov.guesstheflag.BuildConfig

object YandexAds {
//    const val interstitialAdUnitId = "R-M-2577975-1"
//    const val rewardedAdUnitId = "R-M-2577975-2"
//    const val bannerAdUnitId = "R-M-2577975-4"
//    const val interstitialAdUnitId = "demo-interstitial-yandex"
//    const val rewardedAdUnitId = "demo-rewarded-yandex"
//    const val bannerAdUnitId = "demo-banner-yandex"

    fun getInterstitialAdUnitId(): String {
        return if (BuildConfig.DEBUG) {
            "demo-interstitial-yandex"
        } else {
            "R-M-2577975-1"
        }
    }

    fun getRewardedAdUnitId(): String {
        return if (BuildConfig.DEBUG) {
            "demo-rewarded-yandex"
        } else {
            "R-M-2577975-2"
        }
    }

    fun getBannerAdUnitId(): String {
        return if (BuildConfig.DEBUG) {
            "demo-banner-yandex"
        } else {
            "R-M-2577975-4"
        }
    }
}