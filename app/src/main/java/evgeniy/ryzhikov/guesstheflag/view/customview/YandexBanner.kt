package evgeniy.ryzhikov.guesstheflag.view.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.yandex.mobile.ads.banner.AdSize
import com.yandex.mobile.ads.banner.BannerAdEventListener
import com.yandex.mobile.ads.banner.BannerAdView
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import evgeniy.ryzhikov.guesstheflag.R
import evgeniy.ryzhikov.guesstheflag.data.yandex_ads.YandexAds
import evgeniy.ryzhikov.guesstheflag.databinding.YandexBannerBinding
import evgeniy.ryzhikov.guesstheflag.utils.MediaPlayerController

class YandexBanner(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet) {
    private var _binding: YandexBannerBinding? = null
    private val binding get() = _binding!!
    private var bannerAdView: BannerAdView
    private val media = MediaPlayerController.getInstance()

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.yandex_banner, this)
        _binding = YandexBannerBinding.bind(view)
        bannerAdView = binding.bannerAdView
        loadAds()
    }


    private fun loadAds() {
        val maxWidth =
            (context.resources.displayMetrics.widthPixels / context.resources.displayMetrics.density).toInt()
        bannerAdView.setAdUnitId(YandexAds.bannerAdUnitId)
        bannerAdView.setAdSize(
            AdSize.stickySize(
                context,
                maxWidth
            )
        )


        val adRequest = AdRequest.Builder().build()
        bannerAdView.setBannerAdEventListener(object : BannerAdEventListener {
            override fun onAdLoaded() {
            }

            override fun onAdFailedToLoad(p0: AdRequestError) {
            }

            override fun onAdClicked() {
            }

            override fun onLeftApplication() {
                media.pauseMusic()
            }

            override fun onReturnedToApplication() {
                media.resumeMusic()
            }

            override fun onImpression(p0: ImpressionData?) {
            }

        })

        bannerAdView.loadAd(adRequest)
    }


}