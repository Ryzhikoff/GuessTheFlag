package evgeniy.ryzhikov.guesstheflag.view.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import evgeniy.ryzhikov.guesstheflag.App
import evgeniy.ryzhikov.guesstheflag.R
import evgeniy.ryzhikov.guesstheflag.data.yandex_ads.YandexAdCallback
import evgeniy.ryzhikov.guesstheflag.data.yandex_ads.YandexRewardedAd
import evgeniy.ryzhikov.guesstheflag.view.activity.MenuActivity
import evgeniy.ryzhikov.guesstheflag.databinding.FragmentNotEnoughEnergyBinding
import evgeniy.ryzhikov.guesstheflag.utils.MediaPlayerController
import evgeniy.ryzhikov.guesstheflag.utils.StartingLoadingAnimation
import javax.inject.Inject

const val TAG_NOT_ENOUGH_ENERGY = "fragment_not_enough_energy"

class NotEnoughEnergyFragment(var callingActivity: StartingLoadingAnimation?) : DialogFragment() {
    private var _binding: FragmentNotEnoughEnergyBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var media: MediaPlayerController

    override fun onAttach(context: Context) {
        super.onAttach(context)
        App.instance.dagger.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotEnoughEnergyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListenerButton()
    }

    private fun setListenerButton() {
        binding.btnInMainMenu.setOnClickListener {
            media.playSound(MediaPlayerController.SoundEvent.CLICK_BUTTON)
            startMainActivity()
        }

        binding.btnWathVideo.setOnClickListener {
            media.playSound(MediaPlayerController.SoundEvent.CLICK_BUTTON)
            showRewardAd()
            it.isEnabled = false;
            binding.btnInMainMenu.isEnabled = false
        }
    }
    private fun startMainActivity() {
        val intent = Intent(requireActivity(), MenuActivity::class.java)
        media.stopMusic = false
        startActivity(intent)
        requireActivity().finish()
    }

    private fun showRewardAd() {
        val yandexRewardedAd = YandexRewardedAd(callingActivity as AppCompatActivity)
        callingActivity?.startLoadingAnimation()
        yandexRewardedAd.loadAndStartAd(object : YandexAdCallback{
            override fun onComplete() {
                callingActivity?.stopLoadingAnimation()
                if (isVisible) {
                    startMainActivity()
                }
            }

            override fun onError() {
                Toast.makeText(requireContext(), requireContext().resources.getString(R.string.error_reward_ad_not_loaded), Toast.LENGTH_SHORT).show()
                binding.btnWathVideo.isEnabled = true
                binding.btnInMainMenu.isEnabled = true
                callingActivity?.stopLoadingAnimation()
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        callingActivity?.stopLoadingAnimation()
        _binding = null
        callingActivity = null
    }
}