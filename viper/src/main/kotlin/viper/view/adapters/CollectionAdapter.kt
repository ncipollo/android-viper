package viper.view.adapters

import rx.Observable
import viper.presenters.CollectionPresenter
import viper.view.fragments.CollectionView

/**
 * Represents an adapter which will be driven by a collection presenter.
 * Created by Nick Cipollo on 11/3/16.
 */
interface CollectionAdapter<P : CollectionPresenter<*, *>> : CollectionView {
    var presenter: P?
    val actionObserver : Observable<AdapterAction>
}

data class AdapterAction(val actionId: Int,val itemIndex: Int)