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
import evgeniy.ryzhikov.guesstheflag.view.activity.RatingActivity

class MainMenuFragment : Fragment() {
    private var _binding: FragmentMainMenuBinding? = null
    private val binding get() = _binding!!

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
            (activity as MenuActivity).navController.navigate(R.id.action_mainMenuFragment_to_chooseGameFragment)
        }

        binding.btnSetting.setOnClickListener {
            (activity as MenuActivity).navController.navigate((R.id.action_mainMenuFragment_to_settingsFragment))
        }

        binding.btnRating.setOnClickListener {
            startRatingActivity()
        }

        binding.btnStatistic.setOnClickListener {
            startStatisticActivity()
        }

        binding.btnExit.setOnClickListener {
            (requireActivity() as MenuActivity).exit()
        }
    }

    private fun startStatisticActivity() {
        val intent = Intent(requireContext(), StatisticActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun startRatingActivity() {
        val intent = Intent(requireContext(), RatingActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}