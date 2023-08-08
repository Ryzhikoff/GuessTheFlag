package evgeniy.ryzhikov.guesstheflag.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import evgeniy.ryzhikov.guesstheflag.data.FirebaseUserUid
import evgeniy.ryzhikov.guesstheflag.data.preferences.PreferenceProvider
import evgeniy.ryzhikov.guesstheflag.data.preferences.Preferences.*
import evgeniy.ryzhikov.guesstheflag.view.activity.MenuActivity
import evgeniy.ryzhikov.guesstheflag.databinding.FragmentSettingsBinding
import evgeniy.ryzhikov.guesstheflag.utils.MediaPlayerController


class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private var preference = PreferenceProvider.getInstance()
    private val media = MediaPlayerController.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addButtonListener()
        setPositionOnSeekbars()

        binding.sbVolumeMusic.setOnSeekBarChangeListener(MediaPlayerController.getInstance().MusicVolumeChangeListener())
        binding.sbVolumeSound.setOnSeekBarChangeListener(MediaPlayerController.getInstance().SoundVolumeChangeListener())
    }

    override fun onResume() {
        super.onResume()
        displayName()
    }
    private fun displayName() {
        binding.name.text = FirebaseUserUid.getName()
    }

    private fun addButtonListener() {
        binding.btnBack.setOnClickListener {
            media.playSound(MediaPlayerController.SoundEvent.CLICK_BUTTON)
            (activity as MenuActivity).navController.popBackStack()
        }

        binding.btnChangeName.setOnClickListener {
            media.playSound(MediaPlayerController.SoundEvent.CLICK_BUTTON)
            displayChangeNameFragment()
        }

    }

    private fun displayChangeNameFragment() {
        ChangeNameFragment(object : ChangeNameCallback{
            override fun nameChanges(name: String) {
                binding.name.text = name
            }

        }).show(parentFragmentManager, TAG_CHANGE_NAME)
    }

    private fun setPositionOnSeekbars() {
        binding.sbVolumeSound.progress = (
                preference.getFloat(
            PreferenceName.SETTINGS, PreferenceKey.SOUND_VOLUME) * 100).toInt()

        binding.sbVolumeMusic.progress = (
                preference.getFloat(
                    PreferenceName.SETTINGS, PreferenceKey.MUSIC_VOLUME) * 100).toInt()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface ChangeNameCallback {
        fun nameChanges(name: String)
    }
}