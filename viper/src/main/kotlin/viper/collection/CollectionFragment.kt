package viper.collection

import android.os.Bundle
import rx.Subscription
import viper.presenter.FragmentPresenter
import viper.view.ViperFragment

/**
 * A generic collection fragment is driven by a collection presenter.
 * Created by Nick Cipollo on 11/2/16.
 */
abstract class CollectionFragment<P : CollectionPresenter<*,*>> : CollectionView,
        ViperFragment<P>() {
    lateinit var adapter: CollectionAdapter<P>
    var actionSub: Subscription? = null

    abstract fun createAdapter(): CollectionAdapter<P>

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        adapter = createAdapter()
        adapter.presenter = presenter
        actionSub = adapter.actionObserver.subscribe { sendItemAction(it.actionId,it.itemIndex) }
    }

    override fun onDestroy() {
        super.onDestroy()
        actionSub?.unsubscribe()
        actionSub = null
        adapter.presenter = null
    }

    override fun onCollectionUpdated() {
        adapter.onCollectionUpdated()
    }

    override fun onItemUpdated(itemIndex: Int) {
        adapter.onCollectionUpdated()
    }

    protected fun sendItemAction(actionId: Int, itemIndex: Int) {
        (presenter as? CollectionPresenter<*, *>)?.onItemAction(actionId, itemIndex)
    }
}