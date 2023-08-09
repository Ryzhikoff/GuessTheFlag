package evgeniy.ryzhikov.guesstheflag.data.yandex_ads

import android.content.Context
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.rewarded.Reward
import com.yandex.mobile.ads.rewarded.RewardedAd
import com.yandex.mobile.ads.rewarded.RewardedAdEventListener
import evgeniy.ryzhikov.guesstheflag.domain.Energy
import evgeniy.ryzhikov.guesstheflag.utils.MediaPlayerController

class YandexRewardedAd(val context: Context) {
    private lateinit var rewardedAd : RewardedAd
    private val media = MediaPlayerController.getInstance()

    fun loadAndStartAd(callback: YandexAdCallback) {
        rewardedAd = RewardedAd(context)
        rewardedAd.setAdUnitId(YandexAds.rewardedAdUnitId)
        val adRequest = AdRequest.Builder().build()

        rewardedAd.setRewardedAdEventListener(object : RewardedAdEventListener{
            override fun onAdLoaded() {
                rewardedAd.show()
            }

            override fun onAdFailedToLoad(p0: AdRequestError) {
                println("onAdFailedToLoad ${p0.code}")
                rewardedAd.destroy()
                callback.onError()
            }

            override fun onAdShown() {
                media.pauseMusic()
            }

            override fun onAdDismissed() {
                media.resumeMusic()
                rewardedAd.destroy()
                callback.onComplete()
            }

            override fun onRewarded(p0: Reward) {
                val energy = Energy()
                energy.addForViewingAds()
            }

            override fun onAdClicked() {
            }

            override fun onLeftApplication() {
                media.pauseMusic()
            }

            override fun onReturnedToApplication() {
            }

            override fun onImpression(p0: ImpressionData?) {
            }

        })

        rewardedAd.loadAd(adRequest)
    }

}