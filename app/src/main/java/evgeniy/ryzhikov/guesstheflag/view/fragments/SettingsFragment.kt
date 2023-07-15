package evgeniy.ryzhikov.guesstheflag.view.fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import evgeniy.ryzhikov.guesstheflag.data.FirebaseUserUid
import evgeniy.ryzhikov.guesstheflag.view.activity.MenuActivity
import evgeniy.ryzhikov.guesstheflag.databinding.FragmentSettingsBinding

const val PREFERENCE_SETTINGS = "settings"
const val PREFERENCE_SETTINGS_MUSIC_VOLUME = "music_volume"

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferences: SharedPreferences
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
        sharedPreferences = requireActivity().getSharedPreferences(PREFERENCE_SETTINGS, AppCompatActivity.MODE_PRIVATE)
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
        val editor = sharedPreferences.edit()
        editor.putFloat(PREFERENCE_SETTINGS_MUSIC_VOLUME, volumeMusic)
        editor.apply()
    }

    private fun setPositionOnSeekbars() {
        binding.sbVolumeMusic.progress = (sharedPreferences.getFloat(
            PREFERENCE_SETTINGS_MUSIC_VOLUME, 0f) * 100).toInt()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface ChangeNameCallback {
        fun nameChanges(name: String)
    }
}