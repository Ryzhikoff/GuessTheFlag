package evgeniy.ryzhikov.guesstheflag.data.yandex_ads

import android.content.Context
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.interstitial.InterstitialAd
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener
import evgeniy.ryzhikov.guesstheflag.utils.MediaPlayerController

class YandexInterstitialAd(val context: Context) {
    private lateinit var interstitialAd: InterstitialAd
    private var isLoaded = false
    private lateinit var callback: YandexAdCallback
    private val media = MediaPlayerController.getInstance()

    fun loadAd() {
        interstitialAd = InterstitialAd(context)
        interstitialAd.setAdUnitId(YandexAds.interstitialAdUnitId)

        val adRequest = AdRequest.Builder().build()

        interstitialAd.setInterstitialAdEventListener(object : InterstitialAdEventListener{
            override fun onAdLoaded() {
                isLoaded = true
            }

            override fun onAdFailedToLoad(p0: AdRequestError) {
            }

            override fun onAdShown() {
                media.pauseMusic()
            }

            override fun onAdDismissed() {
                media.resumeMusic()
                interstitialAd.destroy()
                callback.onComplete()
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

        interstitialAd.loadAd(adRequest)
    }

    fun showAds(callback: YandexAdCallback) {
        this.callback = callback
        if (isLoaded) {
            interstitialAd.show()
        } else {
            interstitialAd.destroy()
            callback.onComplete()
        }
    }
}