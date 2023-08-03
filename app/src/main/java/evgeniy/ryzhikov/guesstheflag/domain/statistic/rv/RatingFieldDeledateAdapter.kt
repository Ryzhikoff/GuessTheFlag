package evgeniy.ryzhikov.guesstheflag.domain.statistic.rv


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import evgeniy.ryzhikov.guesstheflag.R

class RatingFieldDelegateAdapter :
    AbsListItemAdapterDelegate<RatingField, StatItem, RatingFieldDelegateAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val position = itemView.findViewById<TextView>(R.id.tvPosition)
        val name = itemView.findViewById<TextView>(R.id.tvName)
        val points = itemView.findViewById<TextView>(R.id.tvRating)
        val games = itemView.findViewById<TextView>(R.id.tvGame)
        val percentWin = itemView.findViewById<TextView>(R.id.tvPercent)

    }

    override fun isForViewType(
        item: StatItem,
        items: MutableList<StatItem>,
        position: Int
    ): Boolean {
        return item is RatingField
    }

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_rating_field, parent, false)
        )
    }

    override fun onBindViewHolder(
        item: RatingField,
        holder: ViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.position.text = item.position
        holder.name.text = item.name
        holder.points.text = item.points.toString()
        holder.games.text = item.games.toString()
        holder.percentWin.text = item.percentWin
    }

}