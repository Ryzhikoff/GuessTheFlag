package evgeniy.ryzhikov.guesstheflag.statistic.rv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import evgeniy.ryzhikov.guesstheflag.R

class StatHeaderDelegateAdapter :
    AbsListItemAdapterDelegate<StatHeader, StatItem, StatHeaderDelegateAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val header = itemView.findViewById<TextView>(R.id.statHeader)
    }

    override fun isForViewType(
        item: StatItem,
        items: MutableList<StatItem>,
        position: Int
    ): Boolean {
        return item is StatHeader
    }

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_stat_header,parent, false))
    }

    override fun onBindViewHolder(
        item: StatHeader,
        holder: ViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.header.text = item.header
    }
}