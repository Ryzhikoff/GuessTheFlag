package evgeniy.ryzhikov.guesstheflag.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import evgeniy.ryzhikov.guesstheflag.R

class StatisticItemView (context: Context, attributeSet: AttributeSet?) : ConstraintLayout(context, attributeSet) {

    init {
        // "Надуваем наш фрагмент"
        LayoutInflater.from(context).inflate(R.layout.item_stat_field, this)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

    }
}