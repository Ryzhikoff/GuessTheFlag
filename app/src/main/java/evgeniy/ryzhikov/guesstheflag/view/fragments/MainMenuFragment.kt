package evgeniy.ryzhikov.guesstheflag.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import evgeniy.ryzhikov.guesstheflag.view.activity.MenuActivity
import evgeniy.ryzhikov.guesstheflag.R
import evgeniy.ryzhikov.guesstheflag.view.activity.StatisticActivity
import evgeniy.ryzhikov.guesstheflag.databinding.FragmentMainMenuBinding
import evgeniy.ryzhikov.guesstheflag.utils.MediaPlayerController
import evgeniy.ryzhikov.guesstheflag.view.activity.RatingActivity

class MainMenuFragment : Fragment() {
    private var _binding: FragmentMainMenuBinding? = null
    private val binding get() = _binding!!
    private val media = MediaPlayerController.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addButtonListener()
    }

    private fun addButtonListener() {
        binding.btnStartGame.setOnClickListener {
            media.playSound(MediaPlayerController.SoundEvent.CLICK_BUTTON)
            (activity as MenuActivity).navController.navigate(R.id.action_mainMenuFragment_to_chooseGameFragment)
        }

        binding.btnSetting.setOnClickListener {
            media.playSound(MediaPlayerController.SoundEvent.CLICK_BUTTON)
            (activity as MenuActivity).navController.navigate((R.id.action_mainMenuFragment_to_settingsFragment))
        }

        binding.btnRating.setOnClickListener {
            media.playSound(MediaPlayerController.SoundEvent.CLICK_BUTTON)
            startRatingActivity()
        }

        binding.btnStatistic.setOnClickListener {
            media.playSound(MediaPlayerController.SoundEvent.CLICK_BUTTON)
            startStatisticActivity()
        }

        binding.btnExit.setOnClickListener {
            media.playSound(MediaPlayerController.SoundEvent.CLICK_BUTTON)
            (requireActivity() as MenuActivity).exit()
        }
    }

    private fun startStatisticActivity() {
        media.stopMusic = false
        val intent = Intent(requireContext(), StatisticActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun startRatingActivity() {
        media.stopMusic = false
        val intent = Intent(requireContext(), RatingActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}