package evgeniy.ryzhikov.guesstheflag.domain.statistic.rv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import evgeniy.ryzhikov.guesstheflag.R

class StatFieldDelegateAdapter :
    AbsListItemAdapterDelegate<StatField, StatItem, StatFieldDelegateAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val icon = itemView.findViewById<ImageView>(R.id.statItemIcon)
        val header = itemView.findViewById<TextView>(R.id.statItemHeader)
        val quantity = itemView.findViewById<TextView>(R.id.statItemQuantity)
    }

    override fun isForViewType(
        item: StatItem,
        items: MutableList<StatItem>,
        position: Int
    ): Boolean {
        return item is StatField
    }

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_stat_field, parent, false))
    }

    override fun onBindViewHolder(item: StatField, holder: ViewHolder, payloads: MutableList<Any>) {
        holder.icon.setImageResource(item.iconId)
        holder.header.text = item.header
        holder.quantity.text = item.quantity
    }
}