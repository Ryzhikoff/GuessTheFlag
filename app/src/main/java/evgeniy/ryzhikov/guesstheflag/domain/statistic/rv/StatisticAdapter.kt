package evgeniy.ryzhikov.guesstheflag.domain.statistic.rv

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
    }

    fun clear() {
        list.clear()
        update()
    }
    override fun setItems(items: List<StatItem>?) {
        super.setItems(items)
        notifyDataSetChanged()
    }
}