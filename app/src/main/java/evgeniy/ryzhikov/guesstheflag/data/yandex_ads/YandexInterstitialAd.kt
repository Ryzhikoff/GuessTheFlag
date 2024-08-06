package evgeniy.ryzhikov.guesstheflag.data.yandex_ads

import android.app.Activity
import com.yandex.mobile.ads.common.AdError
import com.yandex.mobile.ads.common.AdRequestConfiguration
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.interstitial.InterstitialAd
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener
import com.yandex.mobile.ads.interstitial.InterstitialAdLoadListener
import com.yandex.mobile.ads.interstitial.InterstitialAdLoader
import evgeniy.ryzhikov.guesstheflag.App
import evgeniy.ryzhikov.guesstheflag.utils.MediaPlayerController
import javax.inject.Inject

class YandexInterstitialAd(val activity: Activity) {
//    private lateinit var interstitialAd: InterstitialAd
//    private var isLoaded = false
//    private lateinit var callback: YandexAdCallback

    @Inject
    lateinit var media: MediaPlayerController

    init {
        App.instance.dagger.inject(this)
    }

//    fun loadAd() {
//        interstitialAd = InterstitialAd(context)
//        interstitialAd.setAdUnitId(YandexAds.getInterstitialAdUnitId())
//
//        val adRequest = AdRequest.Builder().build()
//
//        interstitialAd.setInterstitialAdEventListener(object : InterstitialAdEventListener{
//            override fun onAdLoaded() {
//                isLoaded = true
//            }
//
//            override fun onAdFailedToLoad(p0: AdRequestError) {
//            }
//
//            override fun onAdShown() {
//                media.pauseMusic()
//            }
//
//            override fun onAdDismissed() {
//                media.resumeMusic()
//                interstitialAd.destroy()
//                callback.onComplete()
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
//                media.resumeMusic()
//                interstitialAd.destroy()
//                callback.onComplete()
//            }
//
//            override fun onImpression(p0: ImpressionData?) {
//            }
//
//        })
//
//        interstitialAd.loadAd(adRequest)
//    }
//
//    fun showAds(callback: YandexAdCallback) {
//        this.callback = callback
//        if (isLoaded) {
//            interstitialAd.show()
//        } else {
//            interstitialAd.destroy()
//            callback.onComplete()
//        }
//    }

    private var interstitialAd: InterstitialAd? = null
    private var interstitialAdLoader: InterstitialAdLoader? = null

    fun loadAd() {

        interstitialAdLoader = InterstitialAdLoader(activity).apply {
            setAdLoadListener(object : InterstitialAdLoadListener {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    this@YandexInterstitialAd.interstitialAd = interstitialAd
                    // The ad was loaded successfully. Now you can show loaded ad.
                }

                override fun onAdFailedToLoad(error: AdRequestError) {
                    // Ad failed to load with AdRequestError.
                    // Attempting to load a new ad from the onAdFailedToLoad() method is strongly discouraged.
                }
            })
        }
        loadInterstitialAd()
    }

    private fun loadInterstitialAd() {
        val adRequestConfiguration = AdRequestConfiguration.Builder(YandexAds.getInterstitialAdUnitId()).build()
        interstitialAdLoader?.loadAd(adRequestConfiguration)
    }

    fun showAds(onDismiss: () -> Unit) {
        interstitialAd?.apply {
            setAdEventListener(object : InterstitialAdEventListener {
                override fun onAdShown() {
                    media.pauseMusic()
                }
                override fun onAdFailedToShow(adError: AdError) {
                    // Called when an InterstitialAd failed to show.
                    // Clean resources after Ad dismissed
                    interstitialAd?.setAdEventListener(null)
                    interstitialAd = null

                    // Now you can preload the next interstitial ad.
                    loadInterstitialAd()
                }
                override fun onAdDismissed() {
                    // Called when ad is dismissed.
                    // Clean resources after Ad dismissed
                    interstitialAd?.setAdEventListener(null)
                    interstitialAd = null
                    media.resumeMusic()
                    // Now you can preload the next interstitial ad.
                    loadInterstitialAd()
                    onDismiss()
                }
                override fun onAdClicked() {
                    media.pauseMusic()
                    // Called when a click is recorded for an ad.
                }
                override fun onAdImpression(impressionData: ImpressionData?) {
                    media.resumeMusic()
                    // Called when an impression is recorded for an ad.
                }
            })
            show(activity)
        }
    }

    fun clear() {
        interstitialAdLoader?.setAdLoadListener(null)
        interstitialAdLoader = null
        interstitialAd?.setAdEventListener(null)
        interstitialAd = null
    }
}