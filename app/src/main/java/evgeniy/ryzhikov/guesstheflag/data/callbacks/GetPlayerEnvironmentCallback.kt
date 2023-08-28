package evgeniy.ryzhikov.guesstheflag.data.callbacks

import evgeniy.ryzhikov.guesstheflag.domain.PlayerEnvironment

interface GetPlayerEnvironmentCallback {
    fun onSuccess(playerEnvironment: PlayerEnvironment)
    fun onFailure(e: Exception)
}