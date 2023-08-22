package evgeniy.ryzhikov.guesstheflag.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import evgeniy.ryzhikov.guesstheflag.R
import evgeniy.ryzhikov.guesstheflag.data.yandex_ads.YandexAdCallback
import evgeniy.ryzhikov.guesstheflag.data.yandex_ads.YandexRewardedAd
import evgeniy.ryzhikov.guesstheflag.view.activity.MenuActivity
import evgeniy.ryzhikov.guesstheflag.databinding.FragmentNotEnoughEnergyBinding
import evgeniy.ryzhikov.guesstheflag.utils.MediaPlayerController
import evgeniy.ryzhikov.guesstheflag.utils.StartingLoadingAnimation

const val TAG_NOT_ENOUGH_ENERGY = "fragment_not_enough_energy"

class NotEnoughEnergyFragment(var callingActivity: StartingLoadingAnimation?) : DialogFragment() {
    private var _binding: FragmentNotEnoughEnergyBinding? = null
    private val binding get() = _binding!!
    private val media = MediaPlayerController.getInstance()

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
                startMainActivity()
            }

            override fun onError() {
                Toast.makeText(requireContext(), requireContext().resources.getString(R.string.error_reward_ad_not_loaded), Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        callingActivity = null
    }
}