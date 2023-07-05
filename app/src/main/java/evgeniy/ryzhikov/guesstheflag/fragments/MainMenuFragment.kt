package evgeniy.ryzhikov.guesstheflag.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import evgeniy.ryzhikov.guesstheflag.MenuActivity
import evgeniy.ryzhikov.guesstheflag.R
import evgeniy.ryzhikov.guesstheflag.StatisticActivity
import evgeniy.ryzhikov.guesstheflag.databinding.FragmentMainMenuBinding

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}