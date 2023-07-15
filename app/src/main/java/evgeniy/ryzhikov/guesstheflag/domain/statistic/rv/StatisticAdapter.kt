package evgeniy.ryzhikov.guesstheflag.domain.statistic.rv

import android.util.Log
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class StatisticAdapter : ListDelegationAdapter<List<StatItem>>() {

    private var list = ArrayList<StatItem>()

    init {
        delegatesManager.addDelegate(StatFieldDelegateAdapter())
        delegatesManager.addDelegate(StatHeaderDelegateAdapter())
    }

    fun addItem(item : StatItem) {
        list.add(item)
    }

    fun update() {
        setItems(list)
        Log.d("===========> ", list.toString())
    }
    override fun setItems(items: List<StatItem>?) {
        super.setItems(items)
        notifyDataSetChanged()
    }
}