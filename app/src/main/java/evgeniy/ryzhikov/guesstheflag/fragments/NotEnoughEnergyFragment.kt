package evgeniy.ryzhikov.guesstheflag.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import evgeniy.ryzhikov.guesstheflag.MenuActivity
import evgeniy.ryzhikov.guesstheflag.databinding.FragmentNotEnoughEnergyBinding
import evgeniy.ryzhikov.guesstheflag.utils.Energy

const val TAG_NOT_ENOUGH_ENERGY = "fragment_not_enough_energy"

class NotEnoughEnergyFragment : DialogFragment() {
    private var _binding: FragmentNotEnoughEnergyBinding? = null
    private val binding get() = _binding!!

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
            val intent = Intent(requireActivity(), MenuActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        binding.btnWathVideo.setOnClickListener {
            Toast.makeText(requireContext(), "Посмотрели рекламу!", Toast.LENGTH_SHORT).show()
            val energy = Energy(requireContext())
            energy.addForViewingAds()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}