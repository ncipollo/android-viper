package viper.view.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import rx.Observable
import rx.subjects.PublishSubject
import viper.presenters.ActivityPresenter
import viper.presenters.CollectionPresenter

/**
 * A recycler adapter which removes some of the boiler plate needed to bind the presenter
 * and the adapter.
 * Created by Nick Cipollo on 11/7/16.
 */
class ViperRecyclerAdapter<ListItem,
        VH : ViperViewHolder<ListItem>,
        P : CollectionPresenter<*, ListItem>>(val vhBuilder: (ViewGroup, Int) -> VH)
    : CollectionAdapter<P>, RecyclerView.Adapter<VH>() {

    constructor(vhBuilder: (ViewGroup) -> VH) : this({
        parent,type -> vhBuilder(parent)
    })

    private val actionSubject: PublishSubject<AdapterAction> = PublishSubject.create<AdapterAction>()

    override val actionObserver: Observable<AdapterAction>
        get() = actionSubject.asObservable()

    override var presenter: P? = null

    override val activityPresenter: ActivityPresenter<*>?
        get() = presenter?.activityPresenter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val vh = vhBuilder(parent, viewType)
        vh.actionSubject = actionSubject
        return vh
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        presenter?.getListItem(position)?.let {
            holder.bindListItem(it)
        }
    }

    override fun getItemCount(): Int = presenter?.count ?: 0

    override fun onCollectionUpdated() = notifyDataSetChanged()

    override fun onItemUpdated(itemIndex: Int) = notifyItemChanged(itemIndex)
}

/**
 * A view holder which is intended to be used with the ViperRecyclerAdapter. Subclasses may
 * override onTakeView to setup references to the pertinent views, then bindListItem to
 * associate the presenter item with the view.
 */
open class ViperViewHolder<in ListItem>(val view: View) : RecyclerView.ViewHolder(view) {
    companion object {
        val ACTION_ITEM_SELECTED = 1000
    }
    internal lateinit var actionSubject: PublishSubject<AdapterAction>
    constructor(layoutId: Int, parent: ViewGroup) : this(LayoutInflater.from(parent.context)
            .inflate(layoutId, parent, false))

    init {
        takeView(view)
    }

    private fun takeView(view: View) = onTakeView(view)
    /**
     * Called when the view holder has an inflated root view. This can be used to obtain
     * references to the appropriate views.
     */
    open fun onTakeView(view: View) = Unit

    /**
     * Called when data from the list item should be bound to the view.
     */
    open fun bindListItem(item: ListItem) = Unit

    /**
     * Subclasses may call this method to send an action to the presenter. This will typically be
     * tied to an event from one of the held views.
     */
    fun sendAction(actionId: Int = CollectionPresenter.ACTION_ITEM_SELECTED) {
        val itemIndex = adapterPosition
        if (itemIndex != RecyclerView.NO_POSITION) {
            actionSubject.onNext(AdapterAction(actionId, itemIndex))
        }
    }
}