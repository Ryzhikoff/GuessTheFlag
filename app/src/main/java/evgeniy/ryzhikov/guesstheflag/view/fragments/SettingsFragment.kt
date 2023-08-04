package evgeniy.ryzhikov.guesstheflag.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import evgeniy.ryzhikov.guesstheflag.data.FirebaseUserUid
import evgeniy.ryzhikov.guesstheflag.data.preferences.PreferenceProvider
import evgeniy.ryzhikov.guesstheflag.data.preferences.Preferences.*
import evgeniy.ryzhikov.guesstheflag.view.activity.MenuActivity
import evgeniy.ryzhikov.guesstheflag.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private var preference = PreferenceProvider.getInstance()
    private var volumeMusic = 0f

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
            (activity as MenuActivity).navController.popBackStack()
        }

        binding.btnChangeName.setOnClickListener {
            ChangeNameFragment(object : ChangeNameCallback{
                override fun nameChanges(name: String) {
                    binding.name.text = name
                }

            }).show(parentFragmentManager, TAG_CHANGE_NAME)
        }

        binding.sbVolumeMusic.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, v: Int, p2: Boolean) {
                volumeMusic = v / 100f
                (requireActivity() as MenuActivity).setVolumeMusic(volumeMusic)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })
    }

    override fun onPause() {
        super.onPause()

        preference.putFloat(PreferenceName.SETTINGS, PreferenceKey.MUSIC_VOLUME, volumeMusic)

    }

    private fun setPositionOnSeekbars() {
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