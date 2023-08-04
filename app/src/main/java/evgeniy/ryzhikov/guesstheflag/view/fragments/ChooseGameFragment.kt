package evgeniy.ryzhikov.guesstheflag.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import evgeniy.ryzhikov.guesstheflag.view.activity.GameMainActivity
import evgeniy.ryzhikov.guesstheflag.view.activity.MenuActivity
import evgeniy.ryzhikov.guesstheflag.R
import evgeniy.ryzhikov.guesstheflag.databinding.FragmentChooseGameBinding
import evgeniy.ryzhikov.guesstheflag.domain.Energy
import evgeniy.ryzhikov.guesstheflag.domain.GameMode
import evgeniy.ryzhikov.guesstheflag.domain.Mode

class ChooseGameFragment : Fragment() {
    private var _binding: FragmentChooseGameBinding? = null
    private val binding get() = _binding!!
    private var  energy: Energy = Energy()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addButtonListener()
    }

    private fun addButtonListener() {
        binding.btnBack.setOnClickListener {
            (activity as MenuActivity).navController.navigate(R.id.action_chooseGameFragment_to_mainMenuFragment)
        }

        binding.btnStartCountryFlag.setOnClickListener {
            startGame(it as AppCompatButton)
        }

        binding.btnStartRegionFlag.setOnClickListener {
            startGame(it as AppCompatButton)
        }

        //Пока заглушка:
        /*binding.btnStartCountryMap.setOnClickListener {
            startGame(it as AppCompatButton)
        }

        binding.btnStartRegionMap.setOnClickListener {
            startGame(it as AppCompatButton)
        }*/
    }

    private fun startGame(button : AppCompatButton) {
        if (energy.isHave()) {
            energy.use()
            GameMode.mode = when (button.id) {
                R.id.btnStartCountryFlag -> Mode.COUNTRY_FLAG
                R.id.btnStartRegionFlag -> Mode.REGION_FLAG
                R.id.btnStartCountryMap -> Mode.COUNTRY_MAP
                R.id.btnStartRegionMap -> Mode.REGION_MAP
                else -> Mode.COUNTRY_FLAG
            }
            val intent = Intent(requireContext(), GameMainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        } else {
            NotEnoughEnergyFragment().show(childFragmentManager, TAG_NOT_ENOUGH_ENERGY)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}