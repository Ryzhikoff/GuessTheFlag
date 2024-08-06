package evgeniy.ryzhikov.guesstheflag.data.yandex_ads

import android.app.Activity
import android.content.Context
import com.yandex.mobile.ads.common.AdError
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestConfiguration
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.rewarded.Reward
import com.yandex.mobile.ads.rewarded.RewardedAd
import com.yandex.mobile.ads.rewarded.RewardedAdEventListener
import com.yandex.mobile.ads.rewarded.RewardedAdLoadListener
import com.yandex.mobile.ads.rewarded.RewardedAdLoader
import evgeniy.ryzhikov.guesstheflag.App
import evgeniy.ryzhikov.guesstheflag.domain.Energy
import evgeniy.ryzhikov.guesstheflag.utils.MediaPlayerController
import javax.inject.Inject

class YandexRewardedAd(val context: Context) {
//    private lateinit var rewardedAd : RewardedAd

    @Inject
    lateinit var energy: Energy
    @Inject
    lateinit var media: MediaPlayerController

    init {
        App.instance.dagger.inject(this)
    }

//    fun loadAndStartAd(callback: YandexAdCallback) {
//        rewardedAd = RewardedAd(context)
//        rewardedAd.setAdUnitId(YandexAds.getRewardedAdUnitId())
//        val adRequest = AdRequest.Builder().build()
//
//        rewardedAd.setRewardedAdEventListener(object : RewardedAdEventListener{
//            override fun onAdLoaded() {
//                rewardedAd.show()
//            }
//
//            override fun onAdFailedToLoad(p0: AdRequestError) {
//                println("onAdFailedToLoad ${p0.code}")
//                rewardedAd.destroy()
//                callback.onError()
//            }
//
//            override fun onAdShown() {
//                media.pauseMusic()
//            }
//
//            override fun onAdDismissed() {
//                media.resumeMusic()
//                rewardedAd.destroy()
//                callback.onComplete()
//            }
//
//            override fun onRewarded(p0: Reward) {
//                energy.addForViewingAds()
//            }
//
//            override fun onAdClicked() {
//            }
//
//            override fun onLeftApplication() {
//                media.pauseMusic()
//            }
//
//            override fun onReturnedToApplication() {
//            }
//
//            override fun onImpression(p0: ImpressionData?) {
//            }
//
//        })
//
//        rewardedAd.loadAd(adRequest)
//    }

    private var rewardedAdLoader: RewardedAdLoader? = null
    private var rewardedAd: RewardedAd? = null

    fun init(
        activity: Activity,
        adUnitId: String,
        isLoaded: (Boolean) -> Unit
    ) {
        rewardedAdLoader = RewardedAdLoader(activity).apply {
            setAdLoadListener(object : RewardedAdLoadListener {
                override fun onAdFailedToLoad(error: AdRequestError) {
                    isLoaded(false)
                }

                override fun onAdLoaded(rewarded: RewardedAd) {
                    rewardedAd = rewarded
                    isLoaded(true)
                }

            })
        }
        loadRewardedAd(adUnitId)
    }

    private fun loadRewardedAd(adUnitId: String) {
        rewardedAdLoader?.loadAd(createAdRequestConfiguration(adUnitId))
    }

    private fun createAdRequestConfiguration(adUnitId: String): AdRequestConfiguration =
        AdRequestConfiguration.Builder(adUnitId).build()

    fun loadAndStartAd(activity: Activity, onComplete: () -> Unit) {
        rewardedAd?.apply {
            setAdEventListener(object : RewardedAdEventListener {
                override fun onAdClicked() {
                }

                override fun onAdDismissed() {
                    onComplete()
                    media.resumeMusic()
                    clear()
                }

                override fun onAdFailedToShow(adError: AdError) {
                }

                override fun onAdImpression(impressionData: ImpressionData?) {
                }

                override fun onAdShown() {
                    media.pauseMusic()
                }

                override fun onRewarded(reward: Reward) {
                    energy.addForViewingAds()
                }

            })
            show(activity)
        }
    }

    private fun clear() {
        rewardedAdLoader?.setAdLoadListener(null)
        rewardedAdLoader = null
        rewardedAd?.setAdEventListener(null)
        rewardedAd = null
    }
}