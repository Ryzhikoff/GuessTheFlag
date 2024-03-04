package evgeniy.ryzhikov.guesstheflag.view.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import evgeniy.ryzhikov.guesstheflag.R
import evgeniy.ryzhikov.guesstheflag.databinding.ItemRatingFieldBinding

class RatingItem(context: Context, attributeSet: AttributeSet): ConstraintLayout(context, attributeSet) {

    private var _binding: ItemRatingFieldBinding? = null
    private val binding get() = _binding!!

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.item_rating_field, this)
        _binding = ItemRatingFieldBinding.bind(view)
    }

    fun setData(position: Int, name: String, points: Int, games: Int, percent: String){
        val pos = "#$position"
        binding.tvPosition.text = pos
        binding.tvName.text = name
        binding.tvRating.text = points.toString()
        binding.tvGame.text = games.toString()
        binding.tvPercent.text = percent
    }

}