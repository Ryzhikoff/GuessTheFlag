package evgeniy.ryzhikov.guesstheflag.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import evgeniy.ryzhikov.guesstheflag.R
import evgeniy.ryzhikov.guesstheflag.data.FirebaseUserUid
import evgeniy.ryzhikov.guesstheflag.databinding.FragmentChangeNameBinding
import evgeniy.ryzhikov.guesstheflag.utils.MediaPlayerController

const val TAG_CHANGE_NAME = "fragment_change_name"

class ChangeNameFragment(val callback: SettingsFragment.ChangeNameCallback) : DialogFragment() {
    private var _binding: FragmentChangeNameBinding? = null
    private val binding get() = _binding!!
    private val media = MediaPlayerController.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangeNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnChangeName.setOnClickListener {
            media.playSound(MediaPlayerController.SoundEvent.CLICK_BUTTON)
            val name = binding.edNewName.text.toString()
            saveName(name)
        }
    }

    private fun saveName (name: String) {
        if (name.length >= 3) {
            FirebaseUserUid.setName(name)
            callback.nameChanges(name)
            parentFragmentManager.beginTransaction().remove(this).commitNow()
        } else {
            Toast.makeText(requireContext(), getString(R.string.settings_non_length_name), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}