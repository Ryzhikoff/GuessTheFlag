package evgeniy.ryzhikov.guesstheflag.utils

import android.content.Context
import android.graphics.drawable.Drawable
import evgeniy.ryzhikov.guesstheflag.domain.GameMode
import evgeniy.ryzhikov.guesstheflag.domain.Mode


class DrawableReader(private val context: Context) {
    private var path: String = when(GameMode.mode)  {
         Mode.COUNTRY_FLAG -> "flags/country_flag/"
         Mode.REGION_FLAG -> "flags/region_flag/"
         Mode.COUNTRY_MAP -> "flags/country_map/"
         Mode.REGION_MAP -> "flags/region_map/"
         else -> "flags/country_flag/"
     }

    fun getDrawable(name: String): Drawable? {
        val drawableInputStream = context.assets.open("$path$name.png")
        return Drawable.createFromStream(drawableInputStream, null)
    }
}