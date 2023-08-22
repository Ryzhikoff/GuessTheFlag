package evgeniy.ryzhikov.guesstheflag.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import evgeniy.ryzhikov.guesstheflag.data.preferences.PreferenceProvider
import evgeniy.ryzhikov.guesstheflag.domain.Energy
import evgeniy.ryzhikov.guesstheflag.utils.MediaPlayerController
import javax.inject.Singleton

@Module
class DomainModule(val context: Context) {

    @Provides
    fun provideContext() = context

    @Provides
    fun provideEnergy() = Energy()

    @Singleton
    @Provides
    fun providePreferenceProvider(context: Context) = PreferenceProvider(context)

    @Singleton
    @Provides
    fun provideMediaPlayerController(context: Context, preferenceProvider: PreferenceProvider) = MediaPlayerController(context, preferenceProvider)

}