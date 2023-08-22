package evgeniy.ryzhikov.guesstheflag.utils

import android.content.Context
import android.media.MediaPlayer
import android.widget.SeekBar
import evgeniy.ryzhikov.guesstheflag.R
import evgeniy.ryzhikov.guesstheflag.data.preferences.PreferenceProvider
import evgeniy.ryzhikov.guesstheflag.data.preferences.Preferences.*
import javax.inject.Inject

/**
 * Класс для управления фоновой музыкой и звуками в приложении.
 * Фоновая музыка играет на протяжении всей жизни приложения.
 * Пример для паузы возобновления музыки при сворачивании приложения:
 *
 * override fun onPause() {
 *     super.onPause()
 *     if (media.stopMusic) {
 *         media.pauseMusic()
 *     }
 * }
 *
 * override fun onResume() {
 *    super.onResume()
 *    if (media.stopMusic) {
 *      media.resumeMusic()
 *    }
 *    media.stopMusic = true
 * }
 *
 * При смене Активити/Фрагмента добавить:
 * media.stopMusic = false
 * Где media - объект MediaPlayerController
 *
 * Слушатели MusicVolumeChangeListener и SoundVolumeChangeListener отслеживают изменение громкости
 * в настройках
 *
 * Для установки звука при нажатии кнопки:
 * media.playSound(MediaPlayerController.SoundEvent.CLICK_BUTTON)
 */
class MediaPlayerController @Inject constructor(val context: Context ,val preference : PreferenceProvider) {

    private lateinit var music: MediaPlayer
    private lateinit var sounds: MediaPlayer

    private var playingMusic = true
    private var playingSound = true

    var stopMusic = true

    init {
        playingMusic = preference.getFloat(
            PreferenceName.SETTINGS,
            PreferenceKey.MUSIC_VOLUME
        ) > 0

        if (playingMusic) {
            initMusic()
        }

        playingSound = preference.getFloat(
            PreferenceName.SETTINGS,
            PreferenceKey.SOUND_VOLUME
        ) > 0

    }

    private fun initMusic() {
        music = MediaPlayer.create(context, R.raw.retro_platforming)
        val volume = getMusicVolume()
        music.setVolume(volume, volume)
        music.isLooping = true
        music.start()
    }

    fun resumeMusic() {
        if (playingMusic) {
            music.start()
        }
    }

    fun pauseMusic() {
        if (playingMusic) {
            music.pause()
        }
    }


    private fun setMusicVolume(volume: Float) {
        if (volume > 0) {
            if (!playingMusic) {
                initMusic()
                playingMusic = true
            }
            music.setVolume(volume, volume)
        } else {
            music.stop()
            playingMusic = false
        }
    }

    private fun getMusicVolume() =
        preference.getFloat(PreferenceName.SETTINGS, PreferenceKey.MUSIC_VOLUME)


    fun playSound(soundEvent: SoundEvent, volume: Float = getSoundVolume()) {
        if (playingSound) {
            sounds = MediaPlayer.create(
                context,
                when (soundEvent) {
                    SoundEvent.CLICK_BUTTON -> R.raw.click_button
                    SoundEvent.CORRECT_ANSWER -> R.raw.correct_answer
                    SoundEvent.WRONG_ANSWER -> R.raw.wrong_answer
                }
            )
            sounds.setVolume(volume, volume)
            sounds.start()
        }
    }
    private fun setSoundVolume(volume: Float) {
        if (volume > 0) {
            if (!playingSound) {
                playingSound = true
            }
            playSound(SoundEvent.CLICK_BUTTON, volume)
        } else {
            playingSound = false
        }
    }


    private fun getSoundVolume() =
        preference.getFloat(PreferenceName.SETTINGS, PreferenceKey.SOUND_VOLUME)

    inner class MusicVolumeChangeListener : SeekBar.OnSeekBarChangeListener {
        private var tempVolume = 0f
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            tempVolume = progress / 100f
            setMusicVolume(tempVolume)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {}

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            preference.putFloat(PreferenceName.SETTINGS, PreferenceKey.MUSIC_VOLUME, tempVolume)
        }
    }

    inner class SoundVolumeChangeListener : SeekBar.OnSeekBarChangeListener {
        private var tempVolume = 0f
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            tempVolume = progress / 100f
            setSoundVolume(tempVolume)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {}

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            preference.putFloat(PreferenceName.SETTINGS, PreferenceKey.SOUND_VOLUME, tempVolume)
        }
    }


    enum class SoundEvent {
        CLICK_BUTTON,
        CORRECT_ANSWER,
        WRONG_ANSWER
    }
}
