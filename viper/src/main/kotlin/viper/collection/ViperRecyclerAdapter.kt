package viper.collection

import android.support.v7.widget.RecyclerView
import viper.presenter.ActivityPresenter

/**
 * A recycler adapter which removes some of the boiler plate needed to bind the presenter
 * and the adapter.
 * Created by Nick Cipollo on 11/7/16.
 */
abstract class ViperRecyclerAdapter<VH : RecyclerView.ViewHolder, P : CollectionPresenter<*, *>> :
        CollectionAdapter<P>, RecyclerView.Adapter<VH>() {
    override var presenter: P? = null

    override val activityPresenter: ActivityPresenter<*>?
        get() = presenter?.activityPresenter

    override fun getItemCount(): Int = presenter?.count ?: 0

    override fun onCollectionUpdated() = notifyDataSetChanged()

    override fun onItemUpdated(itemIndex: Int) = notifyItemChanged(itemIndex)
}